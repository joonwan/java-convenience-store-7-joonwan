package store.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import store.domain.BillPaper;
import store.domain.Product;
import store.dto.StockStatus;

public class OutputView {

    public static void printWelcomeMessage() {
        System.out.println("\n안녕하세요. W편의점입니다.");
    }

    public static void printStockStatuses(List<StockStatus> stockStatuses) {
        printStockStatusMessage();
        stockStatuses.stream()
                .forEach(stockStatus -> printStockStatus(stockStatus));
    }

    public static void printError(IllegalArgumentException exception) {
        System.out.println("[ERROR] " + exception.getMessage());
    }

    private static void printStockStatusMessage() {
        System.out.println("현재 보유하고 있는 상품입니다.\n");
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
            System.out.printf("- %s %,d원 %,d개 %s\n", productName, price, promotionStock, promotionName);
            return;
        }
        System.out.printf("- %s %,d원 재고 없음 %s\n", productName, price, promotionName);
    }

    private static void printDefaultStockQuantity(StockStatus stockStatus) {
        String productName = stockStatus.getProductName();
        int price = stockStatus.getPrice();
        int defaultStockQuantity = stockStatus.getDefaultStockQuantity();

        if (defaultStockQuantity > 0) {
            System.out.printf("- %s %,d원 %,d개\n", productName, price, defaultStockQuantity);
            return;
        }
        System.out.printf("- %s %,d원 재고 없음\n", productName, price);
    }

    public static void printBillPaper(BillPaper billPaper) {
//        printTotalOrderProducts(billPaper);
//        printAdditionalReceiveProducts(billPaper);
//        printTotalPrice(billPaper);
        System.out.println(billPaper);
    }

//    private static void printTotalOrderProducts(BillPaper billPaper) {
//        System.out.println("==============W 편의점================");
//        System.out.printf("%-16s%-7s%-12s%n","상품명","수량","금액");
//        Map<Product, Integer> totalOrderProducts = billPaper.getTotalOrderProducts();
//        for (Product product : totalOrderProducts.keySet()) {
//            String productName = product.getName();
//            int quantity = totalOrderProducts.get(product);
//            long price = product.calculatePrice(quantity);
//            System.out.printf("%-16s%,-7d%-,12d%n",productName, quantity, price);
//        }
//    }
//
//    private static void printAdditionalReceiveProducts(BillPaper billPaper) {
//        System.out.printf("=============증\t정===============");
//        Map<Product, Integer> additionalReceiveProducts = billPaper.getAdditionalReceiveProducts();
//        for (Product product : additionalReceiveProducts.keySet()) {
//            String productName = product.getName();
//            int quantity = additionalReceiveProducts.get(product);
//            if (quantity == 0) {
//                continue;
//            }
//            System.out.printf("%-16s%,-7d%n",productName, quantity);
//        }
//    }


}
