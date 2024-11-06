package store;

import store.repository.PromotionRepository;
import store.system.PromotionInitializer;

public class Application {

    private static final String PROMOTION_FILE_PATH = "src/test/resources/promotions.md";

    public static void main(String[] args) {

        PromotionRepository promotionRepository = new PromotionRepository();
        PromotionInitializer promotionInitializer = new PromotionInitializer(promotionRepository, PROMOTION_FILE_PATH);

        promotionInitializer.initializePromotion();
    }
}
