package store;

import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.system.ProductInitializer;
import store.system.PromotionInitializer;

public class Application {

    private static final String PROMOTION_FILE_PATH = "src/test/resources/promotions.md";
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    public static void main(String[] args) {

        PromotionRepository promotionRepository = new PromotionRepository();
        PromotionInitializer promotionInitializer = new PromotionInitializer(promotionRepository, PROMOTION_FILE_PATH);

        promotionInitializer.initializePromotion();

        ProductRepository productRepository = new ProductRepository();
        ProductInitializer productInitializer = new ProductInitializer(productRepository, promotionRepository,
                PRODUCT_FILE_PATH);

        productInitializer.initializeProduct();

    }
}
