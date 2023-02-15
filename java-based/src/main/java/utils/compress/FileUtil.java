package utils.compress;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@Slf4j
public class FileUtil {
  public static final int BUFFER_SIZE = 1024;

  private FileUtil() {}

  // region 文件夹递归压缩

  /**
   * 文件夹递归压缩
   *
   * @param srcDir 文件夹路径
   * @param out 输出流
   * @param keepDirStructure 是否保持原本文件结构
   */
  public static void toZip(String srcDir, OutputStream out, boolean keepDirStructure) {

    long start = System.currentTimeMillis();
    try (ZipOutputStream zos = new ZipOutputStream(out)) {
      File sourceFile = new File(srcDir);
      compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
      long end = System.currentTimeMillis();
      log.info("压缩完成，耗时：" + (end - start) + " ms");
    } catch (Exception e) {
      throw new RuntimeException("zip error from ZipUtils", e);
    }
  }

  @SneakyThrows
  private static void compress(
      File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) {

    byte[] buf = new byte[BUFFER_SIZE];

    if (sourceFile.isFile()) {
      // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
      zos.putNextEntry(new ZipEntry(name));
      // copy文件到zip输出流中
      int length;
      try (FileInputStream in = new FileInputStream(sourceFile)) {
        while ((length = in.read(buf)) != -1) {
          zos.write(buf, 0, length);
        }
        zos.closeEntry();
      }
    } else {
      File[] listFiles = sourceFile.listFiles();
      if (listFiles == null || listFiles.length == 0) {
        // 需要保留原来的文件结构时,需要对空文件夹进行处理
        if (Boolean.TRUE.equals(keepDirStructure)) {
          // 空文件夹的处理
          zos.putNextEntry(new ZipEntry(name + File.separator));
          // 没有文件，不需要文件的copy
          zos.closeEntry();
        }
      } else {
        for (File file : listFiles) {
          // 判断是否需要保留原来的文件结构
          if (Boolean.TRUE.equals(keepDirStructure)) {
            // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
            // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
            compress(file, zos, name + File.separator + file.getName(), Boolean.TRUE);
          } else {
            compress(file, zos, file.getName(), keepDirStructure);
          }
        }
      }
    }
  }

  /**
   * 压缩成ZIP
   *
   * @param srcFiles 需要压缩的文件列表
   * @param out 压缩文件输出流
   * @throws RuntimeException 压缩失败会抛出运行时异常
   */
  @SneakyThrows
  public static void toZip(List<File> srcFiles, OutputStream out) {
    long start = System.currentTimeMillis();
    byte[] buffer = new byte[BUFFER_SIZE];
    try (ZipOutputStream zos = new ZipOutputStream(out)) {
      for (File srcFile : srcFiles) {
        zos.putNextEntry(new ZipEntry(srcFile.getName()));
        try (FileInputStream in = new FileInputStream(srcFile)) {
          int length;
          while ((length = in.read(buffer)) != -1) {
            zos.write(buffer, 0, length);
          }
          zos.closeEntry();
        }
      }
      long end = System.currentTimeMillis();
      log.info("压缩完成，耗时：" + (end - start) + " ms");
    } catch (Exception e) {
      throw new RuntimeException("zip error from ZipUtils", e);
    }
  }

  // endregion

  // region 解压缩文件

  /**
   * 功能:解压缩
   *
   * @param descDir：解压后的目标目录
   */
  @SneakyThrows
  public static void unZipFiles(String source, String descDir) {
    StopWatch stopWatch = new StopWatch("Unzip Files");
    stopWatch.start("Unzip Processing");

    Path path = Path.of(source);
    if (Files.notExists(path)) {
      Files.createFile(path);
    }

    try (ZipFile zipFile = new ZipFile(path.toString())) {
      for (Enumeration<? extends ZipEntry> entries = zipFile.entries();
          entries.hasMoreElements(); ) {
        ZipEntry entry = entries.nextElement();
        String zipEntryName = entry.getName();

        try (OutputStream outputStream = new FileOutputStream(descDir + zipEntryName);
            InputStream inputStream = zipFile.getInputStream(entry)) {
          byte[] buffer = new byte[BUFFER_SIZE];
          int length = 0;
          while (length == inputStream.read(buffer)) {
            outputStream.write(buffer, 0, length);
          }
          stopWatch.stop();
          log.info("解压缩完成.{}ms", stopWatch.getTotalTimeMillis());
        }
      }
    }
  }

  // endregion

  // region 二进制读取
  public static byte[] BinaryReader(String filePath) throws IOException {
    File filename = new File(filePath);
    try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename))) {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BUFFER_SIZE);
      byte[] buffer = new byte[BUFFER_SIZE];
      int length;
      while ((length = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
      }
      return outputStream.toByteArray();
    }
  }
  // endregion

  // region 读取文本类型文件
  public static List<String> readFromTextFile(String pathname) throws IOException {
    List<String> strArray = new ArrayList<>();
    File filename = new File(pathname);
    InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
    try (BufferedReader br = new BufferedReader(reader)) {

      String line;
      line = br.readLine();
      while (line != null) {
        strArray.add(line);
        line = br.readLine();
      }
      return strArray;
    }
  }
  // endregion

  // region 删除文件

  public static boolean deleteFile(String pathName) {
    try {
      Path path = Paths.get(pathName);
      Files.delete(path);
      return true;
    } catch (Exception ex) {
      log.error("deleteFile pathName: {}", pathName, ex);
      return false;
    }
  }

  // endregion

}
