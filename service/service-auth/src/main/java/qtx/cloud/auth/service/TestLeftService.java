package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.auth.entity.TestLeft;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qtx
 * @since 2023-05-20
 */
public interface TestLeftService extends IService<TestLeft> {

  List<TestLeft> listNew();
  List<TestLeft> listNew2();
  List<TestLeft> listNew3();

}
