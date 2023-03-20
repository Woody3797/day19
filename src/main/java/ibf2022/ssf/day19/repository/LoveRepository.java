package ibf2022.ssf.day19.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public List<Love> getAllMatchMaking() throws IOException {
        Set<String> allKeys = template.keys("*");
        List<Love> allLove = new ArrayList<>();
        for (String key : allKeys) {
            String result = (String) template.opsForValue().get(key);
            allLove.add(Love.create(result));
        }
        return allLove;
    }

}
