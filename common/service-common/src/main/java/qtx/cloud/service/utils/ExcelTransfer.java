package qtx.cloud.service.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.service.excel.ConvertList;
import qtx.cloud.service.excel.DataListener;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 下载对应实体类不允许使用链式调用注解
 *
 * <p>@Accessors(chain = true) 对应实体类实现equals和hashCode方法会自动过滤与数据库重复的数据
 *
 * @author qtx
 * @since 2022/10/30 20:03
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "excel.model")
public class ExcelTransfer<T> {

  private String packageName;

  private int size;

  /**
   * 上传excel
   *
   * @param file 文件
   * @param service 对应实体的service
   * @return 成功与否
   * @throws ClassNotFoundException 对应的实体没找到
   */
  public boolean importExcel(MultipartFile file, IService<T> service)
      throws ClassNotFoundException {
    isEmpty(file);
    Class<?> aClass = getClass(service);
    try {
      EasyExcel.read(file.getInputStream(), aClass, new DataListener<>(service, aClass))
          .sheet()
          .doRead();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    }
    return true;
  }

  /**
   * 上传excel
   *
   * @param file 文件
   * @param service 对应实体的service
   * @param aClass 实体类
   * @return 成功与否
   * @throws ClassNotFoundException 对应的实体没找到
   */
  public boolean importExcel(MultipartFile file, IService<T> service, Class<?> aClass)
      throws ClassNotFoundException {
    isEmpty(file);
    try {
      EasyExcel.read(file.getInputStream(), aClass, new DataListener<>(service, aClass))
          .sheet()
          .doRead();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    }
    return true;
  }

  /**
   * 上传excel
   *
   * @param file 文件
   * @param service 对应实体的service
   * @param list 实现自定义转换方法
   * @return 成功与否
   * @throws ClassNotFoundException 对应的实体没找到
   */
  public boolean importExcel(MultipartFile file, IService<T> service, ConvertList<T> list)
      throws ClassNotFoundException {
    isEmpty(file);
    Class<?> aClass = getClass(service);
    try {
      EasyExcel.read(file.getInputStream(), aClass, new DataListener<>(service, list, aClass))
          .sheet()
          .doRead();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    }
    return true;
  }

  /**
   * 上传excel
   *
   * @param file 文件
   * @param service 对应实体的service
   * @param list 实现自定义转换方法
   * @param aClass 实体类
   * @return 成功与否
   * @throws ClassNotFoundException 对应的实体没找到
   */
  public boolean importExcel(
      MultipartFile file, IService<T> service, ConvertList<T> list, Class<?> aClass)
      throws ClassNotFoundException {
    isEmpty(file);
    try {
      EasyExcel.read(file.getInputStream(), aClass, new DataListener<>(service, list, aClass))
          .sheet()
          .doRead();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    }
    return true;
  }

  /**
   * 多sheet，自定义数据源加表头
   *
   * @param response http
   * @param name 文件名称
   * @param listListMap 表头为key，数据为value
   * @throws IOException
   */
  public void exportExcel(
      HttpServletResponse response,
      String name,
      Map<List<List<String>>, List<List<String>>> listListMap)
      throws IOException {
    setResponse(response, name);
    try (ExcelWriter excelWriter =
        EasyExcel.write(response.getOutputStream())
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            .build()) {
      AtomicInteger i = new AtomicInteger(0);
      listListMap.forEach(
          (k, v) -> {
            WriteSheet writeSheet = EasyExcel.writerSheet(i.get(), "模板" + i).head(k).build();
            excelWriter.write(v, writeSheet);
            i.getAndIncrement();
          });
    }
  }

  /**
   * 下载excel，多表，单sheet
   *
   * @param response http
   * @param name 文件名称
   * @param sheet 表名
   * @param list 实体:数据
   */
  public void exportExcel(
      HttpServletResponse response, String name, String sheet, List<ExcelList> list)
      throws IOException {
    setResponse(response, name);
    try (ExcelWriter excelWriter =
        EasyExcel.write(response.getOutputStream())
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            .build()) {
      AtomicInteger i = new AtomicInteger(0);
      WriteSheet writeSheet = EasyExcel.writerSheet(sheet).needHead(Boolean.FALSE).build();
      list.forEach(
          e -> {
            WriteTable build = EasyExcel.writerTable(i.get()).head(e.getListsHead()).build();
            i.getAndIncrement();
            excelWriter.write(e.getListsData(), writeSheet, build);
          });
    }
  }

