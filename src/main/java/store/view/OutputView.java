package store.view;

import static store.viewmessage.OutputViewMessage.ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT;
import static store.viewmessage.OutputViewMessage.ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT;
import static store.viewmessage.OutputViewMessage.ERROR_PREFIX;
import static store.viewmessage.OutputViewMessage.NOT_ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT;
import static store.viewmessage.OutputViewMessage.NOT_ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT;
import static store.viewmessage.OutputViewMessage.STOCK_STATUS_MESSAGE;
import static store.viewmessage.OutputViewMessage.WELCOME_MESSAGE;

import java.util.List;
import java.util.Map;
import store.domain.BillPaper;
import store.domain.Product;
import store.dto.StockStatus;

public class OutputView {

    public static void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public static void printStockStatuses(List<StockStatus> stockStatuses) {
        printStockStatusMessage();
        stockStatuses.stream()
                .forEach(stockStatus -> printStockStatus(stockStatus));
    }

    public static void printError(IllegalArgumentException exception) {
        System.out.println(ERROR_PREFIX + exception.getMessage());
    }

    public static void printBillPaper(BillPaper billPaper) {
        printTotalOrderProducts(billPaper.getTotalOrderProducts());
        printAdditionalReceiveProducts(billPaper.getAdditionalReceiveProducts());
        printPrices(billPaper);
    }

    private static void printStockStatusMessage() {
        System.out.println(STOCK_STATUS_MESSAGE);
    }

    private static void printStockStatus(StockStatus stockStatus) {
        if (hasPromotion(stockStatus)) {
            printPromotionStockQuantity(stockStatus);
        }
        printDefaultStockQuantity(stockStatus);
    }

    private static boolean hasPromotion(StockStatus stockStatus) {
        return !stockStatus.getPromotionName().isBlank();
    }

    private static void printPromotionStockQuantity(StockStatus stockStatus) {
        String productName = stockStatus.getProductName();
        int price = stockStatus.getPrice();
        int promotionStock = stockStatus.getPromotionStock();
        String promotionName = stockStatus.getPromotionName();

        if (promotionStock > 0) {
            System.out.printf(ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT, productName, price, promotionStock,
                    promotionName);
            return;
        }
        System.out.printf(NOT_ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT, productName, price, promotionName);
    }

    private static void printDefaultStockQuantity(StockStatus stockStatus) {
        String productName = stockStatus.getProductName();
        int price = stockStatus.getPrice();
        int defaultStockQuantity = stockStatus.getDefaultStockQuantity();

        if (defaultStockQuantity > 0) {
            System.out.printf(ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT, productName, price, defaultStockQuantity);
            return;
        }
        System.out.printf(NOT_ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT, productName, price);
    }

    private static void printTotalOrderProducts(Map<Product, Integer> totalOrderProducts) {
        System.out.println("==============W 편의점================");
        System.out.printf("%-8s%11s%-10s%-7s\n", "상품명", " ", "수량", "금액");

        for (Product product : totalOrderProducts.keySet()) {
            String productName = product.getName();
            int quantity = totalOrderProducts.get(product);
            long price = product.calculatePrice(quantity);
            System.out.printf("%-8s%11s%,-9d%-7d\n", productName, " ", quantity, price);
        }
    }

    private static void printAdditionalReceiveProducts(Map<Product, Integer> additionalReceiveProducts) {

        System.out.printf("%-19s%" + "16s", "=============증", "정===============\n");
        for (Product product : additionalReceiveProducts.keySet()) {
            String productName = product.getName();
            int quantity = additionalReceiveProducts.get(product);
            if (quantity == 0) {
                continue;
            }
            System.out.printf("%-19s%,-16d\n", productName, quantity);
        }
    }

    private static void printPrices(BillPaper billPaper) {
        System.out.println("====================================");
        long totalOrderPrice = billPaper.getTotalOrderPrice();
        int totalOrderCount = billPaper.getTotalOrderCount();
        long promotionDiscountPrice = billPaper.getPromotionDiscountPrice();
        long membershipDiscountPrice = billPaper.getMembershipDiscountPrice();

        printTotalOrderPrice(totalOrderCount, totalOrderPrice);
        printPromotionDiscountPrice(promotionDiscountPrice);
        printMembershipDiscountPrice(membershipDiscountPrice);
        printAfterDiscountPrice(totalOrderPrice - (promotionDiscountPrice + membershipDiscountPrice));
    }

    private static void printTotalOrderPrice(int totalOrderCount, long totalOrderPrice) {
        System.out.printf("%-19s%-,9d%,d\n", "총구매액", totalOrderCount, totalOrderPrice);
    }

    private static void printPromotionDiscountPrice(long promotionDiscountPrice) {
        System.out.printf("%-19s%,16d\n", "행사할인", -promotionDiscountPrice);
    }

    private static void printMembershipDiscountPrice(long membershipDiscountPrice) {
        System.out.printf("%-19s%,16d\n", "멤버십 할인", -membershipDiscountPrice);
    }

    private static void printAfterDiscountPrice(long afterDiscountPrice) {
        System.out.printf("%-19s%,16d\n", "내실돈", afterDiscountPrice);
    }

}
