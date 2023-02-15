package design_pattern.behavioral.responsibility_chain;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class Demo_ResponsibilityChain3 {

  private static String msg = "    测试内容敏感词1   ";

  // region 定义接口

  interface Process {
    /** 执行处理 */
    void doProcess();
  }

  // endregion

  // region 文本处理器
  static class BlankRemoveProcessor implements Process {
    @Override
    public void doProcess() {
      log.info("=".repeat(72));
      log.info("{}空格处理{}", "-".repeat(20), "-".repeat(20));
      msg = msg.trim();
      log.info(msg);
    }
  }

  static class SensitiveWordProcessor implements Process {
    private static String sensitiveWord = "敏感词1";

    @Override
    public void doProcess() {
      log.info("=".repeat(72));
      log.info("{}敏感词处理{}", "-".repeat(20), "-".repeat(20));
      msg = msg.replace(sensitiveWord, "");
      log.info(msg);
    }
  }

  static class CopyrightProcessor implements Process {

    @Override
    public void doProcess() {
      log.info("=".repeat(72));
      log.info("{}版权处理{}", "-".repeat(20), "-".repeat(20));
      msg += " written by jensen deng";
      log.info(msg);
    }
  }

  static class Handler {
    private List<Process> chains = new ArrayList<>();

    /**
     * 添加责任链
     *
     * @param process
     * @return
     */
    public Handler addChain(Process process) {
      chains.add(process);
      return this;
    }

    public void process() {
      for (Process chain : chains) {
        chain.doProcess();
      }
    }
  }

  // endregion

  // region 测试
  @Test
  @DisplayName("责任链实现内容过滤器")
  void should_responsibility_filter() {
    log.info("=".repeat(72));
    log.info("{}处理前{}", "-".repeat(20), "-".repeat(20));
    log.info(msg);
    Handler chain =
        new Handler()
            .addChain(new BlankRemoveProcessor())
            .addChain(new SensitiveWordProcessor())
            .addChain(new CopyrightProcessor());
    chain.process();
  }
  // endregion
}
