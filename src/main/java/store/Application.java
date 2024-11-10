package store;

import store.controller.ConvenienceStoreController;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.OrderService;
import store.service.ProductService;
import store.system.ResourceManager;

public class Application {

    public static void main(String[] args) {
        PromotionRepository promotionRepository = new PromotionRepository();
        ProductRepository productRepository = new ProductRepository();

        loadFile(promotionRepository, productRepository);

        ConvenienceStoreController convenienceStoreController = new ConvenienceStoreController(
                new ProductService(productRepository),
                new OrderService(productRepository));

        convenienceStoreController.run();
    }

    private static void loadFile(PromotionRepository promotionRepository, ProductRepository productRepository) {

        ResourceManager resourceManager = new ResourceManager(promotionRepository, productRepository);
        resourceManager.loadFile();

    }
}
