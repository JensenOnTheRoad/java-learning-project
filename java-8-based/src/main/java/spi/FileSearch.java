package spi;

import java.util.List;

/**
 * @author jensen_deng
 */
public class FileSearch implements Search {

  @Override
  public List<String> searchDocument(String keyword) {
    System.out.println("File search: " + keyword);
    return null;
  }
}
