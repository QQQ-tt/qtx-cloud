package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.entity.TestLeft;
import qtx.cloud.auth.mapper.TestLeftMapper;
import qtx.cloud.auth.service.TestLeftService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-05-20
 */
@Service
public class TestLeftServiceImpl extends ServiceImpl<TestLeftMapper, TestLeft> implements TestLeftService {

  @Override
  public List<TestLeft> listNew() {
    return baseMapper.selectListNew();
  }

  @Override
  public List<TestLeft> listNew2() {
    return baseMapper.selectListNew2();
  }

  @Override
  public List<TestLeft> listNew3() {
    return baseMapper.selectListNew3();
  }
}
