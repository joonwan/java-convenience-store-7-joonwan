package store.domain;

import static store.constants.DiscountConst.MAX_MEMBERSHIP_DISCOUNT_PRICE;
import static store.constants.DiscountConst.MEMBERSHIP_PROMOTION_DISCOUNT_RATE;

import java.util.LinkedHashMap;
import java.util.Map;

public class BillPaper {

    private long totalOrderPrice;
    private long promotionDiscountPrice;
    private long membershipDiscountPrice;
    private Map<Product, Integer> totalOrderProducts = new LinkedHashMap<>();
    private Map<Product, Integer> additionalReceiveProducts = new LinkedHashMap<>();

    public void updateOrder(Product product, int orderQuantity, int additionalReceiveCount) {
        totalOrderPrice += (long) product.getPrice() * orderQuantity;
        promotionDiscountPrice += (long) product.getPrice() * additionalReceiveCount;

        totalOrderProducts.put(product, orderQuantity);
        additionalReceiveProducts.put(product, additionalReceiveCount);
    }

    public long getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public long getPromotionDiscountPrice() {
        return promotionDiscountPrice;
    }

    public long getMembershipDiscountPrice() {
        return membershipDiscountPrice;
    }

    public Map<Product, Integer> getTotalOrderProducts() {
        return new LinkedHashMap<>(totalOrderProducts);
    }

    public Map<Product, Integer> getAdditionalReceiveProducts() {
        return new LinkedHashMap<>(additionalReceiveProducts);
    }

    public void applyMembershipDiscount() {
        double sum = getNotPromotionAppliedTotalPrice();
        membershipDiscountPrice = Double.valueOf(sum * MEMBERSHIP_PROMOTION_DISCOUNT_RATE).longValue();
        if (membershipDiscountPrice > MAX_MEMBERSHIP_DISCOUNT_PRICE) {
            membershipDiscountPrice = MAX_MEMBERSHIP_DISCOUNT_PRICE;
        }
    }

    public int getTotalOrderCount() {
        int total = 0;
        for (Product product : totalOrderProducts.keySet()) {
            total += totalOrderProducts.get(product);
        }
        return total;
    }

    private double getNotPromotionAppliedTotalPrice() {
        double sum = 0;
        for (Product product : totalOrderProducts.keySet()) {
            if (isPromotionAppliedOrderProduct(product)) {
                continue;
            }
            int quantity = totalOrderProducts.get(product);
            long price = (long) product.getPrice() * quantity;
            sum += price;
        }
        return sum;
    }

    private boolean isPromotionAppliedOrderProduct(Product product) {
        return additionalReceiveProducts.get(product) != 0;
    }

}
