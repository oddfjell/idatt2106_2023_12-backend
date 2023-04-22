package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/waste") //TODO AUTH??
@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class WasteStatisticsController {

    @Autowired
    private WasteService wasteService;

    @GetMapping("/")
    public ResponseEntity<?> geMoneyLost(@AuthenticationPrincipal AccountEntity account){
        return new ResponseEntity<>(wasteService.getMoneyLost(account.getAccount_id()), HttpStatus.OK);
    }

    @GetMapping("/month/{monthNumber}")
    public ResponseEntity<?> geMoneyLostPerMonth(@AuthenticationPrincipal AccountEntity account,
                                                 @PathVariable int monthNumber){
        return new ResponseEntity<>(wasteService.getMoneyLostPerMonth(account.getAccount_id(), monthNumber), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> geyMoneyLostByCategory(@AuthenticationPrincipal AccountEntity account,
                                                    @PathVariable long categoryId) {
        return new ResponseEntity<>(wasteService.getMoneyLostByCategory(account.getAccount_id(), categoryId),HttpStatus.OK);
    }
}
