package dua.tacocloud.tacos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Data
@Table("taco")
public class Taco {
    private static final long serialVersionUID=1L;

    @Id
    private Long id;

    @NotNull
    @Size(min=5,message = "Name must be atleast 5 characters long.")
    private String name;

    @NotNull
    @Size(min=1, message = "You must choose atleast 1 ingredient")
    private List<Ingredient> ingredients;

    private Date createdAt= new Date();
}
