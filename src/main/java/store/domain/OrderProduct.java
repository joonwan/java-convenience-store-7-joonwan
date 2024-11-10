package store.domain;

import static store.domain.OrderProductType.*;
import static store.errormessage.OrderErrorMessage.NEGATIVE_ORDER_QUANTITY_ERROR_MESSAGE;
import static store.errormessage.OrderErrorMessage.NOT_ENOUGH_STOCK_QUANTITY_ERROR_MESSAGE;

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
        if (type.equals(NOT_APPLIED)) {
            return createNotAppliedTypeOrderProductStatus(type);
        }

        if (type.equals(PARTIAL_APPLIED)) {
            return createPartialTypeOrderProductStatus(type);
        }

        return createPromotionAppliedTypeProductStatus(type);
    }

    private OrderProductStatus createNotAppliedTypeOrderProductStatus(OrderProductType type) {
        String productName = product.getName();
        return new OrderProductStatus(productName, orderQuantity, type, 0, 0);
    }

    private OrderProductStatus createPartialTypeOrderProductStatus(OrderProductType type) {
        String productName = product.getName();
        int additionalProductCount = getAdditionalGiftProductCount();
        int notApplicableCount = getNotApplicableProductCount();
        return new OrderProductStatus(productName, orderQuantity, type, additionalProductCount, notApplicableCount);
    }

    private OrderProductStatus createPromotionAppliedTypeProductStatus(OrderProductType type) {
        String productName = product.getName();
        int additionalProductCount = getAdditionalGiftProductCount();
        return new OrderProductStatus(productName, orderQuantity, type, additionalProductCount, 0);
    }

    private int getNotApplicableProductCount() {
        return product.getNotApplicableProductCount(orderQuantity);
    }

    private int getAdditionalGiftProductCount() {
        return product.getAdditionalGiftProductCount(orderQuantity);
    }

    private void validatePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(NEGATIVE_ORDER_QUANTITY_ERROR_MESSAGE);
        }
    }

    private void validateEnoughQuantity(Product product, int quantity, LocalDateTime orderDateTime) {
        if (!product.isEnoughStockQuantity(quantity, orderDateTime)) {
            throw new IllegalArgumentException(NOT_ENOUGH_STOCK_QUANTITY_ERROR_MESSAGE);
        }
    }

    private OrderProductType getOderProductType() {
        if (!product.isPromotionApplicable(orderDateTime)) {
            return NOT_APPLIED;
        }

        if (product.isEnoughPromotionStockQuantity(orderQuantity)) {
            return determineAdditionalPromotion();
        }

        return PARTIAL_APPLIED;
    }

    private OrderProductType determineAdditionalPromotion() {
        if (product.isPossibleGiveMoreProduct(orderQuantity)) {
            return CAN_RECEIVE;
        }
        return CANNOT_RECEIVE;
    }
}
