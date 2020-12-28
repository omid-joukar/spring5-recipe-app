package omid.springframework.services;

import lombok.extern.slf4j.Slf4j;
import omid.springframework.commands.IngredientCommand;
import omid.springframework.converters.IngredientToIngredientCommand;
import omid.springframework.domain.Recipe;
import omid.springframework.repositories.IngredientRepository;
import omid.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            log.error("recipe is not founf bi id:"+ recipeId);
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional =
                recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();
        if (!ingredientCommandOptional.isPresent()){
            log.error("Ingredient is not found: "+id);
        }
        return ingredientCommandOptional.get();
    }
}
