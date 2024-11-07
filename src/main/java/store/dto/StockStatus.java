package store.dto;

public class StockStatus {

    private final String productName;
    private final int price;
    private final int promotionStockQuantity;
    private final int defaultStockQuantity;
    private final String promotionName;

    public StockStatus(String productName, int price, int promotionStockQuantity, int defaultStockQuantity,
                       String promotionName) {
        this.productName = productName;
        this.price = price;
        this.promotionStockQuantity = promotionStockQuantity;
        this.defaultStockQuantity = defaultStockQuantity;
        this.promotionName = promotionName;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getPromotionStock() {
        return promotionStockQuantity;
    }

    public int getDefaultStockQuantity() {
        return defaultStockQuantity;
    }

    public String getPromotionName() {
        return promotionName;
    }
}
