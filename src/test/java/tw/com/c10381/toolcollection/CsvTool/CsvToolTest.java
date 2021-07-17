package tw.com.c10381.toolcollection.CsvTool;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CsvToolTest {

  @Autowired
  private CsvTool csvTool;

  @Test
  void getDataFromCSV() {
    var path = Path.of("src/main/resources/testCase/csvTest.csv").toAbsolutePath();
    var list = csvTool.getDataFromCSV(path);
    assertThat(list).asList().hasSize(8);
  }

  @Test
  void outputCSV() {
    var path = Path.of("src/main/resources/testCase/outPut.csv").toAbsolutePath();
    var data = List.of(
        Map.of("Title1","aaa","Title2","bbb"),
        Map.of("Title1","bbb","Title2","ccc"),
        Map.of("Title1","111","Title2","222"),
        Map.of("Title1","333","Title2","444"));
    var title = List.of("Title1","Title2");

    try{
      csvTool.outputCSV(path,data,title);
    }catch (Exception e){
      System.out.println(e.toString());
    }

    var outputCSVData = csvTool.getDataFromCSV(path);
    assertThat(outputCSVData).asList().hasSize(4)
        .extracting("Title1","Title2")
        .containsExactly(
            Tuple.tuple("aaa","bbb"),
            Tuple.tuple("bbb","ccc"),
            Tuple.tuple("111","222"),
            Tuple.tuple("333","444"));
  }
}