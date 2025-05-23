package dua.tacocloud.tacos.data;

import dua.tacocloud.tacos.Ingredient;
import dua.tacocloud.tacos.Taco;
import dua.tacocloud.tacos.TacoOrder;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository{
    private final JdbcOperations jdbcOperations;
    public JdbcOrderRepository(JdbcOperations jdbcOperations)
    {
        this.jdbcOperations=jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into taco_order"+
                "(delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, ccnumber, cc_expiration, cc_cvv, placed_at)"+
                "values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                order.getDeliveryName(),
                order.getDeliveryStreet(),
                order.getDeliveryCity(),
                order.getDeliveryState(),
                order.getDeliveryZip(),
                order.getCcNumber(),
                order.getCcExpiration(),
                order.getCcCVV(),
                order.getPlacedAt()
        ));

        GeneratedKeyHolder keyHolder= new GeneratedKeyHolder();
        jdbcOperations.update(psc,keyHolder);
        Number key=(Number) keyHolder.getKeys().get("id");
        long orderId = key.longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i=0;
        for(Taco taco : tacos){
            saveTaco(orderId,i++,taco);
        }
        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco)
    {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into taco"+
                        "(name, taco_order, taco_order_key, created_at) "+
                        "values (?,?,?,?)",
                Types.VARCHAR, Type.LONG,Type.LONG,Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                orderId,
                                orderKey,
                                taco.getCreatedAt()
                        )
                );

        GeneratedKeyHolder keyHolder= new GeneratedKeyHolder();
        jdbcOperations.update(psc,keyHolder);
        Number key=(Number) keyHolder.getKeys().get("id");
        long tacoId = key.longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId,taco.getIngredients());
        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<Ingredient> ingredients)
    {
        int key=0;
        for(Ingredient ingredient: ingredients)
        {
            jdbcOperations.update("insert into ingredient_ref (ingredient, taco, taco_key) "+
                    "values (?,?,?)",
                    ingredient.getId(),tacoId,key++
                    );
        }
    }
}
