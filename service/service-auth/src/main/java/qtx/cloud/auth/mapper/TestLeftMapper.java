package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
  List<TestLeft> selectListNewPage(Page<TestLeft> page);
  List<TestLeft> selectListNew2();
  List<TestLeft> selectListNew2Page(Page<TestLeft> page);
  List<TestLeft> selectListNew3();


}
