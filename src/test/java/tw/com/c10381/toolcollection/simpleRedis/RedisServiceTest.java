package tw.com.c10381.toolcollection.simpleRedis;


import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class RedisServiceTest {
  @Autowired
  RedisService redisService;

  @Test
  @Disabled
  public void test(){
    var key = "test1";
    var value = "哈囉";
    redisService.saveString(key,value);
    var re = redisService.getString(key);
    log.info(re);
    assertThat(re)
        .isEqualTo(value);
  }
}