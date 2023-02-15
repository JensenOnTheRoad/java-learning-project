package utils.easyExcel.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 自定义合并单元格
 *
 * @author Jensen Deng
 */
public class CustomMergeCellStrategy implements CellWriteHandler {
  /** 需要合并哪些列 */
  private List<Integer> mergeColumIndexes;
  /** 从第几列开始合并 */
  private Integer mergeRowIndex;

  /**
   * 是否有唯一标识，默认FALSE
   *
   * <p>若没有单元格相同就合并
   *
   * <p>若有，只合并当前唯一标识的列
   */
  private Boolean hasUniqueKey = Boolean.FALSE;

  /** 用于合并的唯一标识,例如ID */
  private Integer uniqueKey;

  /** 私有构造方法，禁止被无参实例化 */
  private CustomMergeCellStrategy() {}

  /**
   * 有唯一标识的构造方法
   *
   * @param mergeColumIndexes
   * @param mergeRowIndex
   * @param uniqueKey
   */
  public CustomMergeCellStrategy(
      List<Integer> mergeColumIndexes, Integer mergeRowIndex, Integer uniqueKey) {
    this.mergeColumIndexes = mergeColumIndexes;
    this.mergeRowIndex = mergeRowIndex;
    this.uniqueKey = uniqueKey;
    this.hasUniqueKey = Boolean.TRUE;
  }

  /**
   * 没有唯一标识的构造方法
   *
   * @param mergeColumIndexes
   * @param mergeRowIndex
   */
  public CustomMergeCellStrategy(List<Integer> mergeColumIndexes, Integer mergeRowIndex) {
    this.mergeColumIndexes = mergeColumIndexes;
    this.mergeRowIndex = mergeRowIndex;
  }

  @Override
  public void afterCellDispose(
      WriteSheetHolder writeSheetHolder,
      WriteTableHolder writeTableHolder,
      List<WriteCellData<?>> cellDataList,
      Cell cell,
      Head head,
      Integer relativeRowIndex,
      Boolean isHead) {
    int curRowIndex = cell.getRowIndex();
    int curColumnIndex = cell.getColumnIndex();
    if (curRowIndex > mergeRowIndex) {
      for (Integer mergeColumIndex : mergeColumIndexes) {
        if (curColumnIndex == mergeColumIndex) {
          mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColumnIndex);
          break;
        }
      }
    }
  }

  private void mergeWithPrevRow(
      WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
    Object curData;
    Object preData;
    if (hasUniqueKey) {
      // 当前列单元格
      Cell curCell = cell.getSheet().getRow(curRowIndex).getCell(uniqueKey);
      curData =
          curCell.getCellType() == CellType.STRING
              ? curCell.getStringCellValue()
              : curCell.getNumericCellValue();
      // 前一列单元格
      Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(uniqueKey);
      preData =
          preCell.getCellType() == CellType.STRING
              ? preCell.getStringCellValue()
              : preCell.getNumericCellValue();
    } else {
      // 当前列单元格
      curData =
          cell.getCellType() == CellType.STRING
              ? cell.getStringCellValue()
              : cell.getNumericCellValue();
      // 前一列单元格
      Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
      preData =
          preCell.getCellType() == CellType.STRING
              ? preCell.getStringCellValue()
              : preCell.getNumericCellValue();
    }
    // 当前列数据与上一列数据进行对比
    if (curData.equals(preData)) {
      Sheet sheet = writeSheetHolder.getSheet();
      List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
      boolean isMerged = false;
      // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元

      for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
        CellRangeAddress cellRangeAddress = mergeRegions.get(i);
        if (cellRangeAddress.isInRange(curRowIndex - 1, curColIndex)) {
          sheet.removeMergedRegion(i);
          cellRangeAddress.setLastRow(curRowIndex);
          sheet.addMergedRegion(cellRangeAddress);
          isMerged = true;
        }
      }
      // 若上一个单元格未被合并，则新增合并单元
      if (!isMerged) {
        CellRangeAddress cellRangeAddress =
            new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
        sheet.addMergedRegion(cellRangeAddress);
      }
    }
  }
}
