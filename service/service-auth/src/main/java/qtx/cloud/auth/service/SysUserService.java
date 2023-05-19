package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import qtx.cloud.auth.entity.SysUser;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.dto.auth.*;
import qtx.cloud.model.vo.auth.CreateVO;
import qtx.cloud.model.vo.auth.LoginVO;
import qtx.cloud.model.vo.auth.SysUserVO;

/**
 * 服务类
 *
 * @author qtx
 * @since 2022-08-30
 */
public interface SysUserService extends IService<SysUser> {

  /**
   * 登录
   *
   * @param user 用户信息
   * @return 用户基本信息
   * @throws DataException 用户验证失败
   */
  LoginVO login(LoginDTO user) throws DataException;

  /**
   * 获取当前登录用户详情
   *
   * @return 用户信息
   */
  SysUserVO getOneOnline();

  /**
   * 获取验证码
   *
   * @param response 响应
   * @param session  请求uuid
   */
  void getAuthCode(HttpServletResponse response, String session) throws IOException;

  /**
   * 创建用户
   *
   * @param user 用户信息
   * @return 用户信息
   * @throws DataException 数据插入失败
   */
  CreateVO createUser(CreateUserDTO user) throws DataException;

  /**
   * 添加和修改用户
   *
   * @param user 用户实体
   * @return true or false
   * @throws DataException 数据插入失败
   */
  boolean saveOrUpdateNew(CreateSysUserDTO user) throws DataException;

  /**
   * 退出登录
   *
   * @return true or false
   */
  boolean logout();

  /**
   * 分页查询用户信息
   *
   * @param dto
   * @return
   */
  Page<SysUserVO> listUserPage(SysUserDTO dto);

  /**
   * 修改密码
   *
   * @param user 用户实体
   * @return true or false
   * @throws DataException 密码错误
   */
  boolean changePassword(SysUserPasswordDTO user) throws DataException;

  /**
   * 修改状态
   *
   * @param userCard 账户
   * @param status   状态码
   * @return
   */
  boolean changeStatus(String userCard, Boolean status);

  /**
   * 通过id删除
   *
   * @param id 用户id
   * @return true or false
   */
  boolean removeByIdNew(Long id);

  /**
   * 获取用户信息
   *
   * @param card 账户
   * @return 用户信息
   */
  SysUserVO getUserByCard(String card);

  /**
   * 通过科室
   *
   * @param dto
   * @return
   */
  Page<SysUserVO> listProjectUser(SysUserDepartmentDTO dto);

  /**
   * 刷新token
   *
   * @param refreshToken
   * @param userCard
   * @return
   */
  LoginVO refreshToken(String refreshToken, String userCard);
}
