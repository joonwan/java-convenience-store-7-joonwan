package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderProductTest {

    @DisplayName("주문 수량은 총 재고 수량을 초과할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {21, 22, 23, 24})
    void invalidOrderQuantity(int orderQuantity) {

        Product product = new Product("coke", 1000, 10, 10);

        Assertions.assertThatThrownBy(() -> new OrderProduct(product, orderQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량은 총 재고수량을 초과할 수 없습니다.");

    }

    @DisplayName("주문 수량은 음수가 들어올 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, 0})
    void notPositiveOrderQuantity(int orderQuantity) {

        Product product = new Product("coke", 1000, 10, 10);

        Assertions.assertThatThrownBy(() -> new OrderProduct(product, orderQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량 입력시 양수만 입력할 수 있습니다.");

    }
}
