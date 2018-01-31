package ie.gmit.sw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChessController {

    @RequestMapping("/")
    public String index(){
        return "<h1>Hi, Hi from Cian</h1>";
    }
}
