package qtx.cloud.auth.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.entity.SysUser;
import qtx.cloud.auth.entity.SysUserInfo;
import qtx.cloud.auth.entity.SysUserRole;
import qtx.cloud.auth.entity.User;
import qtx.cloud.auth.mapper.SysUserMapper;
import qtx.cloud.auth.service.SysUserInfoService;
import qtx.cloud.auth.service.SysUserRoleService;
import qtx.cloud.auth.service.SysUserService;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.base.BaseEntity;
import qtx.cloud.model.bo.auth.UserBO;
import qtx.cloud.model.dto.auth.*;
import qtx.cloud.model.vo.auth.CreateVO;
import qtx.cloud.model.vo.auth.LoginVO;
import qtx.cloud.model.vo.auth.SysUserVO;
import qtx.cloud.service.utils.*;

/**
 * 服务实现类
 *
 * @author qtx
 * @since 2022-08-30
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {

  private final JwtUtils jwtUtils;

  private final PasswordEncoder passwordEncoder;

  private final CommonMethod commonMethod;

  private final SysUserRoleService sysUserRoleService;

  private final RedisUtils redisUtils;

  private final SysUserInfoService sysUserInfoService;

  @Value("${time.token}")
  private Long tokenTime;

  @Value("${time.refresh}")
  private Integer refreshTime;

  public SysUserServiceImpl(
      JwtUtils jwtUtils,
      PasswordEncoder passwordEncoder,
      CommonMethod commonMethod,
      SysUserRoleService sysUserRoleService,
      RedisUtils redisUtils,
      SysUserInfoService sysUserInfoService) {
    this.jwtUtils = jwtUtils;
    this.passwordEncoder = passwordEncoder;
    this.commonMethod = commonMethod;
    this.sysUserRoleService = sysUserRoleService;
    this.redisUtils = redisUtils;
    this.sysUserInfoService = sysUserInfoService;
  }

  @Override
  public LoginVO login(LoginDTO user) throws DataException {
    String userCard = user.getUserCard();
    // 登录试错
    Integer loginNumRedis = (Integer) redisUtils.getMsg(getRedisLoginErrorNumKey(userCard));
    int loginNum = Optional.ofNullable(loginNumRedis).orElse(0);
    if (loginNum == StaticConstant.LOGIN_ERROR_UPPER_LIMIT) {
      log.info("userCard:{},{}", userCard, DataEnums.USER_LOGIN_LOCKING);
      throw new DataException(DataEnums.USER_LOGIN_LOCKING);
    }
    // 验证码
    String secret = user.getSession();
    String msg = (String) redisUtils.getMsg(getRedisAuthCodeKey(secret));
    if (StringUtils.isBlank(msg)) {
      throw new DataException(DataEnums.AUTH_CODE_EXPIRED);
    }
    if (!user.getAuthCode().equalsIgnoreCase(msg)) {
      int i = (int) redisUtils.getMsg(getRedisAuthErrorNumKey(secret));
      if (i > 0) {
        redisUtils.setMsgDiyTimeOut(getRedisAuthErrorNumKey(secret), i - 1, 1, TimeUnit.MINUTES);
        throw new DataException(DataEnums.USER_CODE_FAIL);
      } else {
        throw new DataException(DataEnums.AUTH_CODE_EXPIRED);
      }
    }
    // 密码校验
    Map<Object, Object> hashMsg =
        redisUtils.getHashMsg(StaticConstant.SYS_USER + userCard + StaticConstant.REDIS_INFO);
    String password = null;
    String userName = null;
    if (hashMsg == null) {
      SysUser one =
          baseMapper.selectOne(
              Wrappers.lambdaQuery(SysUser.class)
                  .eq(SysUser::getUserCard, userCard)
                  .eq(SysUser::getStatus, true));
      if (one != null) {
        password = one.getPassword();
        userName = one.getUserName();
      }
    } else {
      password = (String) hashMsg.get("password");
      userName = (String) hashMsg.get("userName");
    }
    if (Objects.isNull(userName)) {
      throw new DataException(DataEnums.USER_IS_NULL);
    }
    if (!passwordEncoder.matches(user.getPassword(), password)) {
      redisUtils.setMsgDiyTimeOut(
          getRedisLoginErrorNumKey(userCard),
          loginNum + 1,
          StaticConstant.LOGIN_ERROR_LOCK_LIMIT,
          TimeUnit.MINUTES);
      throw new DataException(DataEnums.WRONG_PASSWORD);
    }
    return getLoginVO(userCard, secret, userName);
  }

  @Override
  public SysUserVO getOneOnline() {
    String user = commonMethod.getUser();
    return baseMapper.selectUserByUserCard(user);
  }

  @Override
  public void getAuthCode(HttpServletResponse response, String session) throws IOException {
    // 利用图片工具生成图片
    // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
    Object[] objs =
        VerifyUtil.newBuilder()
            .setWidth(120)
            .setHeight(35)
            .setSize(4)
            .setLines(1)
            .setFontSize(25)
            .setBackgroundColor(Color.WHITE)
            .build()
            .createImage();
    redisUtils.setMsgDiyTimeOut(getRedisAuthCodeKey(session), objs[0], 1, TimeUnit.MINUTES);
    redisUtils.setMsgDiyTimeOut(getRedisAuthErrorNumKey(session), 3, 1, TimeUnit.MINUTES);
    // 将图片输出给浏览器
    BufferedImage image = (BufferedImage) objs[1];
    response.setContentType("image/png");
    OutputStream os = response.getOutputStream();
    ImageIO.write(image, "png", os);
  }

  @Override
  @SentinelResource(value = "sayHello")
  public CreateVO createUser(CreateUserDTO user) throws DataException {
    String password = user.getPassword();
    if (StringUtils.isBlank(user.getUserName()) && StringUtils.isBlank(password)) {
      throw new DataException(DataEnums.DATA_IS_ABNORMAL);
    }
    Set<String> set = list().stream().map(SysUser::getUserCard).collect(Collectors.toSet());
    boolean flag;
    do {
      String s = String.valueOf(NumUtils.numUserCard());
      flag = set.contains(s);
      if (!flag) {
        user.setUserCard(s);
      }
    } while (flag);
    user.setPassword(passwordEncoder.encode(password));
    SysUser sysUser =
        SysUser.builder()
            .userCard(user.getUserCard())
            .userName(user.getUserName())
            .password(user.getPassword())
            .build();
    if (save(sysUser)) {
      sysUserInfoService.save(SysUserInfo.builder().userCard(user.getUserCard()).build());
      addUserToRedis(sysUser);
      return new CreateVO(user.getUserName(), user.getUserCard(), password);
    } else {
      throw new DataException(DataEnums.DATA_INSERT_FAIL);
    }
  }

  @Override
  public boolean saveOrUpdateNew(CreateSysUserDTO user) throws DataException {
    List<Integer> roleId = user.getRoleId();
    removeRole(user.getUserCard());
    sysUserRoleService.addRoleWithUser(
        new UserRolesDTO().setUserCard(user.getUserCard()).setRoleIds(roleId));
    SysUser sysUser = new SysUser();
    SysUserInfo sysUserInfo = new SysUserInfo();
    BeanUtils.copyProperties(user, sysUser);
    sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
    BeanUtils.copyProperties(user, sysUserInfo);
    try {
      if (sysUser.getId() == null) {
        sysUserInfoService.save(sysUserInfo);
        addUserToRedis(sysUser);
      } else {
        sysUserInfoService.update(
            sysUserInfo,
            Wrappers.lambdaUpdate(SysUserInfo.class)
                .eq(SysUserInfo::getUserCard, sysUser.getUserCard()));
      }
      return saveOrUpdate(sysUser);
    } catch (Exception e) {
      throw new DataException(DataEnums.DATA_INSERT_FAIL);
    }
  }

  @Override
  public boolean logout() {
    return removeRedisUserOnline(commonMethod.getUser());
  }

  @Override
  public Page<SysUserVO> listUserPage(SysUserDTO dto) {
    return baseMapper.selectPageNew(
        dto.getPage(),
        Wrappers.lambdaQuery(SysUser.class)
            .eq(BaseEntity::getDeleteFlag, false)
            .like(
                StringUtils.isNotBlank(dto.getUserCard()), SysUser::getUserCard, dto.getUserCard())
            .like(
                StringUtils.isNotBlank(dto.getUserName()), SysUser::getUserName, dto.getUserName())
            .eq(StringUtils.isNotBlank(dto.getStatus()), SysUser::getStatus, dto.getStatus())
            .orderByDesc(BaseEntity::getCreateOn));
  }

  @Override
  public boolean changePassword(SysUserPasswordDTO user) throws DataException {
    String userCard =
        StringUtils.isBlank(user.getUserCard()) ? commonMethod.getUser() : user.getUserCard();
    SysUser sysUser =
        Optional.ofNullable(
                getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserCard, userCard)))
            .orElse(new SysUser());
    if (passwordEncoder.matches(user.getOldPassword(), sysUser.getPassword())) {
      String encode = passwordEncoder.encode(user.getNewPassword());
      redisUtils.setHashMsg(
          StaticConstant.SYS_USER + userCard + StaticConstant.REDIS_INFO, "password", encode);
      return update(
          Wrappers.lambdaUpdate(SysUser.class)
              .set(SysUser::getPassword, encode)
              .eq(SysUser::getUserCard, userCard));
    } else {
      throw new DataException(DataEnums.WRONG_PASSWORD);
    }
  }

  @Override
  public boolean changeStatus(String userCard, Boolean status) {
    if (!status) {
      removeUserOnRedis(userCard);
      removeRedisUserOnline(userCard);
    }
    return update(
        Wrappers.lambdaUpdate(SysUser.class)
            .set(SysUser::getStatus, status)
            .eq(SysUser::getUserCard, userCard));
  }

  @Override
  public boolean removeByIdNew(Long id) {
    SysUser user = getById(id);
    if (user == null) {
      throw new DataException(DataEnums.USER_REMOVE_FAIL);
    }
    String userCard = user.getUserCard();
    removeRole(userCard);
    removeUserInfo(userCard);
    removeUserOnRedis(userCard);
    removeRedisUserOnline(userCard);
    return removeById(id);
  }

  @Override
  public SysUserVO getUserByCard(String card) {
    SysUser one = getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserCard, card));
    SysUserVO vo = new SysUserVO();
    BeanUtils.copyProperties(one, vo);
    return vo;
  }

  @Override
  public Page<SysUserVO> listProjectUser(SysUserDepartmentDTO dto) {
    return baseMapper.selectPageByDepartment(dto.getPage(), dto);
  }

  @Override
  public LoginVO refreshToken(String refreshToken, String userCard) {
    Map<Object, Object> user =
        redisUtils.getHashMsg(StaticConstant.LOGIN_USER + userCard + StaticConstant.REDIS_INFO);
    if (Objects.isNull(user)) {
      throw new DataException(DataEnums.TOKEN_IS_NULL);
    }
    return getLoginVO(userCard, (String) user.get("secret"), (String) user.get("userName"));
  }

  private void removeRole(String userCard) {
    sysUserRoleService.remove(
        Wrappers.lambdaUpdate(SysUserRole.class).eq(SysUserRole::getUserCard, userCard));
  }

  private void removeUserInfo(String userCard) {
    sysUserInfoService.remove(
        Wrappers.lambdaQuery(SysUserInfo.class).eq(SysUserInfo::getUserCard, userCard));
  }

  private String getRedisAuthCodeKey(String userCard) {
    return StaticConstant.REDIS_LOGIN_AUTH_CODE + userCard + StaticConstant.REDIS_CODE;
  }

  private String getRedisAuthErrorNumKey(String userCard) {
    return StaticConstant.REDIS_LOGIN_AUTH_CODE + userCard + StaticConstant.REDIS_NUM;
  }

  private String getRedisLoginErrorNumKey(String userCard) {
    return StaticConstant.REDIS_LOGIN_AUTH_CODE + userCard + StaticConstant.REDIS_LOGIN_NUM;
  }

  private LoginVO getLoginVO(String userCard, String secret, String name) {
    removeRedisUserOnline(userCard);
    // 角色
    String role = sysUserRoleService.getRoleByUser(userCard);
    String accessToken = jwtUtils.generateToken(String.valueOf(userCard), tokenTime, secret);
    String refreshToken = NumUtils.uuid();
    User build =
        User.builder()
            .userName(name)
            .userCard(userCard)
            .role(role)
            .ip(commonMethod.getIp())
            .secret(secret)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .time(LocalDateTime.now().toString())
            .build();
    redisUtils.setHashMsgAllTimeOut(
        StaticConstant.LOGIN_USER + userCard + StaticConstant.REDIS_INFO,
        JSONObject.parseObject(JSON.toJSONBytes(build), Map.class),
        refreshTime,
        TimeUnit.SECONDS);
    redisUtils.deleteByKey(getRedisAuthCodeKey(secret));
    redisUtils.deleteByKey(getRedisLoginErrorNumKey(userCard));
    log.info("login user :{}", userCard);
    return new LoginVO()
        .setAccessToken(accessToken)
        .setRefreshToken(refreshToken)
        .setName(name)
        .setUserCard(userCard);
  }

  /**
   * 清空但前用户在redis登录信息
   *
   * @param userCard 用户账户
   */
  public boolean removeRedisUserOnline(String userCard) {
    return redisUtils.deleteByKey(StaticConstant.LOGIN_USER + userCard + StaticConstant.REDIS_INFO);
  }

  /**
   * 实时移除redis用户
   *
   * @param userCard 账户
   */
  public void removeUserOnRedis(String userCard) {
    redisUtils.deleteByKey(StaticConstant.SYS_USER + userCard + StaticConstant.REDIS_INFO);
  }

  /**
   * 实时添加用户到redis
   *
   * @param sysUser 用户基本信息
   */
  private void addUserToRedis(SysUser sysUser) {
    UserBO userBO =
        UserBO.builder()
            .id(sysUser.getId())
            .userName(sysUser.getUserName())
            .userCard(sysUser.getUserCard())
            .password(sysUser.getPassword())
            .build();
    redisUtils.setHashMsgAll(
        StaticConstant.SYS_USER + sysUser.getUserCard() + StaticConstant.REDIS_INFO,
        JSONObject.parseObject(JSON.toJSONString(userBO), Map.class));
  }
}
