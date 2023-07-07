package utils.xml;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class XmlParser {

  @Test
  void test_() {
    String xml =
        """
    <?xml version="1.0" encoding="UTF-8" ?>
    <students>
        <student no="001">
            <name>张三</name>
            <age>18</age>
            <score>96</score>
            <parents>
                <father>阿强</father>
                <mother>阿珍</mother>
            </parents>
        </student>
        <student no="002">
            <name>李四</name>
            <age>19</age>
            <score>99</score>
            <parents>
                <father>阿贵</father>
                <mother>阿莲</mother>
            </parents>
        </student>
    </students>
""";

    parse(xml);
  }

  @SneakyThrows
  public static void parse(String content) {
    File file = new File("test_parser.xml");
    FileWriter writer = new FileWriter(file);

    writer.write(content);
    SAXReader reader = new SAXReader();
    Document document = reader.read(file);

    // 获取 XML 文档的根节点，即 <students> 标签
    Element root = document.getRootElement();
    // elements 方法用于获取指定的标签集合
    List<Element> students = root.elements("student");

    for (Element student : students) {
      // attribute 方法用于获取标签属性
      Attribute noAttr = student.attribute("no");
      String no = noAttr.getText();
      System.out.println("no: " + no);

      // element 方法用于获取唯一的子节点对象
      Element nameElement = student.element("name");
      // getText 方法用于获取标签文本
      String name = nameElement.getText();
      System.out.println("name: " + name);

      // elementText 方法用于获取指定标签的文本
      String age = student.elementText("age");
      System.out.println("age: " + age);

      String score = student.elementText("score");
      System.out.println("score: " + score);

      Element parents = student.element("parents");
      String father = parents.elementText("father");
      String mother = parents.elementText("mother");

      System.out.println("father: " + father);
      System.out.println("mother: " + mother);
      System.out.println("---------");
    }
  }
}
