package omid.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import omid.springframework.commands.IngredientCommand;
import omid.springframework.services.IngredientService;
import omid.springframework.services.RecipeService;
import omid.springframework.services.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    @Autowired
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String showIngredients(@PathVariable String id, Model model){
        log.debug("Getting List of ingredients Recipe id : "+id);
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }
    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId ,
                                       @PathVariable String id, Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(id)));
        return "recipe/ingredient/show";
    }
    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(id)));
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }
    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug("saved recipe id :"+savedCommand.getRecipeId() );
        log.debug("saved ingredient id : "+ savedCommand.getId());
        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
    }
}
