package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.cloud.auth.entity.TestLeft;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2023-05-20
 */
@Mapper
public interface TestLeftMapper extends BaseMapper<TestLeft> {

  List<TestLeft> selectListNew();
  List<TestLeft> selectListNew2();
  List<TestLeft> selectListNew3();


}
