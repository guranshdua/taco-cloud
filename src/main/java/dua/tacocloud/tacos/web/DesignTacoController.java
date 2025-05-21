package dua.tacocloud.tacos.web;

import dua.tacocloud.tacos.Ingredient;
import dua.tacocloud.tacos.Ingredient.Type;
import dua.tacocloud.tacos.Taco;
import dua.tacocloud.tacos.TacoOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    @ModelAttribute
    public void addIngredientsToModel(Model model)
    {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();

        ingredients.add(new Ingredient("FLTO","Flour Tortilla", Type.WRAP));
        ingredients.add(new Ingredient("COTO","Corn Tortilla", Type.WRAP));
        ingredients.add(new Ingredient("GRBF","Ground Beef", Type.PROTEIN));
        ingredients.add(new Ingredient("CARN","Carnitas", Type.PROTEIN));
        ingredients.add(new Ingredient("TMTO","Diced Tomatoes", Type.VEGGIES));
        ingredients.add(new Ingredient("LETC","Lettuce", Type.VEGGIES));
        ingredients.add(new Ingredient("CHED","Cheddar", Type.CHEESE));
        ingredients.add(new Ingredient("JACK","Monterrey Jack", Type.CHEESE));
        ingredients.add(new Ingredient("SLSA","Salsa", Type.SAUCE));
        ingredients.add(new Ingredient("SRCR","Sour Cream", Type.SAUCE));

        Type[] types = Ingredient.Type.values();
        for(Type type : types)
        {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
    }

    @ModelAttribute(name = "taco")
    public Taco taco()
    {
        return new Taco();
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order()
    {
        return new TacoOrder();
    }

    @GetMapping
    public String showDesignForm()
    {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder)
    {
        if(errors.hasErrors())
        {
            log.warn(errors.toString());
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco : {}",taco);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type)
    {
        return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }
}
