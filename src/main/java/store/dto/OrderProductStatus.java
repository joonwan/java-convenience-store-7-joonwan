package store.dto;

import store.domain.OrderProductType;

public class OrderProductStatus {

    private final OrderProductType orderProductType;
    private final int additionalGiftProductCount;
    private final int notApplicableProductCount;

    public OrderProductStatus(OrderProductType orderProductType, int additionalGiftProductCount,
                              int notApplicableProductCount) {
        this.orderProductType = orderProductType;
        this.additionalGiftProductCount = additionalGiftProductCount;
        this.notApplicableProductCount = notApplicableProductCount;
    }

    public OrderProductType getOrderProductType() {
        return orderProductType;
    }

    public int getAdditionalGiftProductCount() {
        return additionalGiftProductCount;
    }

    public int getNotApplicableProductCount() {
        return notApplicableProductCount;
    }

    @Override
    public String toString() {
        return "OrderProductStatus{" +
                "orderProductType=" + orderProductType +
                ", additionalGiftProductCount=" + additionalGiftProductCount +
                ", notApplicableProductCount=" + notApplicableProductCount +
                '}';
    }
}
