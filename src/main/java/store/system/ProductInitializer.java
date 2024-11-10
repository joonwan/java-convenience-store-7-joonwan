package store.system;

import static store.constants.FileInitializeConst.COLUMN_INFO_LINE_NUMBER;
import static store.constants.FileInitializeConst.NULL;
import static store.constants.FileInitializeConst.REGEX;
import static store.constants.FileInitializeConst.VALID_PRODUCT_COLUMN_NUMBER;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.Promotion;
import store.file.FileContentReader;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.util.parser.ProductFileParser;

public class ProductInitializer {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final String productFilePath;

    public ProductInitializer(ProductRepository productRepository, PromotionRepository promotionRepository,
                              String productFilePath) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.productFilePath = productFilePath;
    }

    public void initializeProduct() {
        List<String> fileContents = getFileContents();
        List<String> skipContents = skipColumnInfoLine(fileContents);

        validateEachContentsSize(skipContents);
        saveToRepository(skipContents);
    }

    private List<String> getFileContents() {
        FileContentReader fileContentReader = new FileContentReader(productFilePath);
        return fileContentReader.readContents();
    }

    private static List<String> skipColumnInfoLine(List<String> contents) {
        return contents.stream()
                .skip(COLUMN_INFO_LINE_NUMBER)
                .collect(Collectors.toList());
    }

    private void validateEachContentsSize(List<String> contents) {
        contents.stream()
                .map(content -> content.split(REGEX))
                .forEach(content -> validateSize(content));
    }

    private void validateSize(String[] productionComponent) {
        if (!isValidContentsSize(productionComponent)) {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isValidContentsSize(String[] productionComponent) {
        return productionComponent.length == VALID_PRODUCT_COLUMN_NUMBER;
    }

    private void saveToRepository(List<String> contents) {
        for (String content : contents) {
            if (isDefaultProductContent(content)) {
                persistDefaultProduct(content);
                continue;
            }
            persistPromotionProduct(content);
        }
    }

    private boolean isDefaultProductContent(String content) {
        String promotionName = ProductFileParser.getPromotionName(content);
        return promotionName.equals(NULL);
    }

    private void persistDefaultProduct(String content) {
        String productName = ProductFileParser.getProductName(content);
        if (productRepository.containsName(productName)) {
            Product findProduct = productRepository.findByName(productName);
            int defaultQuantity = ProductFileParser.getQuantity(content);
            findProduct.increaseDefaultQuantity(defaultQuantity);
            return;
        }

        Product product = ProductFileParser.parseToDefaultProduct(content);
        productRepository.save(productName, product);
    }

    private void persistPromotionProduct(String content) {
        String productName = ProductFileParser.getProductName(content);
        if (productRepository.containsName(productName)) {
            updateProductStockQuantity(content, productName);
            return;
        }

        Product product = ProductFileParser.parseToPromotionProduct(content);
        Promotion promotion = findPromotionByName(content);
        product.registerPromotion(promotion);
        productRepository.save(productName, product);
    }

    private void updateProductStockQuantity(String productName, String content) {
        Product findProduct = productRepository.findByName(productName);
        int promotionQuantity = ProductFileParser.getQuantity(content);
        findProduct.increasePromotionQuantity(promotionQuantity);
    }

    private Promotion findPromotionByName(String content) {
        String promotionName = ProductFileParser.getPromotionName(content);
        return promotionRepository.findByPromotionName(promotionName);
    }

}
