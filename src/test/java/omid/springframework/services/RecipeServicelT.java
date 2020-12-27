package omid.springframework.services;

import omid.springframework.commands.RecipeCommand;
import omid.springframework.converters.RecipeCommandToRecipe;
import omid.springframework.converters.RecipeToRecipeCommand;
import omid.springframework.domain.Recipe;
import omid.springframework.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServicelT {
    public static final String NEW_DESCRIPTION = "New Description";
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Autowired
    RecipeService recipeService;

    @Test
    @Transactional
    public void isCorrect(){
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe source = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(source);
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand returnServiceSource = recipeService.saveRecipeCommand(testRecipeCommand);
        assertEquals(NEW_DESCRIPTION, returnServiceSource.getDescription());
        assertEquals(source.getId(), returnServiceSource.getId());
        assertEquals(source.getCategories().size(), returnServiceSource.getCategories().size());
        assertEquals(source.getIngredients().size(), returnServiceSource.getIngredients().size());
    }

}