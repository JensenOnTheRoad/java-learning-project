package utils.easyExcel.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 合并列策略
 *
 * @author Jensen Deng
 */
public class ColumnHiddenStrategy implements CellWriteHandler {

  /** 需要隐藏的列 */
  List<Integer> columns;

  public ColumnHiddenStrategy(List<Integer> columns) {
    this.columns = columns;
  }

  @Override
  public void afterCellCreate(
      WriteSheetHolder writeSheetHolder,
      WriteTableHolder writeTableHolder,
      Cell cell,
      Head head,
      Integer relativeRowIndex,
      Boolean isHead) {
    columns.forEach(
        columnIndex -> writeSheetHolder.getSheet().setColumnHidden(columnIndex, Boolean.TRUE));
  }
}
