package qtx.cloud.java.annotation;

import qtx.cloud.java.enums.ExcelMapAllEnum;

import java.lang.annotation.*;

/**
 * @author qtx
 * @since 2023/4/1 23:50
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelMap {
  ExcelMapAllEnum value() default ExcelMapAllEnum.NULL;
}
