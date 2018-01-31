package ie.gmit.sw.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {

    @RequestMapping("/")
    public String index(){
        return "<h1>Hi lads, Cian first commit!, Majo here</h1>";
    }
}
