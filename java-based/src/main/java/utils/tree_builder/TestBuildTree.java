package utils.tree_builder;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;
/**
 * @author jensen_deng
 */
interface TreeBase<T, ID> {

  ID getId();

  ID getPid();

  // 判断是否为最顶层数据，可以是 pid=id ，可以是 pid==null ，也可以是pid==0这种形式，根据需要自己实现
  default boolean isTop(ID p) {
    return p == this.getPid();
  }

  // 设置子集合
  void setSubs(List<T> var1);
}

public class TestBuildTree {

  @Test
  public void transfer() {

    List<A> list =
        Arrays.asList(
            new A(0, 12),
            new A(1, 0),
            new A(2, 1),
            new A(3, 1),
            new A(4, 2),
            new A(5, 2),
            new A(6, 5),
            new A(7, 5),
            new A(11, 0),
            new A(12, 11));

    List<A> transfer = TreeUtil.transfer(list, 0);

    System.out.println(new Gson().toJson(transfer));
  }
}

@Data
@ToString
class A implements TreeBase<A, Integer> {

  private int id;
  private int pId;
  private List<A> subs;

  public A(int id, int pId) {
    this.id = id;
    this.pId = pId;
  }

  @Override
  public void setSubs(List<A> var1) {
    this.subs = var1;
  }

  @Override
  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Integer getPid() {
    return pId;
  }
}
