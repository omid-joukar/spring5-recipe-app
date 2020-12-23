package omid.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import omid.springframework.domain.Category;
import omid.springframework.domain.UnitOfMeasure;
import omid.springframework.repositories.CategoryRepository;
import omid.springframework.repositories.UnitOfMeasureRepository;
import omid.springframework.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
@Slf4j
@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){
        log.debug("Getting Index page");
        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }
}
