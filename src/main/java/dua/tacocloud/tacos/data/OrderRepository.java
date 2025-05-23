package dua.tacocloud.tacos.data;

import dua.tacocloud.tacos.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
