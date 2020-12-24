package omid.springframework.controllers;

import omid.springframework.domain.Recipe;
import omid.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    IndexController indexController;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }
    @Test
    public void testMockMvc()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    @Test
    public void getIndexPage() {
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe1);
        recipes.add(recipe2);
        when(recipeService.getRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        String viewModel = indexController.getIndexPage(model);

        assertEquals("index",viewModel);
        verify(recipeService,times(1)).getRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2,setInController.size());

    }
}