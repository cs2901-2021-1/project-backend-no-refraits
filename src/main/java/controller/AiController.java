package controller;


import data.entities.AiDto;
import data.entities.Prediccion;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {
    static final String CLIENT_URL = "*";

    @PostMapping("/prediccion")
    @CrossOrigin(origins = CLIENT_URL)
    public Prediccion prediccion(@RequestBody AiDto aiDto, @RequestHeader("Authorization") String token){
        System.out.println(aiDto.getDireccion());
        System.out.println(aiDto.getCurso());


        return new Prediccion("23");
    }

}