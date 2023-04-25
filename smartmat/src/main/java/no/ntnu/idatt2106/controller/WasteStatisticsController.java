package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@RequestMapping(value = "/waste") //TODO AUTH??
@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class WasteStatisticsController {

    @Autowired
    private WasteService wasteService;

    // GET TOTAL WASTE FOR AN ACCOUNT
    @GetMapping("/")
    public ResponseEntity<?> geMoneyLost(@AuthenticationPrincipal AccountEntity account){
        return new ResponseEntity<>(wasteService.getMoneyLost(account.getAccount_id()), HttpStatus.OK);
    }

    // GET WASTE SORTED BY CATEGORY
    @GetMapping("/category")
    public ResponseEntity<?> geyMoneyLostPerCategory(@AuthenticationPrincipal AccountEntity account) {
        return new ResponseEntity<>(wasteService.getMoneyLostPerCategory(account.getAccount_id()),HttpStatus.OK);
    }

    // GET WASTE FOR A GIVEN CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> geyMoneyLostByCategory(@AuthenticationPrincipal AccountEntity account,
                                                    @PathVariable long categoryId) {
        return new ResponseEntity<>(wasteService.getMoneyLostByCategory(account.getAccount_id(), categoryId),HttpStatus.OK);
    }

    // GET WASTE FOR EACH MONTH
    @GetMapping("/month")
    public ResponseEntity<?> geyMoneyLostPerMonth(@AuthenticationPrincipal AccountEntity account) {
        return new ResponseEntity<>(wasteService.getMoneyLostPerMonth(account.getAccount_id()),HttpStatus.OK);
    }

    // GET WASTE FOR A GIVEN MONTH
    @GetMapping("/month/{monthNumber}")
    public ResponseEntity<?> geMoneyLostByMonth(@AuthenticationPrincipal AccountEntity account,
                                                 @PathVariable int monthNumber){
        return new ResponseEntity<>(wasteService.getMoneyLostByMonth(account.getAccount_id(), monthNumber), HttpStatus.OK);
    }
}
