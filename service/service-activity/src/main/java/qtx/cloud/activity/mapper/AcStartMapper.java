package qtx.cloud.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qtx.cloud.activity.entity.AcStart;
import qtx.cloud.model.bo.activity.AcBO;
import qtx.cloud.model.vo.activity.AcToDoVO;
import qtx.cloud.model.vo.activity.TaskVO;

/**
 * 流程启动表 Mapper 接口
 *
 * @author qtx
 * @since 2023-03-15
 */
@Mapper
public interface AcStartMapper extends BaseMapper<AcStart> {

  /**
   * 查询流程ac
   *
   * @param acUuid 流程id
   * @param flag 初始化类型
   * @param group 组编号
   * @return 初始流程集合
   */
  List<AcBO> selectAc(
      @Param("acUuid") String acUuid, @Param("flag") Boolean flag, @Param("group") Integer group);

  /**
   * 查询待办
   *
   * @param acUuid 流程uuid
   * @param userCode 用户工号
   * @return 待办集合
   */
  List<AcToDoVO> selectToDo(@Param("acUuid") String acUuid, @Param("userCode") String userCode);

  /**
   * 查询流程详情
   *
   * @param taskUuid 任务uuid
   * @return 流程集合
   */
  List<TaskVO> selectTask(@Param("taskUuid") String taskUuid);
}
