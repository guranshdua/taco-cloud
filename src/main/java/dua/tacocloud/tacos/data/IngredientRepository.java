package dua.tacocloud.tacos.data;

import dua.tacocloud.tacos.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository  extends CrudRepository<Ingredient,String> {

}
