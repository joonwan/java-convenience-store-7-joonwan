package store.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import store.dto.StockStatus;

public class Product {

    private String name;
    private int price;
    private int promotionStockQuantity;
    private int defaultStockQuantity;
    private Promotion promotion;

    public Product(String name, int price, int promotionStockQuantity, int defaultStockQuantity) {
        this.name = name;
        this.price = price;
        this.promotionStockQuantity = promotionStockQuantity;
        this.defaultStockQuantity = defaultStockQuantity;
    }

    public String getName() {
        return name;
    }

    public void increasePromotionQuantity(int amount) {
        this.promotionStockQuantity += amount;
    }

    public void increaseDefaultQuantity(int amount) {
        this.defaultStockQuantity += amount;
    }

    public void registerPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void clearPromotion() {
        this.promotion = null;
    }

    public StockStatus getStockStatus() {
        String promotionName = "";
        if (promotion != null) {
            promotionName = promotion.getName();
        }

        return new StockStatus(name, price, promotionStockQuantity, defaultStockQuantity, promotionName);
    }

    public boolean isEnoughStockQuantity(int orderQuantity, LocalDateTime orderDateTime) {
        if (promotion == null || !promotion.isAvailableApplyPromotion(orderDateTime)) {
            return orderQuantity <= defaultStockQuantity;
        }

        return orderQuantity <= (promotionStockQuantity + defaultStockQuantity);
    }

    public boolean isEnoughPromotionStockQuantity(int orderQuantity) {
        return orderQuantity <= promotionStockQuantity;
    }

    public boolean isPromotionApplicable(LocalDateTime orderDateTime) {
        return promotion != null && promotion.isAvailableApplyPromotion(orderDateTime);
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }

    public boolean isAdditionalPromotion(int orderQuantity) {
        return promotion.isPossibleGiveMoreProduct(orderQuantity, promotionStockQuantity);
    }

    public int getAdditionalGiftProductCount() {
        return promotion.getAdditionalGiftProductCount();
    }

    public int getNotApplicableProductCount(int orderQuantity) {
        return orderQuantity - promotion.getPromotionAppliedQuantity(promotionStockQuantity);
    }


}
