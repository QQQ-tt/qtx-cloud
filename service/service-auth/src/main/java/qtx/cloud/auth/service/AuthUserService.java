package qtx.cloud.auth.service;

import qtx.cloud.model.vo.auth.AuthVO;

/**
 * @author qtx
 * @since 2022/11/28 14:42
 */
public interface AuthUserService {

  /**
   * 认证token真伪及解析及权限认证
   *
   * @param token 用户token
   * @param ip 用户ip
   * @param userCard 账户
   * @param url 请求地址
   * @return 用户账户
   */
  AuthVO authToken(String token, String ip, String userCard, String url);
}
