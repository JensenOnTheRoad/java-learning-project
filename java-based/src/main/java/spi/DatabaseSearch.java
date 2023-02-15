package spi;

import java.util.List;

/**
 * @author jensen_deng
 */
public class DatabaseSearch implements Search {

  @Override
  public List<String> searchDocument(String keyword) {
    System.out.println("Database search: " + keyword);
    return null;
  }
}
