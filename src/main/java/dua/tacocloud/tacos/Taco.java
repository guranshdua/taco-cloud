package dua.tacocloud.tacos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Taco {
    private static final long serialVersionUID=1L;
    private Long id;

    @NotNull
    @Size(min=5,message = "Name must be atleast 5 characters long.")
    private String name;

    @NotNull
    @Size(min=1, message = "You must choose atleast 1 ingredient")
    private List<Ingredient> ingredients;

    private Date createdAt= new Date();
}
