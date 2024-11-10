package store.service;

import static store.domain.OrderProductType.*;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.BillPaper;
import store.domain.Order;
import store.domain.OrderProduct;
import store.domain.OrderProductType;
import store.domain.Product;
import store.dto.OrderProductStatus;
import store.repository.ProductRepository;
import store.util.validator.OrderInputValidator;

public class OrderService {

    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Order createOrder(String items) {
        OrderInputValidator.validateItemFormat(items);

        Map<String, Integer> parsedItems = parseItems(items);
        List<OrderProduct> orderProducts = createOrderProducts(parsedItems);
        return new Order(orderProducts);
    }

    private Map<String, Integer> parseItems(String items) {
        Map<String, Integer> parsedItems = new LinkedHashMap<>();

        for(String item : items.split(",")) {
            String productName = getProductName(item);
            int orderQuantity = getOrderQuantity(item);

            updateParsedItems(parsedItems, productName, orderQuantity);
        }
        return parsedItems;
    }

    private String getProductName(String item) {
        String[] itemComponents = item.split("-");
        return itemComponents[0].trim().substring(1);
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

    private void updateParsedItems(Map<String, Integer> parsedItems, String productName, int orderQuantity) {
        if (parsedItems.containsKey(productName)) {
            int findOrderQuantity = parsedItems.get(productName);

            parsedItems.put(productName, findOrderQuantity + orderQuantity);
            return ;
        }

        parsedItems.put(productName, orderQuantity);
    }

    private List<OrderProduct> createOrderProducts (Map<String, Integer> parsedItems) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (String productName : parsedItems.keySet()) {
            Product product = productRepository.findByName(productName);
            int orderQuantity = parsedItems.get(productName);
            orderProducts.add(new OrderProduct(product, orderQuantity, DateTimes.now()));
        }
        return orderProducts;
    }

    public BillPaper getBillPaper(List<OrderProductStatus> confirmedOrderProductStatuses) {
        BillPaper billPaper = new BillPaper();

        for (OrderProductStatus orderProductStatus : confirmedOrderProductStatuses) {
            decreaseStockQuantity(orderProductStatus);
            updateBillPaper(billPaper, orderProductStatus);
        }
        return billPaper;
    }

    private void decreaseStockQuantity(OrderProductStatus orderProductStatus) {
        String productName = orderProductStatus.getProductName();
        Product product = productRepository.findByName(productName);
        OrderProductType type = orderProductStatus.getOrderProductType();
        int orderQuantity = orderProductStatus.getOrderQuantity();

        product.decreaseStockQuantityByType(type, orderQuantity);
    }

    private void updateBillPaper(BillPaper billPaper, OrderProductStatus orderProductStatus) {
        String productName = orderProductStatus.getProductName();
        Product product = productRepository.findByName(productName);

        int orderQuantity = orderProductStatus.getOrderQuantity();
        int additionalReceiveCount = orderProductStatus.getAdditionalReceiveCount();

        billPaper.updateOrder(product, orderQuantity, additionalReceiveCount);
    }
}
