package id.ac.ui.cs.advancedprogramming.sifost.tes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index(){

        return "Hello, M'Lady";

    }
}