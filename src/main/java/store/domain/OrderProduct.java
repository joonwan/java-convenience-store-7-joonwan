package store.domain;

import static store.domain.OrderProductType.*;

import java.time.LocalDateTime;
import store.dto.OrderProductStatus;

public class OrderProduct {

    private final int orderQuantity;
    private final Product product;
    private final LocalDateTime orderDateTime;

    public OrderProduct(Product product, int orderQuantity, LocalDateTime orderDateTime) {
        validatePositiveQuantity(orderQuantity);
        validateEnoughQuantity(product, orderQuantity, orderDateTime);

        this.product = product;
        this.orderQuantity = orderQuantity;
        this.orderDateTime = orderDateTime;
    }

    public OrderProductStatus getOrderProductStatus() {
        OrderProductType type = getOderProductType();
        if (type.equals(DEFAULT)) {
            return new OrderProductStatus(product.getName(), orderQuantity, type, 0, 0);
        }

        if (type.equals(ADDITIONAL_PROMOTION)) {
            return new OrderProductStatus(product.getName(), orderQuantity, type, getAdditionalGiftProductCount(), 0);
        }

        return new OrderProductStatus(product.getName(), orderQuantity,type, 0, getNotApplicableProductCount());
    }

    private int getNotApplicableProductCount() {
        return product.getNotApplicableProductCount(orderQuantity);
    }

    private int getAdditionalGiftProductCount() {
        return product.getAdditionalGiftProductCount();
    }

    private void validatePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("주문 수량 입력시 양수만 입력할 수 있습니다.");
        }
    }

    private void validateEnoughQuantity(Product product, int quantity, LocalDateTime orderDateTime) {
        if (!product.isEnoughStockQuantity(quantity, orderDateTime)) {
            throw new IllegalArgumentException("주문 수량은 총 재고수량을 초과할 수 없습니다.");
        }
    }

    private OrderProductType getOderProductType() {
        if (!product.isPromotionApplicable(orderDateTime)) {
            return DEFAULT;
        }

        if (product.isEnoughPromotionStockQuantity(orderQuantity)) {
            return determineAdditionalPromotion();
        }

        return PARTIAL_PROMOTION;
    }

    private OrderProductType determineAdditionalPromotion() {
        if (product.isAdditionalPromotion(orderQuantity)) {
            return ADDITIONAL_PROMOTION;
        }
        return DEFAULT;
    }
}
