# EasyExcel 导入导出

## maven 依赖

```
<!-- 表格导入导出 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.1.1</version>
</dependency>
 <!-- 读取配置文件，可选配置(非必要) -->
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-configuration-processor</artifactId>
     <optional>true</optional>
 </dependency>
 <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
 </dependency>
```

## 导入导出 工具类

> @ConfigurationProperties(prefix = "excel.model")
>
> 读取配置文件
> size = qtx.cloud.auth.service.impl

```properties
excel:
model:
package-name:qtx.cloud.auth.entity
size:27
```

```java
package qtx.cloud.service.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;
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
   * 下载excel，多实体，多sheet
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
    private List<List<Object>> listsData;
  }

  @Data
  @Builder
  static class ExcelClass {
    private String sheet;
    private List<?> data;
  }
}
```

```java
package qtx.cloud.service.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author qtx
 * @since 2022/10/30 20:00
 */
@Slf4j
public class DataListener<E> implements ReadListener<E> {
    /** 每隔n条存储数据库，实际使用中可以100条，然后清理list，方便内存回收 */
    private static final int BATCH_COUNT = 10000;
    /** 缓存的数据，与普通list无异，只是防止多次扩容增加开销 */
    private List<E> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /** 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。 */
    private final IService<E> service;
    /** excel数据与table不一致，手动实现转换方法 */
    private ConvertList<E> convert;
    /** 错误标识 */
    private boolean stringError;
    /** 导入实体 */
    private final Class<?> entity;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param service
     */
    public DataListener(IService<E> service, Class<?> aClass) {
        this.service = service;
        this.entity = aClass;
    }

    /**
     * 需要转换excel数据，请使用这个构造方法。
     *
     * @param service
     * @param convert
     */
    public DataListener(IService<E> service, ConvertList<E> convert, Class<?> aClass) {
        this.service = service;
        this.convert = convert;
        this.entity = aClass;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(E data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        try {
            if (isNull(data)) {
                log.info("添加一条数据到备选集合:{}", JSON.toJSONString(data));
                cachedDataList.add(data);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 匹配表头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Set<String> head;
        try {
            head = excelHead();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Set<String> finalHead = head;
        headMap.forEach((k, v) -> {
            if (!finalHead.contains(v.getStringValue())) {
                stringError = false;
            }
        });
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }

    /** 加上存储数据库 */
    private void saveData() {
        List<E> saveList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        if (convert != null) {
            log.info("{}条数据，开始转换数据格式。", cachedDataList.size());
            saveList.addAll(new HashSet<>(convert.convert(cachedDataList)));
        } else {
            // 默认逻辑删除 is_delete 字段自行替换
            Set<E> set = new HashSet<>(service.query().eq("is_delete", "0").list());
            new HashSet<>(cachedDataList).parallelStream().forEach(s -> {
                if (!set.contains(s)) {
                    saveList.add(s);
                }
            });
            log.info("{}条数据，被过滤掉！", cachedDataList.size() - saveList.size());
        }
        log.info("{}条数据，开始存储数据库！", saveList.size());
        service.saveBatch(saveList);
        log.info("存储数据库成功！");
    }

    /**
     * 过滤掉空数据
     *
     * @param data 实体类
     * @return 是否为空
     *
     * @throws IllegalAccessException
     */
    private boolean isNull(E data) throws IllegalAccessException {
        Class<?> aClass = data.getClass();
        Field[] fields = aClass.getDeclaredFields();
        int num = 0;
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                num++;
                continue;
            }
            field.setAccessible(true);
            Object o = field.get(data);
            if (Objects.isNull(o)) {
                num++;
            }
        }
        return num != fields.length;
    }

    /**
     * 获取excel表头
     *
     * @return
     *
     * @throws ClassNotFoundException
     */
    public Set<String> excelHead() throws ClassNotFoundException {
        HashSet<String> set = new HashSet<>(20);
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                String[] value = field.getAnnotation(ExcelProperty.class).value();
                set.add(value[value.length - 1]);
            }
        }
        return set;
    }
}
```

```java
package qtx.cloud.service.excel;

import java.util.List;

/**
 * @author qtx
 * @since 2022/07/30 20:05
 */
public interface ConvertList<E> {

    /**
     * excel与数据库不一致，手动实现转换方式
     *
     * @param list 初始集合
     * @return 处理后集合
     */
    List<E> convert(List<E> list);
}
```

