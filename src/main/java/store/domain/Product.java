package store.domain;

import java.util.Objects;

public class Product {

    private String name;
    private int price;
    private int promotionQuantity;
    private int defaultQuantity;
    private Promotion promotion;

    public Product(String name, int price, int promotionQuantity, int defaultQuantity) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.defaultQuantity = defaultQuantity;
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
