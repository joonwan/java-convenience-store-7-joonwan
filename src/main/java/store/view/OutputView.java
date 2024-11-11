package store.view;

import static store.viewmessage.OutputViewMessage.*;

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
        printGiftItems(billPaper.getAdditionalReceiveProducts());
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
        System.out.println(BILL_PAPER_START_LINE);
        System.out.printf(PRODUCT_COLUMN_INFO_FORMAT, PRODUCT_NAME, QUANTITY, PRICE);

        for (Product product : totalOrderProducts.keySet()) {
            String productName = product.getName();
            int quantity = totalOrderProducts.get(product);
            long price = (long) product.getPrice() * quantity;
            System.out.printf(PRODUCT_INFO_FORMAT, productName, quantity, price);
        }
    }

    private static void printGiftItems(Map<Product, Integer> additionalReceiveProducts) {
        printGiftItemTitle();

        for (Product product : additionalReceiveProducts.keySet()) {
            String productName = product.getName();
            int quantity = additionalReceiveProducts.get(product);
            System.out.printf(GIFT_ITEM_FORMAT, productName, quantity);
        }
    }

    private static void printGiftItemTitle() {
        System.out.printf(GIFT_ITEM_LINE_FORMAT, GIFT_ITEM_TITLE_PREFIX, GIFT_ITEM_TITLE_SUFFIX);
    }

    private static void printPrices(BillPaper billPaper) {
        System.out.println(PRICE_INFO_START_LINE);
        long totalOrderPrice = billPaper.getTotalOrderPrice();
        int totalOrderCount = billPaper.getTotalOrderCount();

        printTotalOrderPrice(totalOrderCount, totalOrderPrice);
        printPromotionDiscountPrice(billPaper.getPromotionDiscountPrice());
        printMembershipDiscountPrice(billPaper.getMembershipDiscountPrice());
        printAfterDiscountPrice(billPaper.getAfterDiscountPrice());
    }

    private static void printTotalOrderPrice(int totalOrderCount, long totalOrderPrice) {
        System.out.printf(TOTAL_PRICE_FORMAT, TOTAL_PRICE, totalOrderCount, totalOrderPrice);
    }

    private static void printPromotionDiscountPrice(long promotionDiscountPrice) {
        System.out.printf(PROMOTION_DISCOUNT_PRICE_FORMAT, PROMOTION_DISCOUNT, promotionDiscountPrice);
    }

    private static void printMembershipDiscountPrice(long membershipDiscountPrice) {
        System.out.printf(MEMBERSHIP_DISCOUNT_PRICE_FORMAT, MEMBERSHIP_DISCOUNT, membershipDiscountPrice);
    }

    private static void printAfterDiscountPrice(long afterDiscountPrice) {
        System.out.printf(RESULT_PRICE_FORMAT, RESULT_PRICE, afterDiscountPrice);
    }

}
