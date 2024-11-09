package store.domain;

import java.time.LocalDateTime;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Promotion(String name, int buy, int get, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailableApplyPromotion(LocalDateTime orderDateTime) {
        return isAfterStartFromDateTime(orderDateTime) && isBeforeFromEndDateTime(orderDateTime);
    }

    private boolean isAfterStartFromDateTime(LocalDateTime orderDateTime) {
        return startDateTime.isEqual(orderDateTime) || startDateTime.isBefore(orderDateTime);
    }

    private boolean isBeforeFromEndDateTime(LocalDateTime orderDateTime) {
        return endDateTime.isEqual(orderDateTime) || endDateTime.isAfter(orderDateTime);
    }

    public boolean isPossibleGiveMoreProduct(int orderQuantity, int promotionStockQuantity) {
        return orderQuantity % (buy + get) == buy && get <= (promotionStockQuantity - orderQuantity);
    }

    public int getAdditionalGiftProductCount(int orderQuantity, int promotionStockQuantity) {
        if (orderQuantity < promotionStockQuantity) {
            return orderQuantity / (buy + get) * get;
        }
        return promotionStockQuantity / (buy + get) * get;
    }

    public int getPromotionAppliedQuantity(int promotionStockQuantity) {
        return promotionStockQuantity / (buy + get) * (buy + get);
    }
}
