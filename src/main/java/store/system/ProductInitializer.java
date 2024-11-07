package store.system;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.Promotion;
import store.file.FileContentReader;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.util.ProductFileParser;

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
        List<String> contents = getFileContents();
        List<String> skipContents = skipColumnInfoLine(contents);

        validateEachContentsSize(skipContents);

        saveToRepository(skipContents);
    }

    private List<String> getFileContents() {
        FileContentReader fileContentReader = new FileContentReader(productFilePath);
        return fileContentReader.readContents();
    }

    private static List<String> skipColumnInfoLine(List<String> contents) {
        return contents.stream()
                .skip(1)
                .collect(Collectors.toList());
    }

    public void validateEachContentsSize(List<String> contents) {
        contents.stream()
                .map(content -> content.split(","))
                .forEach(content -> validateSize(content));
    }

    private void validateSize(String[] productionComponent) {
        if (productionComponent.length != 4) {
            throw new IllegalArgumentException();
        }
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
        return promotionName.equals("null");
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
            Product findProduct = productRepository.findByName(productName);
            int promotionQuantity = ProductFileParser.getQuantity(content);
            findProduct.increasePromotionQuantity(promotionQuantity);
            return;
        }
        Product product = ProductFileParser.parseToPromotionProduct(content);
        String promotionName = ProductFileParser.getPromotionName(content);
        Promotion promotion = promotionRepository.findByPromotionName(promotionName);
        product.applyPromotion(promotion);
        productRepository.save(productName, product);
    }

}
