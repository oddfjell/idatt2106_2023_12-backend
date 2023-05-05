package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for all /waste endpoints
 */
@RequestMapping(value = "/waste")
@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:4173/"}, allowCredentials = "true")
public class WasteStatisticsController {

    /**
     * WasteService field injection
     */
    @Autowired
    private WasteService wasteService;

    /**
     * Returns the total waste from an account
     * @param account AccountEntity
     * @return ResponseEntity<?>
     */
    @GetMapping("/")
    public ResponseEntity<?> geMoneyLost(@AuthenticationPrincipal AccountEntity account) {
        return new ResponseEntity<>(wasteService.getMoneyLost(account.getAccount_id()), HttpStatus.OK);
    }

    /**
     * Returns the total waste from an account sorted in category's
     * @param account AccountEntity
     * @return ResponseEntity<?>
     */
    @GetMapping("/category")
    public ResponseEntity<?> geyMoneyLostPerCategory(@AuthenticationPrincipal AccountEntity account) {
        return new ResponseEntity<>(wasteService.getMoneyLostPerCategory(account.getAccount_id()), HttpStatus.OK);
    }

    /**
     * Returns the money lost by an account in a category
     * @param account AccountEntity
     * @param categoryId long
     * @return ResponseEntity<?>
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> geyMoneyLostByCategory(@AuthenticationPrincipal AccountEntity account,
                                                    @PathVariable long categoryId) {
        return new ResponseEntity<>(wasteService.getMoneyLostByCategory(account.getAccount_id(), categoryId), HttpStatus.OK);
    }

    /**
     * Returns the money lost per month
     * @param account AccountEntity
     * @param month int
     * @return ResponseEntity<?>
     */
    @GetMapping("/month/{month}")
    public ResponseEntity<?> geyMoneyLostPerMonth(@AuthenticationPrincipal AccountEntity account,
                                                  @PathVariable int month) {
        return new ResponseEntity<>(wasteService.getTotalWastePerDateByMonth(account, month), HttpStatus.OK);
    }
}
