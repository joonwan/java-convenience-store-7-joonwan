package store.domain;

import static store.constants.DiscountConst.MAX_MEMBERSHIP_DISCOUNT_PRICE;
import static store.constants.DiscountConst.MEMBERSHIP_PROMOTION_DISCOUNT_RATE;

import java.util.LinkedHashMap;
import java.util.Map;

public class BillPaper {

    private boolean membershipDiscountApplied;
    private final Map<Product, Integer> totalOrderProducts = new LinkedHashMap<>();
    private final Map<Product, Integer> additionalReceiveProducts = new LinkedHashMap<>();

    public Map<Product, Integer> getTotalOrderProducts() {
        return new LinkedHashMap<>(totalOrderProducts);
    }

    public Map<Product, Integer> getAdditionalReceiveProducts() {
        return new LinkedHashMap<>(additionalReceiveProducts);
    }

    public void updateOrder(Product product, int orderQuantity, int additionalReceiveCount) {
        totalOrderProducts.put(product, orderQuantity);

        if (additionalReceiveCount != 0) {
            additionalReceiveProducts.put(product, additionalReceiveCount);
        }
    }

    public long getTotalOrderPrice() {
        long totalOrderPrice = 0;

        for (Product product : totalOrderProducts.keySet()) {
            int price = product.getPrice();
            int quantity = totalOrderProducts.get(product);
            totalOrderPrice += (long) price * quantity;
        }

        return totalOrderPrice;
    }

    public long getPromotionDiscountPrice() {
        long promotionDiscountPrice = 0;
        for (Product product : additionalReceiveProducts.keySet()) {
            int price = product.getPrice();
            int quantity = additionalReceiveProducts.get(product);
            promotionDiscountPrice += (long) price * quantity;
        }
        return promotionDiscountPrice;
    }

    public long getAfterDiscountPrice() {
        long totalOrderPrice = getTotalOrderPrice();
        long promotionDiscountPrice = getPromotionDiscountPrice();
        long membershipDiscountPrice = getMembershipDiscountPrice();

        long totalDiscountPrice = promotionDiscountPrice + membershipDiscountPrice;
        return totalOrderPrice - totalDiscountPrice;
    }

    public void applyMembershipDiscount() {
        membershipDiscountApplied = true;
    }

    public long getMembershipDiscountPrice() {
        if (!membershipDiscountApplied) {
            return 0;
        }
        double rawMembershipDiscountPrice = getNotPromotionAppliedTotalPrice() * MEMBERSHIP_PROMOTION_DISCOUNT_RATE;

        long membershipDiscountPrice = Double.valueOf(rawMembershipDiscountPrice).longValue();
        if (membershipDiscountPrice > MAX_MEMBERSHIP_DISCOUNT_PRICE) {
            membershipDiscountPrice = MAX_MEMBERSHIP_DISCOUNT_PRICE;
        }
        return membershipDiscountPrice;
    }

    public int getTotalOrderCount() {
        return totalOrderProducts.size();
    }

    private double getNotPromotionAppliedTotalPrice() {
        double sum = 0;
        for (Product product : totalOrderProducts.keySet()) {
            if (!isPromotionAppliedOrderProduct(product)) {
                int quantity = totalOrderProducts.get(product);
                long price = (long) product.getPrice() * quantity;
                sum += price;
            }
        }
        return sum;
    }

    private boolean isPromotionAppliedOrderProduct(Product product) {
        return additionalReceiveProducts.containsKey(product);
    }

}
