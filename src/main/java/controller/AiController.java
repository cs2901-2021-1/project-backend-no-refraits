package controller;


import data.entities.AiDto;
import data.entities.Prediccion;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {
    static final String CLIENT_URL = "*";

    public AiController() {
        // Done
    }

    @PostMapping("/prediccion")
    @CrossOrigin(origins = CLIENT_URL)
    public Prediccion prediccion(@RequestBody AiDto aiDto, @RequestHeader("Authorization") String token){
        return new Prediccion("23");
    }

}
