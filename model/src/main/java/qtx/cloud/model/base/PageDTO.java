package qtx.cloud.model.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/10/30 18:39
 */
@Data
public class PageDTO {

  private Integer pageSize;

  private Integer pageNum;

  @JsonIgnore
  public IPage<Object> getPage() {
    return com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO.of(pageNum, pageSize);
  }
}
