package tw.com.c10381.toolcollection.zipTool;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class ZipToolTest {
  @Autowired
  private ZipTool zipTool;

  @Test
  @Order(1)
  void zipFile() {
    var paths = new Path[]{
        Path.of("src/main/resources/testCase/csvTest.csv"),
        Path.of("src/main/resources/testCase/outPut.csv")};
    var zipLocation = "src/main/resources/testCase/test.zip";

    var result = zipTool.zipFile(paths,zipLocation);

    assertThat(result).isTrue();
  }

  @Test
  @Order(2)
  void unZipFile() {
    var path = "src/main/resources/testCase/test.zip";
    var unZipFile = "src/main/resources/testCase/unZip";

    var result = zipTool.unZipFile(path,unZipFile);

    assertThat(result).isTrue();
  }
}