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
public class WasteStatisticsController {

    @Autowired
    private WasteService wasteService;

    @GetMapping("/")
    public ResponseEntity<?> geMoneyLost(@AuthenticationPrincipal AccountEntity account){
        return new ResponseEntity<>(wasteService.getMoneyLost(account.getAccount_id()), HttpStatus.OK);
    }
}
