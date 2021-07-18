package tw.com.c10381.toolcollection.jsonTool;

import static org.apache.logging.log4j.util.Strings.isBlank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor =@__(@Autowired))
public class JsonTool {

  private final ObjectMapper objMapper;

  /**
   * 如第一層有data欄位，則先使用data中資料
   * @param content
   * @param jsonKeyFields 要分析至JSON至指定層
   * @return
   * @throws JsonProcessingException
   */
  public List<Map<String,Object>> getSpecifyContentFromJsonResult(String content,String... jsonKeyFields) throws JsonProcessingException {
    var jsonInData = content;
    for(var field:jsonKeyFields){
      jsonInData = Optional.ofNullable(objMapper.readTree(jsonInData))
          .map(node -> node.get(field))
          .map(Object::toString)
          .orElseThrow(()->new NoSuchElementException("此JSON:\n"+content+"\n在\""+field+"\"中無資料"));
    }
    return getContentFromJsonResult(jsonInData);
  }

  /**
   * 將MockMvc回傳的Json做第一層處理，並放入List，方便assert
   * @param content
   * @return
   * @throws JsonProcessingException
   */
  public List<Map<String,Object>> getContentFromJsonResult(String content) throws JsonProcessingException {
    if(isBlank(content)){
      return List.of();
    }
    var needValid = new ArrayList<Map<String,Object>>();
    var node = objMapper.readTree(content);
    if(node.isArray()){
      needValid.addAll(objMapper.readValue(content, new TypeReference<>() {}));
    }else{
      needValid.add(objMapper.readValue(content, new TypeReference<>() {}));
    }
    return needValid;
  }


  /**
   * 將input轉換為JsonString
   * @param body
   * @return
   */
  public String convertToJson(Object body) throws JSONException {
    if(null == body) { return ""; }
    if(body instanceof List){ return new JSONArray(body).toString(); }

    var bodyMap = Map.<String,Object>of();
    if(body instanceof Map){
      bodyMap = (Map<String, Object>) body;
    }else{
      // body = single object
      bodyMap = objMapper.convertValue(body, new TypeReference<>() {});
    }
    return new JSONObject(bodyMap).toString();
  }

}
