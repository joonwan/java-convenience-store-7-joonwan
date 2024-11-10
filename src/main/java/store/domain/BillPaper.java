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

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("==============W 편의점================\n");
        sb.append(String.format("%-15s%-7s%-12s\n", "상품명", "수량", "금액"));
        addTotalOrderProducts(sb);
        sb.append("=============증\t정===============\n");
        addAdditionalReceiveProducts(sb);
        sb.append("====================================\n");
        sb.append(String.format("%-10s\t%-5d\t%,d\n", "총구매액", getTotalOrderCount(), totalOrderPrice));
        sb.append(String.format("%-20s\t-%-,5d\n", "행사할인", promotionDiscountPrice));
        sb.append(String.format("%-20s\t-%-,5d\n", "멤버십할인", membershipDiscountPrice));
        sb.append(String.format("%-20s\t%-,5d\n", "내실돈", totalOrderPrice - (promotionDiscountPrice + membershipDiscountPrice)));
        return sb.toString();
    }

    private void addTotalOrderProducts(StringBuilder sb) {
        for (Product product : totalOrderProducts.keySet()) {
            String productName = product.getName();
            int quantity = totalOrderProducts.get(product);
            long price = product.calculatePrice(quantity);

            String format = String.format("%-15s%-7d%-12d\n", productName, quantity, price);
            sb.append(format);
        }
    }

    private void addAdditionalReceiveProducts(StringBuilder sb) {
        for (Product product : additionalReceiveProducts.keySet()) {
            String productName = product.getName();
            int quantity = additionalReceiveProducts.get(product);
            if (quantity != 0) {
                String format = String.format("%-10s\t%-5d\n", productName, quantity);
                sb.append(format);
            }
        }
    }

    private long getTotalOrderCount() {
        int total = 0;
        for (Product product : totalOrderProducts.keySet()) {
            total += totalOrderProducts.get(product);
        }
        return total;
    }
}
