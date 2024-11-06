package store.domain;

public class Product {

    private String name;
    private int price;
    private int promotionQuantity;
    private int defaultQuantity;
    private Promotion promotion;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public void increasePromotionQuantity(int amount) {
        this.promotionQuantity += amount;
    }

    public void increaseDefaultQuantity(int amount) {
        this.defaultQuantity += amount;
    }

    public void applyPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void endPromotion() {
        this.promotion = null;
    }
}
