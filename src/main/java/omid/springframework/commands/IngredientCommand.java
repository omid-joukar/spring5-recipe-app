package omid.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private Long recipeId;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

}