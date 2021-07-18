package tw.com.c10381.toolcollection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToolcollectionApplication {

  public static void main(String[] args) {
    SpringApplication.run(ToolcollectionApplication.class, args);
  }

  /**
   * For Jackson ObjectMapper @Autowired
   * @return
   */
  @Bean
  public ObjectMapper objectMapper(){
    return new ObjectMapper();
  }

}
