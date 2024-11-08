package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Order;
import store.domain.OrderProduct;
import store.domain.Product;
import store.repository.ProductRepository;
import store.util.validator.OrderInputValidator;

public class OrderService {

    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Order createOrder(String items) {
        OrderInputValidator.validateItemFormat(items);

        List<OrderProduct> orderProducts = Arrays.stream(items.split(","))
                .map(String::trim)
                .map(item -> createOrderProduct(item))
                .collect(Collectors.toList());

        return new Order(orderProducts, DateTimes.now());
    }

    private OrderProduct createOrderProduct(String item) {
        Product orderProduct = getProduct(item);
        int orderQuantity = getOrderQuantity(item);
        return new OrderProduct(orderProduct, orderQuantity);
    }

    private Product getProduct(String item) {
        String[] itemComponents = item.split("-");
        String productName = itemComponents[0].trim().substring(1);
        return productRepository.findByName(productName);
    }

    private int getOrderQuantity(String item) {
        try {
            String[] itemComponents = item.split("-");
            String rawOrderQuantity = itemComponents[1].trim();
            int startIndex = 0;
            int endIndex = rawOrderQuantity.length() - 1;

            return Integer.parseInt(rawOrderQuantity.substring(startIndex, endIndex));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("주문 수량은 정수만 입력할 수 있습니다.");
        }
    }
}
