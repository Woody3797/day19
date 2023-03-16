package ibf2022.ssf.day19.repository;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.ssf.day19.model.Love;

@Repository
public class LoveRepository {
    
    @Autowired 
    @Qualifier("love")
    RedisTemplate<String, String> template;

    public void save(Love love) {
        love.setId(Love.generateId());
        template.opsForValue().set(love.getId(), love.toJSON().toString());
        String result = template.opsForValue().get(love.getId());

        if (result != null) {
            template.opsForValue().set(love.getId(), love.toJSON().toString());
        } else {
            template.opsForValue().setIfAbsent(love.getId(), love.toJSON().toString());
        }
    }

    public Optional<Love> findById(String id) throws IOException {
        String jsonValueString = (String) template.opsForValue().get(id);
        Love love = Love.create(jsonValueString);
        return Optional.of(love);
    }


}
