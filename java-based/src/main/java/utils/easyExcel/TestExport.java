package utils.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import utils.easyExcel.strategy.ExcelFillCellMergeStrategy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestExport {

  private static final Path PATH_1 = Path.of("temporary" + File.separator + "测试导出合并单元格.xlsx");
  private static final Path PATH_2 = Paths.get("temporary" + File.separator + "test.xlsx");

  @BeforeEach
  void setUp() throws IOException {
    clean(PATH_1);
    clean(PATH_2);
  }

  @Test
  @DisplayName("合并单元格，从id列开始合并相同的行")
  void test() throws IOException {
    List<ExcelModel> dataList = getData();

    File file1 = new File(PATH_1.toString());
    if (!Files.exists(file1.toPath())) {
      Files.createFile(file1.toPath());
    }

    EasyExcel.write(file1, ExcelModel.class)
        .registerWriteHandler(new ExcelFillCellMergeStrategy(1, 3))
        .autoCloseStream(Boolean.TRUE)
        .sheet("测试导出合并单元格Excel")
        .doWrite(dataList);
  }

  @Test
  @DisplayName("自定义单元格合并")
  void should_merge_cells_2() throws IOException {
    List<TestExcel> dataList =
        Arrays.asList(
            TestExcel.builder().id("1").name("Li").property("FALSE").sex("男").build(),
            TestExcel.builder().id("2").name("Li").property("FALSE").sex("man").build(),
            TestExcel.builder().id("3").name("Zhao").property("FALSE").sex("女").build());

    Files.createFile(PATH_2);

    ExcelFillCellMergeStrategy merge = new ExcelFillCellMergeStrategy(1, 3);
    EasyExcelFactory.write(PATH_2.toFile(), TestExcel.class)
        .registerWriteHandler(merge)
        .sheet("测试合并")
        .doWrite(dataList);
  }

  private static List<ExcelModel> getData() {
    List<ExcelModel> list = new ArrayList<>();
    ExcelModel model1 = new ExcelModel();
    model1.setOrder("1");
    model1.setTitle("标题111");
    model1.setCompany("单位111");
    model1.setDocumentCode("编号111");
    model1.setIdea("意见111");
    model1.setPublishDate("2022-01-21");

    ExcelModel model2 = new ExcelModel();
    model2.setOrder("1");
    model2.setTitle("标题111");
    model2.setCompany("单位222");
    model2.setDocumentCode("编号222");
    model2.setIdea("意见111");
    model2.setPublishDate("2022-01-21");

    ExcelModel model3 = new ExcelModel();
    model3.setOrder("2");
    model3.setTitle("标题333");
    model3.setCompany("单位222");
    model3.setDocumentCode("编号222");
    model3.setIdea("意见333");
    model3.setPublishDate("2022-01-21");

    ExcelModel model4 = new ExcelModel();
    model4.setOrder("4");
    model4.setTitle("标题444");
    model4.setCompany("单位444");
    model4.setDocumentCode("编号444");
    model4.setIdea("意见444");
    model4.setPublishDate("2022-01-21");

    ExcelModel model5 = new ExcelModel();
    model5.setOrder("5");
    model5.setTitle("标题555");
    model5.setCompany("单位555");
    model5.setDocumentCode("编号555");
    model5.setIdea("意见555");
    model5.setPublishDate("2022-01-21");

    list.add(model1);
    list.add(model2);
    list.add(model3);
    list.add(model4);
    list.add(model5);
    return list;
  }

  private void clean(Path filePath) throws IOException {
    if (Files.exists(filePath)) {
      Files.delete(filePath);
    }
  }

  @Data
  @Builder
  static class TestExcel {
    @ExcelProperty(value = "编号", index = 0)
    private String id;

    @ExcelProperty(value = "名称", index = 1)
    private String name;

    @ExcelProperty(value = "性别", index = 2)
    private String sex;

    @ExcelProperty(value = "属性", index = 3)
    private String property;
  }

  @Getter
  @Setter
  @ContentRowHeight(15) // 内容行高
  @HeadRowHeight(20) // 表头行高
  public static class ExcelModel {

    public static final String RES_EXCEL_NAME = "document.xlsx";
    public static final String TEMPLATE_EXCEL_NAME = "文章管理";
    public static final String SUFFIX = ".xlsx";

    /**
     * notice
     *
     * <p>当采用模板上传Excel且 .needHead(false) 设置了不生成标题头
     *
     * <p>@ColumnWidth(10)标签将无效，根据模板头的长度来走
     */
    @ColumnWidth(10)
    @ExcelProperty(value = "序号", index = 0)
    private String order;

    @ColumnWidth(20)
    @ExcelProperty(value = "文章标题", index = 1)
    private String title;

    @ColumnWidth(15)
    @ExcelProperty(value = "单位", index = 2)
    private String company;

    @ColumnWidth(15)
    @ExcelProperty(value = "编号", index = 3)
    private String documentCode;

    @ColumnWidth(12)
    @ExcelProperty(value = "发文日期", index = 4)
    private String publishDate;

    @ColumnWidth(25)
    @ExcelProperty(value = "意见", index = 5)
    private String idea;
  }
}
