package no.ntnu.idatt2106.controller;


import no.ntnu.idatt2106.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/recipes")
@RestController
public class RecipeController {

    @Autowired
    private FridgeRepository fridgeRepository;

    @GetMapping("/weekMenu")
    public ResponseEntity<?> getWeekMenu() {
        return null;
    }

    @GetMapping("/recipe")
    public ResponseEntity<?> getRecipe() {
        return null;
    }

    @GetMapping("/nRecipes")
    public ResponseEntity<?> getNRecipes() {
        return null;
    }
}
