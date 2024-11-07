package store.system;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class ProductInitializerTest {

    private final ProductRepository productRepository = new ProductRepository();
    private final PromotionRepository promotionRepository = new PromotionRepository();

    @BeforeEach
    void initPromotionRepository() {
        String promotionFilePath = "src/main/resources/promotions.md";
        PromotionInitializer promotionInitializer = new PromotionInitializer(promotionRepository, promotionFilePath);
        promotionInitializer.initializePromotion();
    }

    @DisplayName("파일이 정상일 경우 저장소에는 파일에 존재하는 상품만큼 상품 객체가 생성되어야 한다.")
    @Test
    void initializeProductRepository() {
        //given
        String productFilePath = "src/test/resources/products.md";
        ProductInitializer productInitializer = new ProductInitializer(productRepository, promotionRepository,
                productFilePath);

        //when
        productInitializer.initializeProduct();

        //then
        assertThat(productRepository.size()).isEqualTo(11);
    }
}