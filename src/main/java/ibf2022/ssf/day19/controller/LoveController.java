package ibf2022.ssf.day19.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class LoveController {
    
    @GetMapping
    public ResponseEntity<String> getLove() {
        return 
    }
}
