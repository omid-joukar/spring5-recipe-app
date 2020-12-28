package omid.springframework.repositories;

import omid.springframework.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {
}
