package qtx.cloud.oss.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;

/**
 * @author qtx
 * @since 2023/3/17 17:17
 */
public class ZipUtil {

  private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

  public static void zip(OutputStream out, String[] paths, InputStream[] ins) {
    ZipOutputStream zipOutputStream = null;

    try {
      zipOutputStream = getZipOutputStream(out);
      zip(zipOutputStream, paths, ins);
    } finally {
      ZipUtil.close(zipOutputStream);
    }
  }

  private static void closeEntry(ZipOutputStream out) {
    try {
      out.closeEntry();
    } catch (IOException ignored) {
    }
  }

  public static void zip(ZipOutputStream zipOutputStream, String[] paths, InputStream[] ins)
      throws RuntimeException {
    if (ZipUtil.isNotEmpty(paths) && ZipUtil.isNotEmpty(ins)) {
      if (paths.length != ins.length) {
        throw new IllegalArgumentException("Paths length is not equals to ins length !");
      } else {
        for (int i = 0; i < paths.length; ++i) {
          add(ins[i], paths[i], zipOutputStream);
        }
      }
    } else {
      throw new IllegalArgumentException("Paths or ins is empty !");
    }
  }

  public static <T> boolean isNotEmpty(T[] array) {
    return array != null && array.length != 0;
  }

  private static void add(InputStream in, String path, ZipOutputStream out)
      throws RuntimeException {
    if (null != in) {
      try {
        out.putNextEntry(new ZipEntry(path));
        IOUtils.copy(in, out);
      } catch (IOException var7) {
        throw new RuntimeException(var7);
      } finally {
        ZipUtil.close(in);
        closeEntry(out);
      }
    }
  }

  private static ZipOutputStream getZipOutputStream(OutputStream out) {
    return out instanceof ZipOutputStream
        ? (ZipOutputStream) out
        : new ZipOutputStream(out, ZipUtil.DEFAULT_CHARSET);
  }

  public static void close(Closeable closeable) {
    if (null != closeable) {
      try {
        closeable.close();
      } catch (Exception ignored) {
      }
    }
  }
}
