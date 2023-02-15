package design_pattern.behavioral.responsibility_chain;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Demo_ResponsibilityChain {

  // region 测试

  private final HandlerChain chain = new HandlerChain();

  @BeforeEach
  void setUp() {
    chain.addHandler(new Leader());
    chain.addHandler(new DepartmentManager());
    chain.addHandler(new GeneralManager());
  }

  @Test
  @DisplayName("责任链实现请假审批流程")
  void should_use_responsibility_chain_pattern_implement_leave_approve_process() {

    System.out.println("Apply for 1 day:\t");
    ApplyForm zhangSan = ApplyForm.builder().name("zhang san").days(1.0).reason("").build();
    chain.process(zhangSan);

    System.out.println("=".repeat(100));

    System.out.println("Apply for 3 days:\t");
    ApplyForm liSi = ApplyForm.builder().name("li si").days(3.0).reason("travel").build();
    chain.process(liSi);

    System.out.println("=".repeat(100));

    System.out.println("Apply for 7 days:\t");
    ApplyForm wangWu =
        ApplyForm.builder().name("wang wu").days(7.0).reason("private matter").build();
    chain.process(wangWu);
  }

  // endregion

  // region 定义
  /** 处理接口 */
  interface Handler {
    ProcessResult approve(ApplyForm applyForm);

    ProcessResult reject(ApplyForm applyForm);
  }

  /** 申请单据 */
  @Data
  @Builder
  static class ApplyForm {
    private String name;
    private Double days;
    private String reason;
  }

  /** 处理结果 */
  @Data
  @Builder
  static class ProcessResult {
    private String processor;
    private Boolean status;
  }

  /** 团队负责人 */
  static class Leader implements Handler {
    @Override
    public ProcessResult approve(ApplyForm applyForm) {
      if (applyForm.getDays() > 0 && applyForm.getDays() <= 1) {
        return ProcessResult.builder()
            .processor(this.getClass().getSimpleName())
            .status(Boolean.TRUE)
            .build();
      }
      return ProcessResult.builder()
          .processor(this.getClass().getSimpleName())
          .status(Boolean.FALSE)
          .build();
    }

    @Override
    public ProcessResult reject(ApplyForm applyForm) {
      return ProcessResult.builder()
          .processor(this.getClass().getSimpleName())
          .status(Boolean.FALSE)
          .build();
    }
  }

  /** 部门经理 */
  static class DepartmentManager implements Handler {
    @Override
    public ProcessResult approve(ApplyForm applyForm) {
      if (applyForm.getDays() > 1 && applyForm.getDays() <= 3) {
        return ProcessResult.builder()
            .processor(this.getClass().getSimpleName())
            .status(Boolean.TRUE)
            .build();
      }
      return ProcessResult.builder()
          .processor(this.getClass().getSimpleName())
          .status(Boolean.FALSE)
          .build();
    }

    @Override
    public ProcessResult reject(ApplyForm applyForm) {
      return ProcessResult.builder()
          .processor(this.getClass().getSimpleName())
          .status(Boolean.FALSE)
          .build();
    }
  }

  /** 总经理 */
  static class GeneralManager implements Handler {
    @Override
    public ProcessResult approve(ApplyForm applyForm) {
      if (applyForm.getDays() > 3) {
        return ProcessResult.builder()
            .processor(this.getClass().getSimpleName())
            .status(Boolean.TRUE)
            .build();
      }
      return ProcessResult.builder()
          .processor(this.getClass().getSimpleName())
          .status(Boolean.FALSE)
          .build();
    }

    @Override
    public ProcessResult reject(ApplyForm applyForm) {
      return ProcessResult.builder()
          .processor(this.getClass().getSimpleName())
          .status(Boolean.FALSE)
          .build();
    }
  }

  static class HandlerChain {
    private final List<Handler> handlers = new ArrayList<>();

    void addHandler(Handler handler) {
      handlers.add(handler);
    }

    public void process(ApplyForm applyForm) {

      for (Handler handler : handlers) {
        ProcessResult process;
        // 模拟 lisi 的请求被拒绝
        if (Boolean.TRUE.equals(mockReject(applyForm, handler, "li si"))) {
          return;
        }

        process = handler.approve(applyForm);
        if (process.getStatus().equals(Boolean.TRUE)) {
          System.out.printf(
              "\t<<%s>>, you application has been <<Approved>>,via <<%s>>.%n",
              applyForm.getName(), process.getProcessor());
          return;
        }
      }
    }

    private static Boolean mockReject(ApplyForm applyForm, Handler handler, String applicant) {
      if (handler instanceof DepartmentManager && applyForm.getName().equals(applicant)) {
        ProcessResult process = handler.reject(applyForm);
        String format =
            String.format(
                "\t<<%s>>, you application has been <<Rejected>>,via <<%s>>.",
                applyForm.getName(), process.getProcessor());
        System.out.println(format);
        return true;
      }
      return false;
    }
  }
  // endregion
}
