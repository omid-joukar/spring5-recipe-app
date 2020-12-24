package omid.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {
    Category category;
    @Before
    public void setUp(){
        category = new Category();
    }
    @Test
    public void getId() {
     long idValue = 4L;
        category.setId(idValue);
     assertEquals(java.util.Optional.of(idValue),java.util.Optional.of(category.getId()));
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}