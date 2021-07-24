package tw.com.c10381.toolcollection.httpClinet;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class HttpClientToolTest {

  private static String AUTHORIZATION ="";

  @Autowired
  HttpClientTool client;

  @Test
  @Disabled
  // 因此是使用氣象局API做測試，需有Authorization
  void sendGetRequest() throws IOException, InterruptedException {
    var baseUrl = "https://opendata.cwb.gov.tw/api";
    var url = baseUrl + "/v1/rest/datastore/F-C0032-001";
    var params = Map.of(
        "Authorization",AUTHORIZATION,
        "locationName",new String[]{"臺北市","新北市"}
    );

    var result = client.sendGetRequest(url,params);

    System.out.println(result);
  }
  @Test
  @Disabled
  void sendRequestAndGetSingleFile() throws Exception{
    var url = "https://od.cdc.gov.tw/eic/Day_Confirmation_Age_County_Gender_19CoV.csv";
    var path = Path.of("src/main/resources/testCase/testDownload.csv");

    var flag = client.sendRequestAndGetSingleFile(url,path);

    assertThat(flag).isTrue();
  }

  @Test
  @Disabled
  void sendRequestAndGetZip() throws Exception{
    var url = "https://www.tyc.moj.gov.tw/media/256209/%E6%A1%83%E5%9C%92%E5%9C%B0%E6%AA%A2-109%E5%B9%B41%E6%9C%88%E4%BB%BD%E6%9C%83%E8%A8%88%E6%9C%88%E5%A0%B1.zip?mediaDL=true";
    var path = Path.of("src/main/resources/testCase/");

    var flag = client.sendRequestAndGetZip(url,path);

    assertThat(flag).isTrue();
  }
}