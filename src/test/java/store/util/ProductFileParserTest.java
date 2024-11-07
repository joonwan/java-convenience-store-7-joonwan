package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Product;

class ProductFileParserTest {

    @DisplayName("정상적인 기본 상품의 정보가 들어올 경우 상품 객체를 반환해야 한다.")
    @Test
    void inputDefaultProduct() {
        //given
        Product expectedProduct = new Product("콜라", 1000, 1, 1);
        String productInfo = "콜라,1000,10,null";

        //when
        Product product = ProductFileParser.parseToDefaultProduct(productInfo);

        //then
        assertThat(product).isEqualTo(expectedProduct);
    }

    @DisplayName("정상적인 프로모션 진행 상품의 정보가 들어올 경우 상품 객체를 반환해야 한다.")
    @Test
    void inputPromotionProduct() {
        //given
        Product expectedProduct = new Product("콜라", 1000, 1, 1);
        String productInfo = "콜라,1000,10,탄산2+1";

        //when
        Product product = ProductFileParser.parseToPromotionProduct(productInfo);

        //then
        assertThat(product).isEqualTo(expectedProduct);
    }

    @DisplayName("정상적인 문자열이 들어올 경우 상품 이름을 반환해야 한다.")
    @Test
    void getProductName() {
        //given
        String productInfo = "콜라,1000,10,탄산2+1";

        //when
        String name = ProductFileParser.getProductName(productInfo);

        //then
        assertThat(name).isEqualTo("콜라");
    }

    @DisplayName("정상적인 문자열이 들어올 경우 상품 재고수를 반환해야 한다.")
    @Test
    void getQuantity() {
        //given
        String productInfo = "콜라,1000,10,탄산2+1";

        //when
        int quantity = ProductFileParser.getQuantity(productInfo);

        //then
        assertThat(quantity).isEqualTo(10);
    }

    @DisplayName("정상적인 문자열이 들어올 경우 상품의 프로모션 이름을 반환해야 한다.")
    @Test
    void getPromotionName() {
        //given
        String productInfo = "콜라,1000,10,탄산2+1";

        //when
        String promotionName = ProductFileParser.getPromotionName(productInfo);

        //then
        assertThat(promotionName).isEqualTo("탄산2+1");
    }

    @DisplayName("재고 수량으로 숫자가 아닌 문자열이 들어올 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라,1000,,탄산2+1", "콜라,1000, ,탄산2+1", "콜라,1000,asd,탄산2+1"})
    void invalidStockQuantity(String productInfo) {

        assertThatThrownBy(() -> ProductFileParser.getQuantity(productInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량은 숫자만 입력할 수 있습니다.");

    }

    @DisplayName("가격으로 숫자가 아닌 문자열이 들어올 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라,,10,탄산2+1", "콜라, ,10,탄산2+1", "콜라,asd,10,탄산2+1"})
    void invalidPrice(String productInfo) {
        assertThatThrownBy(() -> ProductFileParser.parseToDefaultProduct(productInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 숫자만 입력할 수 있습니다.");
    }
}