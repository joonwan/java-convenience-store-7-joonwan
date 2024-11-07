package store.domain;

import java.util.Objects;
import store.dto.StockStatus;

public class Product {

    private String name;
    private int price;
    private int promotionStockQuantity;
    private int defaultStockQuantity;
    private String promotionName;

    public Product(String name, int price, int promotionStockQuantity, int defaultStockQuantity) {
        this.name = name;
        this.price = price;
        this.promotionStockQuantity = promotionStockQuantity;
        this.defaultStockQuantity = defaultStockQuantity;
    }

    public void increasePromotionQuantity(int amount) {
        this.promotionStockQuantity += amount;
    }

    public void increaseDefaultQuantity(int amount) {
        this.defaultStockQuantity += amount;
    }

    public void applyPromotion(String promotionName) {
        this.promotionName = promotionName;
    }

    public StockStatus getStockStatus() {
        return new StockStatus(name, price, promotionStockQuantity, defaultStockQuantity, promotionName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

}
