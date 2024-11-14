package store.util.parser;

import static store.constants.FileParsingConst.PRODUCT_FILE_PROMOTION_NAME_INDEX;
import static store.constants.FileParsingConst.PRODUCT_NAME_INDEX;
import static store.constants.FileParsingConst.PRODUCT_PRICE_INDEX;
import static store.constants.FileParsingConst.PRODUCT_QUANTITY_INDEX;
import static store.constants.FileParsingConst.REGEX;
import static store.errormessage.FileParsingErrorMessage.INVALID_PRICE_TYPE_ERROR_MESSAGE;
import static store.errormessage.FileParsingErrorMessage.INVALID_QUANTITY_TYPE_ERROR_MESSAGE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;

public class ProductFileParser {

    public static Product parseToDefaultProduct(String rawProduct) {
        List<String> productContents = Arrays.stream(rawProduct.split(REGEX))
                .map(String::trim)
                .collect(Collectors.toList());
        return createDefaultProduct(productContents);
    }

    public static Product parseToPromotionProduct(String rawProduct) {
        List<String> productContents = Arrays.stream(rawProduct.split(REGEX))
                .map(String::trim)
                .collect(Collectors.toList());

        return createPromotionProduct(productContents);
    }

    public static String getPromotionName(String rawProduct) {
        String[] productComponents = rawProduct.split(REGEX);
        return productComponents[PRODUCT_FILE_PROMOTION_NAME_INDEX].trim();
    }

    public static int getQuantity(String rawProduct) {
        String[] productComponents = rawProduct.split(REGEX);
        return parseToQuantity(productComponents[PRODUCT_QUANTITY_INDEX].trim());
    }

    public static String getProductName(String rawProduct) {
        String[] productComponents = rawProduct.split(REGEX);
        return productComponents[PRODUCT_NAME_INDEX].trim();
    }

    private static Product createDefaultProduct(List<String> productComponents) {
        String name = productComponents.get(PRODUCT_NAME_INDEX);
        int price = parseToPrice(productComponents.get(PRODUCT_PRICE_INDEX));
        int defaultQuantity = parseToQuantity(productComponents.get(PRODUCT_QUANTITY_INDEX));

        return new Product(name, price, 0, defaultQuantity);
    }

    private static Product createPromotionProduct(List<String> productComponents) {
        String name = productComponents.get(PRODUCT_NAME_INDEX);
        int price = parseToPrice(productComponents.get(PRODUCT_PRICE_INDEX));
        int promotionQuantity = parseToQuantity(productComponents.get(PRODUCT_QUANTITY_INDEX));

        return new Product(name, price, promotionQuantity, 0);
    }

    private static int parseToQuantity(String defaultQuantity) {
        try {
            return Integer.parseInt(defaultQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_QUANTITY_TYPE_ERROR_MESSAGE.getContent());
        }
    }

    private static int parseToPrice(String price) {
        try {
            return Integer.parseInt(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PRICE_TYPE_ERROR_MESSAGE.getContent());
        }
    }
}
