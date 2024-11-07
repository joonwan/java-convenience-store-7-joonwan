package store.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;

public class ProductFileParser {

    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_NAME_INDEX = 3;

    public static Product parseToDefaultProduct(String rawProduct) {
        List<String> productContents = Arrays.stream(rawProduct.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        return createDefaultProduct(productContents);
    }

    public static Product parseToPromotionProduct(String rawProduct) {
        List<String> productContents = Arrays.stream(rawProduct.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return createPromotionProduct(productContents);
    }

    public static String getPromotionName(String rawProduct) {
        String[] productComponents = rawProduct.split(",");
        return productComponents[PROMOTION_NAME_INDEX].trim();
    }

    public static int getQuantity(String rawProduct) {
        String[] productComponents = rawProduct.split(",");
        return parseToQuantity(productComponents[QUANTITY_INDEX].trim());
    }

    public static String getProductName(String rawProduct) {
        String[] productComponents = rawProduct.split(",");
        return productComponents[NAME_INDEX].trim();
    }

    private static Product createPromotionProduct(List<String> productComponents) {
        String name = productComponents.get(NAME_INDEX);
        int price = parseToPrice(productComponents.get(PRICE_INDEX));
        int promotionQuantity = parseToQuantity(productComponents.get(QUANTITY_INDEX));

        return new Product(name, price, promotionQuantity, 0);
    }

    private static Product createDefaultProduct(List<String> productComponents) {
        String name = productComponents.get(NAME_INDEX);
        int price = parseToPrice(productComponents.get(PRICE_INDEX));
        int defaultQuantity = parseToQuantity(productComponents.get(QUANTITY_INDEX));

        return new Product(name, price, 0, defaultQuantity);
    }

    private static int parseToPrice(String price) {
        try {
            return Integer.parseInt(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("가격은 숫자만 입력할 수 있습니다.");
        }
    }

    private static int parseToQuantity(String defaultQuantity) {
        try {
            return Integer.parseInt(defaultQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("재고 수량은 숫자만 입력할 수 있습니다.");
        }
    }
}
