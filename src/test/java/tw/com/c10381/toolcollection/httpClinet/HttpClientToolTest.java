package tw.com.c10381.toolcollection.httpClinet;

import java.io.IOException;
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
}