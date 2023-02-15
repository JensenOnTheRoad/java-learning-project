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
public class Demo_ResponsibilityChain2 {

  @Test
  @DisplayName("")
  void should_() {
    String msg = "    测试内容  敏感词1  ";
    Handler chain =
        new Handler()
            .addChain(new BlankRemoveProcessor())
            .addChain(new SensitiveWordProcessor())
            .addChain(new CopyrightProcessor());
    chain.process(msg);
  }

  public interface Process {

    /**
     * 执行处理
     *
     * @param msg
     */
    String doProcess(String msg);
  }

  static class BlankRemoveProcessor implements Process {
    @Override
    public String doProcess(String msg) {
      log.info("=".repeat(72));
      log.info("{}空格处理{}", "-".repeat(20), "-".repeat(20));
      msg = msg.trim();
      log.info(msg);
      return msg;
    }
  }

  static class SensitiveWordProcessor implements Process {
    private static String sensitiveWord = "敏感词1";

    @Override
    public String doProcess(String msg) {
      log.info("=".repeat(72));
      log.info("{}敏感词处理{}", "-".repeat(20), "-".repeat(20));
      msg = msg.replace(sensitiveWord, "");
      log.info(msg);
      return msg;
    }
  }

  static class CopyrightProcessor implements Process {

    @Override
    public String doProcess(String msg) {
      log.info("=".repeat(72));
      log.info("{}版权处理{}", "-".repeat(20), "-".repeat(20));
      msg += " written by jensen deng";
      log.info(msg);
      return msg;
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

    public String process(String msg) {
      String result = "";
      for (Process chain : chains) {
        result = chain.doProcess(msg);
      }
      return result;
    }
  }
}
