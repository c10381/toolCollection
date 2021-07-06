package tw.com.c10381.toolcollection.CsvTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import org.springframework.stereotype.Service;

@Service
public class CsvTool {

  public List<Map<String,String>> getDataFromCSV(Path path){
    var keys = new ArrayList<String>();
    var result = new ArrayList<Map<String,String>>();
    try (BufferedReader br = new BufferedReader(new FileReader(path.toString()))) {
      String line;
      while ((line = br.readLine()) != null) {
        var values = line.split(",");
        // 第一行
        if(keys.isEmpty()){
          keys.addAll(Arrays.asList(values));
          continue;
        }
        var resultMap = new HashMap<String,String>();
        for(var i=0; i < values.length ; i++ ){
          resultMap.put(keys.get(i),values[i]);
        }
        result.add(resultMap);
      }
    }catch(Exception e){
      System.out.println(e.toString());
    }
    return result;
  }

  public void outputCSV(Path outputPath,List<Map<String,String>> differentDate ,List<String> headers)
      throws FileNotFoundException {
    File csvOutputFile = outputPath.toFile();
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {

      pw.println(String.join(",",headers));
      differentDate.forEach(d -> {
        var sb = new StringJoiner(",");
        headers.forEach(header -> {
          var data = Optional.ofNullable(d.get(header)).orElse("");
          sb.add(data) ;
        });
        pw.println(sb.toString());
      });
      System.out.println("已成功生成csv檔案於"+ outputPath.toAbsolutePath().toString());
    }
  }

}
