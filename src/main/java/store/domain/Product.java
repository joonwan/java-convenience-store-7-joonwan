package store.domain;

import static store.domain.OrderProductType.*;

import java.time.LocalDateTime;
import store.dto.StockStatus;

public class Product {

    private final String name;
    private final int price;
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

    public boolean isPossibleGiveMoreProduct(int orderQuantity) {
        return promotion.isPossibleGiveMoreProduct(orderQuantity, promotionStockQuantity);
    }

    public int getAdditionalGiftProductCount(int orderQuantity) {
        return promotion.getAdditionalGiftProductCount(orderQuantity, promotionStockQuantity);
    }

    public int getNotApplicableProductCount(int orderQuantity) {
        return orderQuantity - promotion.getPromotionAppliedQuantity(promotionStockQuantity);
    }

    public void decreaseStockQuantityByType(OrderProductType type, int quantity) {
        if (type.equals(NOT_APPLIED)) {
            defaultStockQuantity -= quantity;
            return;
        }
        if (type.equals(CAN_RECEIVE) || type.equals(CANNOT_RECEIVE)) {
            promotionStockQuantity -= quantity;
            return;
        }

        decreaseTotalQuantity(quantity);
    }

    public long calculatePrice(int quantity) {
        return (long) quantity * price;
    }

    private void decreaseTotalQuantity(int quantity) {
        int diff = quantity - promotionStockQuantity;
        promotionStockQuantity = 0;
        defaultStockQuantity -= diff;
    }
}
