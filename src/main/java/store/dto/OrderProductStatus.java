package store.dto;

import store.domain.OrderProductType;

public class OrderProductStatus {

    private final String productName;
    private int orderQuantity;
    private final OrderProductType orderProductType;
    private final int additionalReceiveCount;
    private final int notApplicableProductCount;

    public OrderProductStatus(String productName, int orderQuantity, OrderProductType orderProductType, int additionalReceiveCount,
                              int notApplicableProductCount) {
        this.productName = productName;
        this.orderQuantity = orderQuantity;
        this.orderProductType = orderProductType;
        this.additionalReceiveCount = additionalReceiveCount;
        this.notApplicableProductCount = notApplicableProductCount;
    }

    public String getProductName() {
        return productName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public OrderProductType getOrderProductType() {
        return orderProductType;
    }

    public int getAdditionalReceiveCount() {
        return additionalReceiveCount;
    }

    public int getNotApplicableProductCount() {
        return notApplicableProductCount;
    }

    public void setOrderQuantity(int quantity) {
        this.orderQuantity += quantity;
    }

}
