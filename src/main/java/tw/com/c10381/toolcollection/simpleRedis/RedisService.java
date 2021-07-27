package tw.com.c10381.toolcollection.simpleRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
  private final RedisRepository redisRepository;

  public void saveString(String key, String value){
    redisRepository.set(key,value);
  }

  public String getString(String key){
    return String.valueOf(redisRepository.get(key));
  }
}
