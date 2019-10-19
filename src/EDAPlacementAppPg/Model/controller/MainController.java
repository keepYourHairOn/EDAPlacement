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
        //TODO: remove
//        int [][][] sample = {{{0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2},
//        {0,0,0,1,0,0,0,0,0,0,1,0,0}, {0,0,0,1,0,0,0,0,0,0,1,0,0}, {0,0,0,1,0,0,0,0,0,0,1,0,0}, {0,0,0,1,0,0,0,0,0,0,1,0,0}, {0,0,0,1,0,0,0,0,0,0,1,0,0},
//        {0,0,0,1,1,1,0,1,1,1,1,0,0},
//        {0,0,0,0,0,1,0,1,0,0,0,0,0},
//        {0,0,0,0,4,4,4,4,4,0,0,0,0}, {0,0,0,0,4,4,4,4,4,0,0,0,0}, {0,0,0,0,4,4,4,4,4,0,0,0,0}, {0,0,0,0,4,4,4,4,4,0,0,0,0},{0,0,0,0,4,4,4,4,4,0,0,0,0}
//    },
//    {
//        {0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2}, {0,2,2,2,2,2,0,0,2,2,2,2,2},
//        {0,1,0,0,0,0,0,0,0,0,0,0,1}, {0,1,0,0,0,0,0,0,0,0,0,0,1}, {0,1,0,0,0,0,0,0,0,0,0,0,1}, {0,1,0,0,0,0,0,0,0,0,0,0,1},
//        {0,1,1,1,1,1,0,0,1,1,1,1,1},
//        {0,0,0,0,0,1,0,0,1,0,0,0,0},
//        {0,0,0,0,0,1,0,0,1,0,0,0,0},
//        {0,0,0,0,4,4,4,4,4,0,0,0,0}, {0,0,0,0,4,4,4,4,4,0,0,0,0}, {0,0,0,0,4,4,4,4,4,0,0,0,0}, {0,0,0,0,4,4,4,4,4,0,0,0,0},{0,0,0,0,4,4,4,4,4,0,0,0,0},
//        {0,0,0,0,0,0,1,0,0,0,0,0,0},
//        {0,0,0,0,0,0,1,0,0,0,0,0,0},
//        {0,0,0,0,0,0,1,0,0,0,0,0,0},
//        {0,0,0,0,3,3,3,3,3,0,0,0,0}, {0,0,0,0,3,3,3,3,3,0,0,0,0}, {0,0,0,0,3,3,3,3,3,0,0,0,0}, {0,0,0,0,3,3,3,3,3,0,0,0,0}, {0,0,0,0,3,3,3,3,3,0,0,0,0}
//    }
//    };
        //TODO: change sample to EDAPlacementApp.circuit();sample
        int[][][] array = EDAPlacementApp.circuit();
        for (int i = 0; i < array.length; i++) {
            int[][] arr1 = array[i];
            for (int j = 0; j < arr1.length; j++) {
                int[] arr2 = arr1[j];
                for (int k = 0; k < arr2.length; k++) {
                    System.out.print(arr2[k] + " ");
                }
                System.out.println();
            }
            System.out.println("--------------------------------------------------");
        }
        
        String result = gson.toJson(EDAPlacementApp.circuit());
//        System.out.println(result);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return ResponseEntity.ok().headers(responseHeaders).body(result);
    }
}
