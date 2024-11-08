package store.domain;

public class OrderProduct {

    private final Product product;
    private int quantity;

    public OrderProduct(Product product, int quantity) {

        validatePositiveQuantity(quantity);
        validateEnoughQuantity(product, quantity);

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    private void validatePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("주문 수량 입력시 양수만 입력할 수 있습니다.");
        }
    }

    private static void validateEnoughQuantity(Product product, int quantity) {
        if (!product.isEnoughStockQuantity(quantity)) {
            throw new IllegalArgumentException("주문 수량은 총 재고수량을 초과할 수 없습니다.");
        }
    }
}
