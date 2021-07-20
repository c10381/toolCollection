package tw.com.c10381.toolcollection.jsonTool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JsonToolTest {

  @Autowired
  JsonTool jsonTool;

  @Test
  void getSpecifyContentFromJsonResult() throws JsonProcessingException {
    var objectInput = objectJsonStr;
    var ArrayInput = ArrayJson;

    var objectResult = jsonTool.getSpecifyContentFromJsonResult(objectInput, "members");
    var arrayResult = jsonTool.getSpecifyContentFromJsonResult(ArrayInput);

    assertThat(objectResult)
        .asList()
        .extracting("name","age","secretIdentity")
        .contains(tuple("Molecule Man", 29, "Dan Jukes"),
                  tuple("Madame Uppercut", 39, "Jane Wilson"),
                  tuple("Eternal Flame", 1000000, "Unknown"));

    assertThat(arrayResult)
        .asList()
        .hasSize(2)
        .extracting("name","age","secretIdentity")
        .contains(
            tuple("Molecule Man", 29, "Dan Jukes"),
            tuple("Madame Uppercut", 39, "Jane Wilson")
            );
  }

  @Test
  void getContentFromJsonResult() throws JsonProcessingException {
    var objectInput = objectJsonStr;
    var ArrayInput = ArrayJson;

    var objectResult = jsonTool.getContentFromJsonResult(objectInput);
    var arrayResult = jsonTool.getContentFromJsonResult(ArrayInput);

    assertThat(objectResult)
        .asList()
        .extracting("squadName","homeTown","formed")
        .contains(tuple("Super hero squad","Metro City",2016));
    assertThat(arrayResult)
        .asList()
        .hasSize(2)
        .extracting("name","age","secretIdentity")
        .contains(
            tuple("Molecule Man", 29, "Dan Jukes"),
            tuple("Madame Uppercut", 39, "Jane Wilson")
        );
  }

  @Test
  void convertToJson() throws JsonProcessingException, JSONException {
    var objectInput = objectJsonStr;
    var ArrayInput = ArrayJson;
    var objectList = jsonTool.getContentFromJsonResult(objectInput);
    var arrayList = jsonTool.getContentFromJsonResult(ArrayInput);

    var objectJsonResult = jsonTool.convertToJson(objectList.get(0));
    var arrayJsonResult = jsonTool.convertToJson(arrayList);

    assertThat(objectJsonResult).isEqualTo(objectJsonStr);
    assertThat(arrayJsonResult).isEqualTo(ArrayInput);
  }


  String objectJsonStr = "{\"squadName\":\"Super hero squad\",\"secretBase\":\"Super tower\",\"homeTown\":\"Metro City\",\"members\":[{\"secretIdentity\":\"Dan Jukes\",\"name\":\"Molecule Man\",\"powers\":[\"Radiation resistance\",\"Turning tiny\",\"Radiation blast\"],\"age\":29},{\"secretIdentity\":\"Jane Wilson\",\"name\":\"Madame Uppercut\",\"powers\":[\"Million tonne punch\",\"Damage resistance\",\"Superhuman reflexes\"],\"age\":39},{\"secretIdentity\":\"Unknown\",\"name\":\"Eternal Flame\",\"powers\":[\"Immortality\",\"Heat Immunity\",\"Inferno\",\"Teleportation\",\"Interdimensional travel\"],\"age\":1000000}],\"active\":true,\"formed\":2016}";
  String ArrayJson = "[{\"secretIdentity\":\"Dan Jukes\",\"name\":\"Molecule Man\",\"powers\":[\"Radiation resistance\",\"Turning tiny\",\"Radiation blast\"],\"age\":29},{\"secretIdentity\":\"Jane Wilson\",\"name\":\"Madame Uppercut\",\"powers\":[\"Million tonne punch\",\"Damage resistance\",\"Superhuman reflexes\"],\"age\":39}]";
}