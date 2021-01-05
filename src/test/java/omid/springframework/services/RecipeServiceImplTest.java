package omid.springframework.services;

import omid.springframework.converters.RecipeCommandToRecipe;
import omid.springframework.converters.RecipeToRecipeCommand;
import omid.springframework.domain.Recipe;
import omid.springframework.exceptions.NotFoundException;
import omid.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(),1);
        verify(recipeRepository,times(1)).findAll();
    }
    @Test
    public void findById(){
        Recipe returnRecipe = new Recipe();
        returnRecipe.setId(1L);
        when(recipeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(returnRecipe));
        Recipe recipe = recipeService.findById(1L);
        assertNotNull(recipe);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }
    @Test(expected = NotFoundException.class)
    public void getRecipeByIdNotFOund() throws Exception{
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Recipe recipeReturned = recipeService.findById(1L);
    }

    @Test
    public void testDeleteBYId(){
        Long idToDelete = Long.valueOf(2l);
        recipeService.deleteById(idToDelete);
        verify(recipeService,times(1)).deleteById(anyLong());
    }
}