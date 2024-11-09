package store.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderProductTest {

    private Product product = new Product("name", 100, 7, 10);

    @DisplayName("주문 수량은 상품의 재고 수량을 초과할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {18, 19, 20, 21, 22})
    void invalidOrderQuantity(int orderQuantity) {

        assertThatThrownBy(() -> new OrderProduct(product, orderQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량은 총 재고수량을 초과할 수 없습니다.");


    }

    @DisplayName("주문 수량은 양수만 가능하다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void notPositiveOrderQuantity(int orderQuantity) {

        assertThatThrownBy(() -> new OrderProduct(product, orderQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량 입력시 양수만 입력할 수 있습니다.");

    }

}