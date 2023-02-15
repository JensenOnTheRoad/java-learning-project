package object_clone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

public class TestObjectClone {

  @Test
  @DisplayName("浅拷贝")
  void test_shadow_copy() throws CloneNotSupportedException {
    // 对象赋值
    UserInfo user1 = UserInfo.builder().name("Zhang").build();

    UserInfo user2 = user1;
    user2.setName("Xu");

    // 使用对象赋值后，修改其中一个对象的属性。两个对象基本类型属性应该还是相等的。
    Assertions.assertThat(user1.getName()).isEqualTo(user2.getName());

    // 对象浅拷贝
    UserInfo userInfo1 = new UserInfo();
    userInfo1.setName("Wang");

    UserInfo userInfo2 = (UserInfo) userInfo1.clone();
    userInfo1.setName("Li");

    // 浅拷贝后，修改其中一个对象的值属性后，两个对象不应相等
    Assertions.assertThat(userInfo1.getName()).isNotEqualTo(userInfo2.getName());
  }

  @Test
  @DisplayName("深拷贝")
  public void test_deep_clone_test() throws CloneNotSupportedException {
    User user1 = User.builder().id(1).userInfo(new UserInfo("Li")).build();
    User user2 = (User) user1.clone();

    user1.setId(2);
    user2.setUserInfo(UserInfo.builder().name("Wang").build());
    // 浅拷贝后，修改其中一个对象的基本类型属性和引用类型属性后，两个对象不应相等
    Assertions.assertThat(user1).isNotEqualTo(user2);
  }

  @Test
  @DisplayName("Spring的BeanUtils工具类拷贝")
  void test_spring_bean_utils_for_clone() {
    User user1 = new User(1);
    User user2 = new User(2);

    BeanUtils.copyProperties(user1, user2);
    user2.setId(22);
    // 使用Spring中的BeanUtils克隆后，修改其中一个对象后，两个对象不应相等
    Assertions.assertThat(user1).isNotEqualTo(user2);
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class UserInfo implements Cloneable {

  private String name;

  /**
   * 浅拷贝,Shadow Clone
   *
   * <p>重写clone方法，改为public方便外部调用
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class User implements Cloneable {
  private Integer id;

  private UserInfo userInfo;

  public User(Integer id) {
    this.id = id;
  }

  /**
   * 深拷贝,Deep Clone
   *
   * <p>重写clone方法，改为public方便外部调用
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    User user = (User) super.clone();
    // 引用类型拷贝
    user.userInfo = (UserInfo) user.getUserInfo().clone();
    return user;
  }
}
