package utils.compress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.util.ResourceUtils;

/**
 * @author jensen_deng
 */
class TestFile {

  public static final String FOLDER_PATH = ResourceUtils.CLASSPATH_URL_PREFIX + "temporary";

  public static final String TARGET_PATH = "./test.zip";
  public File FILE_1;
  public File FILE_2;

  @BeforeEach
  @SneakyThrows
  void setUp() {
    FILE_1 = new File(getResourceFilePath("temporary/test1.txt"));
    FILE_2 = new File(getResourceFilePath("temporary/test2.txt"));
    if (!FILE_1.exists()) {
      FILE_1.createNewFile();
    }
    if (!FILE_2.exists()) {
      FILE_2.createNewFile();
    }

    List<File> files = Arrays.asList(FILE_1, FILE_2);
  }

  @org.junit.jupiter.api.Test
  void zipFiles() throws FileNotFoundException {

    List<File> files = Arrays.asList(FILE_1, FILE_2);
    // 压缩后的文件
    File zipFile = new File(TARGET_PATH);

    // 压缩后的文件
    Path path = Path.of("./test.zip");
    OutputStream outputStream = new FileOutputStream("./temporary/test.zip");

    FileUtil.toZip(path.toString(), outputStream, Boolean.TRUE);
  }

  @org.junit.jupiter.api.Test
  void unZipFiles() {
    // 需要解压缩的文件
    String source = getResourceFilePath("temporary" + File.separator + "test.zip");
    // 解压后的目标目录
    String descDir = "." + File.separator + "temporary";
    FileUtil.unZipFiles(source, descDir);
  }

  @org.junit.jupiter.api.Test
  @DisplayName("递归压缩测试")
  void should_compress_folder_content() throws FileNotFoundException {
    String folderPath = FOLDER_PATH;
    FileOutputStream fos1 = new FileOutputStream(TARGET_PATH);
    FileUtil.toZip(folderPath, fos1, true);
  }

  @org.junit.jupiter.api.Test
  @DisplayName("二进制文件阅读器")
  void fileReader() throws IOException {
    File file = new File(getResourceFilePath("temporary" + File.separator + "test.doc"));
    FileUtil.BinaryReader(file.getAbsolutePath());
  }

  // region 获取资源文件夹路径
  private String getResourceFilePath(String path) {
    return Resource.class.getClassLoader().getResource(path).getPath();
  }
  // endregion
}
