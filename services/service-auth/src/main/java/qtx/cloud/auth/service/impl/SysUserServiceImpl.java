package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.entity.SysUser;
import qtx.cloud.auth.entity.SysUserRole;
import qtx.cloud.auth.entity.Token;
import qtx.cloud.auth.entity.User;
import qtx.cloud.auth.mapper.SysUserMapper;
import qtx.cloud.auth.service.SysUserRoleService;
import qtx.cloud.auth.service.SysUserService;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.base.BaseEntity;
import qtx.cloud.model.dto.auth.*;
import qtx.cloud.model.vo.auth.CreateVO;
import qtx.cloud.model.vo.auth.LoginVO;
import qtx.cloud.model.vo.auth.SysUserVO;
import qtx.cloud.service.utils.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2022-08-30
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final CommonMethod commonMethod;

    private final SysUserRoleService sysUserRoleService;

    private final RedisUtils redisUtils;

    @Value("${time.token}")
    private Long tokenTime;

    @Value("${time.refresh}")
    private Integer refreshTime;

    public SysUserServiceImpl(JwtUtils jwtUtils, PasswordEncoder passwordEncoder, CommonMethod commonMethod,
                              SysUserRoleService sysUserRoleService, RedisUtils redisUtils) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.commonMethod = commonMethod;
        this.sysUserRoleService = sysUserRoleService;
        this.redisUtils = redisUtils;
    }

    @Override
    public LoginVO login(LoginDTO user) throws DataException {
        String userCode = user.getUserCode();
        // 登录试错
        Integer loginNumRedis = (Integer) redisUtils.getMsg(getRedisLoginNumId(userCode));
        int loginNum = Optional.ofNullable(loginNumRedis).orElse(0);
        if (loginNum == StaticConstant.LOGIN_ERROR_UPPER_LIMIT) {
            log.info("userCode:{},{}", userCode, DataEnums.USER_LOGIN_LOCKING);
            throw new DataException(DataEnums.USER_LOGIN_LOCKING);
        }
        // 验证码
        String secret = user.getSession();
        String msg = (String) redisUtils.getMsg(getRedisCodeId(secret));
        if (StringUtils.isBlank(msg)) {
            throw new DataException(DataEnums.AUTH_CODE_EXPIRED);
        }
        if (!user.getAuthCode().equalsIgnoreCase(msg)) {
            int i = (int) redisUtils.getMsg(getRedisNumId(secret));
            if (i > 0) {
                redisUtils.setMsgDiyTimeOut(getRedisNumId(secret), i - 1, 1, TimeUnit.MINUTES);
                throw new DataException(DataEnums.USER_CODE_FAIL);
            } else {
                throw new DataException(DataEnums.AUTH_CODE_EXPIRED);
            }
        }
        // 密码校验
        SysUser one = baseMapper.selectOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserCode, userCode)
                .eq(SysUser::getStatus, true));
        if (Objects.isNull(one)) {
            throw new DataException(DataEnums.USER_IS_NULL);
        }
        if (!passwordEncoder.matches(user.getPassword(), one.getPassword())) {
            redisUtils.setMsgDiyTimeOut(getRedisLoginNumId(userCode), loginNum + 1, 10, TimeUnit.MINUTES);
            throw new DataException(DataEnums.WRONG_PASSWORD);
        }
        return getLoginVO(userCode, secret, one);
    }

    @Override
    public SysUserVO getOneOnline() {
        String user = commonMethod.getUser();
        SysUser one = getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserCode, user));
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public void getAuthCode(HttpServletResponse response, String session) throws IOException {
        // 利用图片工具生成图片
        // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.newBuilder()
                .setWidth(120)
                .setHeight(35)
                .setSize(1)
                .setLines(1)
                .setFontSize(25)
                .setTilt(true)
                .setBackgroundColor(Color.LIGHT_GRAY)
                .build()
                .createImage();
        redisUtils.setMsgDiyTimeOut(getRedisCodeId(session), objs[0], 1, TimeUnit.MINUTES);
        redisUtils.setMsgDiyTimeOut(getRedisNumId(session), 3, 1, TimeUnit.MINUTES);
        // 将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    @Override
    public CreateVO createUser(CreateUserDTO user) throws DataException {
        String password = user.getPassword();
        if (StringUtils.isBlank(user.getUserName()) && StringUtils.isBlank(password)) {
            throw new DataException(DataEnums.DATA_IS_NULL);
        }
        Set<String> set = list().stream().map(SysUser::getUserCode).collect(Collectors.toSet());
        boolean flag;
        do {
            String s = String.valueOf(NumUtils.numUserCard());
            flag = set.contains(s);
            if (!flag) {
                user.setUserCode(s);
            }
        }
        while (flag);
        user.setPassword(passwordEncoder.encode(password));
        if (save(SysUser.builder()
                .userCode(user.getUserCode())
                .userName(user.getUserName())
                .password(user.getPassword())
                .build())) {
            return new CreateVO(user.getUserName(), user.getUserCode(), password);
        } else {
            throw new DataException(DataEnums.DATA_INSERT_FAIL);
        }
    }

    @Override
    public boolean saveOrUpdateNew(CreateSysUserDTO user) throws DataException {
        List<Integer> roleId = user.getRoleId();
        removeRole(user.getUserCode());
        sysUserRoleService.addRoleWithUser(new UserRolesDTO().setUserCode(user.getUserCode()).setRoleIds(roleId));
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user, sysUser);
        try {
            return saveOrUpdate(sysUser);
        } catch (Exception e) {
            throw new DataException(DataEnums.DATA_INSERT_FAIL);
        }
    }

    @Override
    public boolean logout() {
        return deleteRedisUserInfo(commonMethod.getUser());
    }

    @Override
    public Page<SysUserVO> listUserPage(SysUserDTO dto) {
        return baseMapper.selectPageNew(dto.getPage(),
                Wrappers.lambdaQuery(SysUser.class)
                        .eq(BaseEntity::getDeleteFlag, false)
                        .like(StringUtils.isNotBlank(dto.getUserCode()), SysUser::getUserCode, dto.getUserCode())
                        .like(StringUtils.isNotBlank(dto.getUserName()), SysUser::getUserName, dto.getUserName())
                        .eq(StringUtils.isNotBlank(dto.getStatus()), SysUser::getStatus, dto.getStatus())
                        .orderByDesc(BaseEntity::getCreateOn));
    }

    @Override
    public boolean changePassword(SysUserPasswordDTO user) throws DataException {
        String userCard = StringUtils.isBlank(user.getUserCode()) ? commonMethod.getUser() : user.getUserCode();
        return update(Wrappers.lambdaUpdate(SysUser.class)
                .set(SysUser::getPassword, passwordEncoder.encode(user.getNewPassword()))
                .eq(SysUser::getUserCode, userCard));
    }

    @Override
    public boolean changeStatus(String userCode, Boolean status) {
        return update(Wrappers.lambdaUpdate(SysUser.class)
                .set(SysUser::getStatus, status)
                .eq(SysUser::getUserCode, userCode));
    }

    @Override
    public boolean removeByIdNew(Long id) {
        removeRole(getById(id).getUserCode());
        return removeById(id);
    }

    @Override
    public SysUserVO getUserByCode(String code) {
        SysUser one = getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserCode, code));
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(one, vo);
        return vo;
    }

    @Override
    public Page<SysUserVO> listProjectUser(SysUserDepartmentDTO dto) {
        return baseMapper.selectPageByDepartment(dto.getPage(), dto);
    }

    @Override
    public LoginVO refreshToken(String refreshToken, String userCode) {
        Token token = (Token) redisUtils.getMsg(StaticConstant.LOGIN_USER + userCode + ":" + refreshToken);
        if (Objects.isNull(token)) {
            throw new DataException(DataEnums.TOKEN_IS_NULL);
        }
        SysUser one = getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserCode, token.getUser().getUserCode()));
        String secret = (String) redisUtils.getMsg(StaticConstant.LOGIN_USER + userCode + ":secret");
        return getLoginVO(userCode, secret, one);
    }

    private void removeRole(String userCode) {
        sysUserRoleService.remove(Wrappers.lambdaUpdate(SysUserRole.class).eq(SysUserRole::getUserCard, userCode));
    }

    private String getRedisCodeId(String id) {
        return StaticConstant.REDIS_LOGIN_AUTH_CODE + id + StaticConstant.REDIS_CODE;
    }

    private String getRedisNumId(String id) {
        return StaticConstant.REDIS_LOGIN_AUTH_CODE + id + StaticConstant.REDIS_NUM;
    }

    private String getRedisLoginNumId(String id) {
        return StaticConstant.REDIS_LOGIN_AUTH_CODE + id + StaticConstant.REDIS_LOGIN_NUM;
    }

    private LoginVO getLoginVO(String userCode, String secret, SysUser user) {
        deleteRedisUserInfo(userCode);
        // 角色
        String userName = user.getUserName();
        String role = sysUserRoleService.getRoleByUser(user.getUserCode());
        String accessToken = jwtUtils.generateToken(String.valueOf(userCode), tokenTime, secret);
        String refreshToken = NumUtils.uuid();
        User build = User.builder()
                .userName(userName)
                .userCode(userCode)
                .role(role)
                .ip(commonMethod.getIp())
                .refreshToken(refreshToken)
                .build();
        redisUtils.setMsgDiyTimeOut(StaticConstant.LOGIN_USER + userCode + ":info",
                new Token(accessToken, userName, build),
                Math.toIntExact(tokenTime),
                TimeUnit.SECONDS);
        redisUtils.setMsgDiyTimeOut(StaticConstant.LOGIN_USER + userCode + ":secret",
                secret,
                Math.toIntExact(tokenTime),
                TimeUnit.SECONDS);
        redisUtils.setMsgDiyTimeOut(StaticConstant.LOGIN_USER + userCode + ":refreshKey",
                refreshToken,
                refreshTime,
                TimeUnit.SECONDS);
        redisUtils.setMsgDiyTimeOut(StaticConstant.LOGIN_USER + userCode + ":" + refreshToken,
                new Token(accessToken, userName, build),
                refreshTime,
                TimeUnit.SECONDS);
        redisUtils.deleteByKey(getRedisCodeId(secret));
        redisUtils.deleteByKey(getRedisLoginNumId(userCode));
        log.info("login user :{}", userCode);
        return new LoginVO().setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setName(userName)
                .setUserCode(userCode);
    }

    /**
     * 清空但前用户在redis所有信息
     *
     * @param userCode 用户工号
     */
    public boolean deleteRedisUserInfo(String userCode) {
        boolean byKey = redisUtils.deleteByKey(StaticConstant.LOGIN_USER + userCode + ":info");
        redisUtils.deleteByKey(StaticConstant.LOGIN_USER + userCode + ":secret");
        String msg = (String) redisUtils.getMsg(StaticConstant.LOGIN_USER + userCode + ":refreshKey");
        redisUtils.deleteByKey(StaticConstant.LOGIN_USER + userCode + ":refreshKey");
        if (StringUtils.isNotBlank(msg)) {
            redisUtils.deleteByKey(StaticConstant.LOGIN_USER + userCode + ":" + msg);
        }
        return byKey;
    }
}
