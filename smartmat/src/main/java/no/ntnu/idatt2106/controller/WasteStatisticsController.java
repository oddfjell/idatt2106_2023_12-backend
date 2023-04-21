package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/waste") //TODO AUTH??
@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class WasteStatisticsController {

    @Autowired
    private WasteService wasteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> geMoneyLost(@PathVariable long id){
        return new ResponseEntity<>(wasteService.getMoneyLost(id), HttpStatus.OK);
    }
}