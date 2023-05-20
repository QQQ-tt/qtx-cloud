package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class TestLeftServiceImpl extends ServiceImpl<TestLeftMapper, TestLeft> implements TestLeftService {

  @Override
  public List<TestLeft> listNew() {
    long l = System.currentTimeMillis();
    List<TestLeft> testLefts = baseMapper.selectListNew();
    long l1 = System.currentTimeMillis();
    log.error("time:{}", l1 - l);
    return testLefts;
  }

  @Override
  public List<TestLeft> listNewPage() {
    long l = System.currentTimeMillis();
    List<TestLeft> testLefts = baseMapper.selectListNewPage(new Page<>(1, 10));
    long l1 = System.currentTimeMillis();
    log.error("time:{}", l1 - l);
    return testLefts;
  }

  @Override
  public List<TestLeft> listNew2() {
    long l = System.currentTimeMillis();
    List<TestLeft> testLefts = baseMapper.selectListNew2();
    long l1 = System.currentTimeMillis();
    log.error("time:{}", l1 - l);
    return testLefts;
  }

  @Override
  public List<TestLeft> listNew2Page() {
    long l = System.currentTimeMillis();
    List<TestLeft> testLefts = baseMapper.selectListNew2Page(new Page<>(1, 10));
    long l1 = System.currentTimeMillis();
    log.error("time:{}", l1 - l);
    return testLefts;
  }

  @Override
  public List<TestLeft> listNew3() {
    return baseMapper.selectListNew3();
  }
}
