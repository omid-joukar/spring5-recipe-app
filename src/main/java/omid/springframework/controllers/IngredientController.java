package omid.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import omid.springframework.commands.RecipeCommand;
import omid.springframework.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    @Autowired
    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String showIngredients(@PathVariable String id, Model model){
        log.debug("Getting List of ingredients Recipe id : "+id);
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }
}
