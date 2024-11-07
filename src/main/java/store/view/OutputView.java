package store.view;

import java.util.List;
import store.dto.StockStatus;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printStockStatuses(List<StockStatus> stockStatuses) {
        printStockStatusMessage();
        stockStatuses.stream()
                .forEach(stockStatus -> printStockStatus(stockStatus));
    }

    private void printStockStatusMessage() {
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    private void printStockStatus(StockStatus stockStatus) {
        if (hasPromotion(stockStatus)) {
            printPromotionStockQuantity(stockStatus);
        }
        printDefaultStockQuantity(stockStatus);
    }

    private boolean hasPromotion(StockStatus stockStatus) {
        return stockStatus.getPromotionName() != null;
    }

    private void printPromotionStockQuantity(StockStatus stockStatus) {
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

    private void printDefaultStockQuantity(StockStatus stockStatus) {
        String productName = stockStatus.getProductName();
        int price = stockStatus.getPrice();
        int defaultStockQuantity = stockStatus.getDefaultStockQuantity();

        if (defaultStockQuantity > 0) {
            System.out.printf("- %s %,d원 %,d개\n", productName, price, defaultStockQuantity);
            return;
        }
        System.out.printf("- %s %,d원 재고 없음\n", productName, price);
    }
}
