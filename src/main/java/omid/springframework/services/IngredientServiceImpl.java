package omid.springframework.services;

import lombok.extern.slf4j.Slf4j;
import omid.springframework.commands.IngredientCommand;
import omid.springframework.converters.IngredientCommandToIngredient;
import omid.springframework.converters.IngredientToIngredientCommand;
import omid.springframework.domain.Ingredient;
import omid.springframework.domain.Recipe;

import omid.springframework.repositories.RecipeRepository;
import omid.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            log.error("recipe is not found by id:"+ recipeId);
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()){
            log.error("Recipe not found by id : "+ ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId()).orElseThrow(() ->new RuntimeException("Uom Not Found")));
            }else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
            .stream().filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
                    .findFirst().get());
        }
    }
}
