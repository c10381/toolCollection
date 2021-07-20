package tw.com.c10381.toolcollection.httpClinet;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tw.com.c10381.toolcollection.jsonTool.JsonTool;

@Service
@RequiredArgsConstructor
public class HttpClientTool {

  private final ObjectMapper objMapper;
  private final JsonTool jsonTool;
  /**
   * Get，without Header
   * @param url
   * @param params
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  public String sendGetRequest(String url,Map<String,?> params)
      throws IOException, InterruptedException {
    var requestParams = null != params ? params : Map.<String,Object>of();
    var requestUrl = getQueryString(url,requestParams);

    var request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(requestUrl))
        .build();

    return sendRequest(request);
  }

  /**
   * Get，with Header
   * @param url
   * @param headers
   * @param params
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  public String sendGetRequestWithHeader(String url, Map<String,?> headers,Map<String,?> params)
      throws IOException, InterruptedException {
    var requestHeader = null != headers ? getHeaders(headers) : new String[]{};
    var requestParams = null != params ? params : Map.<String,Object>of();
    var requestUrl = getQueryString(url,requestParams);

    var request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(requestUrl))
        .headers(requestHeader)
        .build();

    return sendRequest(request);
  }

  /**
   * Post，without Header
   * url    請放置如"/api/{id}/{secId}"
   * params 請放置Map.of("id",1,"secID",444)
   * @param url
   * @param params
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  public String sendPostRequest(String url,Map<String,?> params,Map<String,?> body)
      throws IOException, InterruptedException, JSONException {
    var requestParams = null != params ? params : Map.<String,Object>of();
    var requestBody = null != body ? jsonTool.convertToJson(body) : "";
    var requestUrl = getQueryString(url,requestParams);

    var request = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString(requestBody))
        .uri(URI.create(requestUrl))
        .build();

    return sendRequest(request);
  }

  /**
   * Post，with Header
   * url    請放置如"/api/{id}/{secId}"
   * params 請放置Map.of("id",1,"secID",444)
   * @param url
   * @param params
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  public String sendPostRequestWithHeader(String url, Map<String,?> headers,Map<String,?> params,Map<String,?> body)
      throws IOException, InterruptedException, JSONException {
    var requestParams = null != params ? params : Map.<String,Object>of();
    var requestHeader = null != headers ? getHeaders(headers) : new String[]{};
    var requestBody = null != body ? jsonTool.convertToJson(body) : "";
    var requestUrl = getQueryString(url,requestParams);

    var request = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString(requestBody))
        .uri(URI.create(requestUrl))
        .headers(requestHeader)
        .build();

    return sendRequest(request);
  }

  /**
   * 發送request共用方法
   * @param request
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  private String sendRequest(HttpRequest request) throws IOException, InterruptedException {

    var client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofMillis(5000L))
        .followRedirects(Redirect.NORMAL)
        .build();

    var response = client.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response);
    return Optional.ofNullable(response)
        .filter(r -> r.statusCode() == 200)
        .map(HttpResponse::body)
        .orElse("");
  }

  /**
   * 將Map轉換成String[]，配合httpClientBuilder.header(String[]) -> k,v,k,v...
   * @param headers
   * @return
   */
  private String[] getHeaders(Map<String, ?> headers) {
    var result = new ArrayList<String>();
    headers.forEach((k,v) -> {
      result.add(k);
      result.add(String.valueOf(v));
    });
    return StringUtils.toStringArray(result);
  }

  /**
   * 組Query String
   * 此需注意的是String[] 型態
   * @param url
   * @param params
   * @return
   */
  private String getQueryString(String url,Map<String,?> params){
    var paramUri = new StringBuilder(1+params.size());
    params.forEach((k,v) -> {
      paramUri.append(k).append("=");
      if(v instanceof String[] && !isEmpty((String[])v)){
        var strArray = (String[])v;
        var join = new StringJoiner(",");
        for(var s:strArray){
          join.add(URLEncoder.encode(s, UTF_8));
        }
        paramUri.append(join.toString());
        paramUri.append("&");
      }else{
        paramUri.append(v).append("&");
      }
    });
    return url +"?"+ paramUri.toString();
  }

  /**
   * 主要為了處理 params case eg:"/api/{id}"
   * @param uri 需處理之uri，注意變數請使用"{}"包裹 eg:"{id}"
   * @param params 需替換之參數，Key需與uri的大括弧"{}"對應  eg:Map.of("id",1)
   * @return
   */
  public String getUrlHasParams(String uri ,Map<String,?> params){
    var uriWithParam = uri;
    for(var e:params.entrySet()){
      uriWithParam = uriWithParam.replace("{"+e.getKey()+"}",String.valueOf(e.getValue()));
    }
    return uriWithParam;
  }

}