  /**
   * 下载excel，多实体，单sheet
   *
   * @param response http
   * @param name 文件名称
   * @param sheet 表名
   * @param map 实体:数据
   */
  public void exportExcel(
      HttpServletResponse response, String name, String sheet, Map<Class<?>, ExcelClass> map)
      throws IOException {
    setResponse(response, name);
    try (ExcelWriter excelWriter =
        EasyExcel.write(response.getOutputStream())
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            .build()) {
      AtomicInteger i = new AtomicInteger(0);
      WriteSheet writeSheet = EasyExcel.writerSheet(sheet).needHead(Boolean.FALSE).build();
      map.forEach(
          (k, v) -> {
            WriteTable build = EasyExcel.writerTable(i.get()).head(k).build();
            i.getAndIncrement();
            excelWriter.write(v.getData(), writeSheet, build);
          });
    }
  }

  /**
   * 下载excel，多表，多sheet
   *
   * @param response http
   * @param name 文件名称
   * @param list 实体:数据
   */
  public void exportExcel(HttpServletResponse response, String name, List<ExcelList> list)
      throws IOException {
    setResponse(response, name);
    try (ExcelWriter excelWriter =
        EasyExcel.write(response.getOutputStream())
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            .build()) {
      AtomicInteger i = new AtomicInteger(0);
      list.forEach(
          e -> {
            WriteSheet writeSheet =
                EasyExcel.writerSheet(i.get(), e.getSheet()).head(e.getListsHead()).build();
            i.getAndIncrement();
            excelWriter.write(e.getListsData(), writeSheet);
          });
    }
  }

  /**
   * 下载excel，多实体，多sheet
   *
   * @param response http
   * @param map 实体:数据
   */
  public void exportExcel(HttpServletResponse response, Map<Class<?>, ExcelClass> map)
      throws IOException {
    setResponse(response, "name");
    try (ExcelWriter excelWriter =
        EasyExcel.write(response.getOutputStream())
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            .build()) {
      AtomicInteger i = new AtomicInteger(0);
      map.forEach(
          (k, v) -> {
            WriteSheet writeSheet = EasyExcel.writerSheet(i.get(), v.getSheet()).head(k).build();
            i.getAndIncrement();
            excelWriter.write(v.getData(), writeSheet);
          });
    }
  }

  /**
   * 下载excel
   *
   * @param response http
   * @param list 需要下载的数据
   * @param name 文件名称
   * @param sheet 表名
   */
  public void exportExcel(
      HttpServletResponse response, List<T> list, String name, String sheet, IService<T> service)
      throws ClassNotFoundException {
    String className = service.getClass().getName();
    String s = className.substring(size, className.length() - 11);
    Class<?> aClass = Class.forName(packageName + s);
    export(response, list, name, sheet, aClass);
  }

  /**
   * 下载excel
   *
   * @param response http
   * @param list 需要下载的数据
   * @param name 文件名称
   * @param sheet 表名
   * @param aClass 实体类
   */
  public void exportExcel(
      HttpServletResponse response, List<T> list, String name, String sheet, Class<?> aClass)
      throws ClassNotFoundException {
    export(response, list, name, sheet, aClass);
  }

  private void export(
      HttpServletResponse response, List<T> list, String name, String sheet, Class<?> aClass) {
    try {
      setResponse(response, name);
      EasyExcel.write(response.getOutputStream(), aClass)
          .sheet(sheet)
          .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
          .doWrite(list);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void isEmpty(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new DataException(DataEnums.DATA_IS_ABNORMAL);
    }
  }

  private void setResponse(HttpServletResponse response, String name)
      throws UnsupportedEncodingException {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setCharacterEncoding("utf-8");
    // 这里URLEncoder.encode可以防止中文乱码 当然和easy excel没有关系
    String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
  }

  private Class<?> getClass(IService<T> service) throws ClassNotFoundException {
    String name = service.getClass().getName();
    String s = name.substring(size, name.length() - 11);
    return Class.forName(packageName + s);
  }

  @Data
  @Builder
  static class ExcelList {
    private String sheet;
    private List<List<String>> listsHead;
    private List<List<?>> listsData;
  }

  @Data
  @Builder
  static class ExcelClass {
    private String sheet;
    private List<?> data;
  }
}
