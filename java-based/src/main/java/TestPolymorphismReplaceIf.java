import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Author Jensen Deng
 */
@Slf4j
public class TestPolymorphismReplaceIf {

  @Test
  @DisplayName("多态替换多IF判断")
  void should_use_polymorphism_replace_If_Statement() {
    log.info("=".repeat(70));

    // region  IF判断

    getTeamMember(1);
    getTeamMember(2);
    getTeamMember(3);

    // endregion

    log.info("=".repeat(70));

    // region 接口及实现

    TeamMember teamMember1 = new Developer();
    teamMember1.say();

    TeamMember teamMember2 = new BusinessAnalyst();
    teamMember2.say();

    TeamMember teamMember3 = new ProjectManager();
    teamMember3.say();
    // endregion

    log.info("=".repeat(70));

    // region 枚举类实现

    TeamMember member1 = (TeamMember) TeamMemberEnums.getMember(1);
    member1.say();

    TeamMember member2 = (TeamMember) TeamMemberEnums.getMember(2);
    member2.say();

    TeamMember member3 = (TeamMember) TeamMemberEnums.getMember(3);
    member3.say();

    log.info("=".repeat(70));

    // endregion
  }

  // region

  private void getTeamMember(Integer type) {
    if (type == 1) {
      log.info("-".repeat(20) + "I am Developer" + "-".repeat(20));
    } else if (type == 2) {
      log.info("-".repeat(20) + "I am Business Analyst" + "-".repeat(20));
    } else if (type == 3) {
      log.info("-".repeat(20) + "I am Project Manager" + "-".repeat(20));
    }
  }

  // endregion
}

// region

interface TeamMember {
  void say();
}

@Slf4j
class Developer implements TeamMember {
  @Override
  public void say() {
    log.info("-".repeat(20) + "I am Developer" + "-".repeat(20));
  }
}

@Slf4j
class BusinessAnalyst implements TeamMember {
  @Override
  public void say() {
    log.info("-".repeat(20) + "I am Business Analyst" + "-".repeat(20));
  }
}

@Slf4j
class ProjectManager implements TeamMember {
  @Override
  public void say() {
    log.info("-".repeat(20) + "I am Project Manager" + "-".repeat(20));
  }
}

// endregion

enum TeamMemberEnums {
  DEVELOPER(1, Developer.class),
  BUSINESS_ANALYST(2, BusinessAnalyst.class),
  PROJECT_MANAGER(3, ProjectManager.class);

  private final Integer type;
  private final Class<?> member;

  TeamMemberEnums(Integer type, Class member) {
    this.type = type;
    this.member = member;
  }

  public static Object getMember(Integer type) {
    return Arrays.stream(TeamMemberEnums.values())
        .filter(x -> x.type.equals(type))
        .findFirst()
        .map(
            y -> {
              try {
                return y.member.getDeclaredConstructor().newInstance();
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            })
        .orElse(null);
  }
}
