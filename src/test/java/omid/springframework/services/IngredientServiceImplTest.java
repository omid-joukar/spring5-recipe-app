package omid.springframework.services;

import omid.springframework.commands.IngredientCommand;
import omid.springframework.converters.IngredientCommandToIngredient;
import omid.springframework.converters.IngredientToIngredientCommand;
import omid.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import omid.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import omid.springframework.domain.Ingredient;
import omid.springframework.domain.Recipe;
import omid.springframework.domain.UnitOfMeasure;
import omid.springframework.repositories.RecipeRepository;
import omid.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientService ingredientService;
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository,ingredientToIngredientCommand,ingredientCommandToIngredient,unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }
    @Test
    public void testSaveRecipeCommand() throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);
        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        assertEquals(Long.valueOf(3L),savedCommand.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }

}