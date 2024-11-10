package store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class BillPaper {

    private long totalOrderPrice;
    private long promotionDiscountPrice;
    private long membershipDiscountPrice;
    private Map<Product, Integer> totalOrderProducts = new LinkedHashMap<>();
    private Map<Product, Integer> additionalReceiveProducts = new LinkedHashMap<>();

    public void updateOrder(Product product, int orderQuantity, int additionalReceiveCount) {
        totalOrderPrice += product.calculatePrice(orderQuantity);
        promotionDiscountPrice += product.calculatePrice(additionalReceiveCount);

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
        double result = 0;
        for (Product product : totalOrderProducts.keySet()) {
            if (additionalReceiveProducts.get(product) != 0) {
                continue;
            }

            int quantity = totalOrderProducts.get(product);
            long price = product.calculatePrice(quantity);
            result += price * 0.3;
        }
        if (result > 8000) {
            membershipDiscountPrice = 8000;
            return;
        }
        membershipDiscountPrice = Double.valueOf(result).longValue();
    }


    public int getTotalOrderCount() {
        int total = 0;
        for (Product product : totalOrderProducts.keySet()) {
            total += totalOrderProducts.get(product);
        }
        return total;
    }
}
