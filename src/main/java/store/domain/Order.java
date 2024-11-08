package store.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final List<OrderProduct> orderProducts;
    private final LocalDateTime orderDateTime;

    public Order(List<OrderProduct> orderProducts, LocalDateTime orderDateTime) {
        this.orderProducts = orderProducts;
        this.orderDateTime = orderDateTime;
    }

    public int size() {
        return orderProducts.size();
    }
}
