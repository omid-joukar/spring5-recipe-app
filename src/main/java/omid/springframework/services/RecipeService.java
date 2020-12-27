package omid.springframework.services;

import omid.springframework.commands.RecipeCommand;
import omid.springframework.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    RecipeCommand findCommandById(long anyLong);
    void deleteById(long id);
}
