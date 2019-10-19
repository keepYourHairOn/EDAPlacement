package EDAPlacementAppPg.Model.controller;


import EDAPlacementAppPg.EDAPlacementApp;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/result")
    public ResponseEntity<String> getResult(){
        Gson gson = new Gson();
        String result = gson.toJson(EDAPlacementApp.circuit());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return ResponseEntity.ok().headers(responseHeaders).body(result);
    }
}
