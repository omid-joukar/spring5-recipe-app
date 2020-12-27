package omid.springframework.controllers;

import omid.springframework.commands.RecipeCommand;
import omid.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id,Model model){
        model.addAttribute("recipe",recipeService.findById(new Long(id)));
        return "recipe/show";
    }
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }
    @PostMapping
    @RequestMapping("/recipe")
    public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id,Model model){
        model.addAttribute("recipe",recipeService.findCommandById(new Long(id)));
        return "recipe/recipeform";
    }
}
