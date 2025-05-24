package dua.tacocloud.tacos.data;

import dua.tacocloud.tacos.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder,Long> {

}
