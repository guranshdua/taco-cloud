package dua.tacocloud.tacos.web;

import dua.tacocloud.tacos.Ingredient;
import dua.tacocloud.tacos.Ingredient.Type;
import dua.tacocloud.tacos.Taco;
import dua.tacocloud.tacos.TacoOrder;
import dua.tacocloud.tacos.data.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo)
    {
        this.ingredientRepo=ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model)
    {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        List<Ingredient> ingredientList= StreamSupport.stream(ingredients.spliterator(),false).collect(Collectors.toList());
        Type[] types = Ingredient.Type.values();
        for(Type type : types)
        {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredientList,type));
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
