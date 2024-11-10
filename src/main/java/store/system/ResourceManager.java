package store.system;

import static store.constants.FilePathConst.*;

import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class ResourceManager {

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public ResourceManager(PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    public void loadFile() {
        loadFileToPromotionRepository();
        loadFileToProductRepository();
    }

    private void loadFileToPromotionRepository() {
        PromotionInitializer promotionInitializer = new PromotionInitializer(promotionRepository, PROMOTION_FILE_PATH);
        promotionInitializer.initializePromotion();
    }

    private void loadFileToProductRepository() {
        ProductInitializer productInitializer = new ProductInitializer(productRepository, promotionRepository,
                PRODUCT_FILE_PATH);

        productInitializer.initializeProduct();
    }

}
