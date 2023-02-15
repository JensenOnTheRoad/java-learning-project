package utils.tree_builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 非递归的方式生成树
 *
 * @description
 * @author LiuKewen
 * @date 2022/1/6 12:51
 */
public class TreeUtil {
  private TreeUtil() {}

  /**
   * 转换树形结构
   *
   * @param source 需要组装的数据
   * @param <P>
   *     <p>第一层数据的parentId，
   *     <p>eg1 id="1" parentId=null ,topParentId=null
   *     <p>则 此数据为第一层数据
   *     <p>
   *     <p>eg2 id="1" parentId="i1" ,topParentId=i1
   *     <p>则 此数据为第一层数据
   *     <p>
   *     <p>eg3 id="1" parentId="i1",topParentId=null
   *     <p>则 此数据不为第一层数据
   * @param <T> 泛型
   * @return 分层之后的列表
   */
  public static <P, T extends TreeBase<T, P>> List<T> transfer(List<T> source, P topParentId) {
    List<T> root = new ArrayList<>();
    ArrayList<T> ts = new ArrayList<>(source);
    Iterator<T> iterator = ts.iterator();

    // 移除根数据
    while (iterator.hasNext()) {
      T next = iterator.next();
      if (next.isTop(topParentId)) {
        root.add(next);
        iterator.remove();
      }
    }

    Map<P, List<T>> parentMap =
        ts.stream().filter(m -> m.getPid() != null).collect(Collectors.groupingBy(T::getPid));

    for (T t : ts) {
      t.setSubs(parentMap.get(t.getId()));
    }

    return root.stream().peek(r -> r.setSubs(parentMap.get(r.getId()))).toList();
  }
}
