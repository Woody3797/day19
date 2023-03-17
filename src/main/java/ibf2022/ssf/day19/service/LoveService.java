package ibf2022.ssf.day19.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.ssf.day19.model.Love;
import ibf2022.ssf.day19.repository.LoveRepository;

@Service
public class LoveService {

    @Autowired
    private LoveRepository loveRepo;
    
    @Value("${day19.love.calculator.url}")
    private String loveCalculatorURL;

    @Value("${day19.love.calculator.api.key}")
    private String loveCalculatorAPIKey;

    @Value("${day19.love.calculator.host}")
    private String loveCalculatorHost;

    public Optional<Love> getLove(String fname, String sname) throws IOException {
        String loveURL = UriComponentsBuilder.fromUriString(loveCalculatorURL).queryParam("sname", sname.replaceAll(" ", "+")).queryParam("fname", fname.replaceAll(" ", "+")).toUriString();

        RequestEntity<Void> request = RequestEntity.get(loveURL).header("X-RapidAPI-Key", loveCalculatorAPIKey).header("X-RapidAPI-Host", loveCalculatorHost).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(request, String.class);
        Love love = Love.create(response.getBody());

        if (love != null) {
            return Optional.of(love);
        }
        return Optional.empty();
    }

    public Optional<Love> getById(String id) throws IOException {
        return loveRepo.findById(id);
    }

    public void saveLove(Love love) {
        loveRepo.save(love);
    }
}
