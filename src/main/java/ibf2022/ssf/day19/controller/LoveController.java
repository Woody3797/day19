package ibf2022.ssf.day19.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf2022.ssf.day19.model.Love;
import ibf2022.ssf.day19.service.LoveService;

@Controller
@RequestMapping(path = {"/"})
public class LoveController {
    
    @Autowired
    private LoveService loveService;

    @GetMapping(path = {"/", "/index", "/index.html"})
    public String getIndex(Model model) {
        model.addAttribute("love", new Love());
        return "index";
    }

    @GetMapping(path = "/getPercentage")
    public String getLove(@RequestParam(required = true) String fname, @RequestParam(required = true) String sname, Model model) throws IOException {
        Optional<Love> love = loveService.getLove(fname, sname);
        loveService.saveLove(love.get());
        model.addAttribute("love", love.get());
        return "result";
    }

    @GetMapping(path = "/list")
    public String getAll(Model model) throws IOException {
        List<Love> love = loveService.getLoveList();
        love.sort(Comparator.comparing(Love::getPercentage).reversed());
        model.addAttribute("love", love);
        return "list";
    }

    @GetMapping(path = "{id}")
    public String findById(Model model, @PathVariable String id) throws IOException {
        Love love = loveService.getById(id);
        model.addAttribute("love", love);
        return "result";
    }

}
