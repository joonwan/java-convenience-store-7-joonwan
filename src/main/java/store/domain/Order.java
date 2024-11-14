package store.domain;

import java.util.List;
import store.dto.OrderProductStatus;

public class Order {

    private final List<OrderProduct> orderProducts;

    public Order(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<OrderProductStatus> getOrderProductStatuses() {
        return orderProducts.stream()
                .map(OrderProduct::getOrderProductStatus)
                .toList();
    }
}
