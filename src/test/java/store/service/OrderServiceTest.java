package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Order;
import store.domain.Product;
import store.repository.ProductRepository;

class OrderServiceTest {

    private final ProductRepository productRepository = new ProductRepository();
    private final OrderService orderService = new OrderService(productRepository);

    @BeforeEach
    void initProductRepository() {
        Product coke = new Product("콜라", 1000, 10, 10);
        Product cider = new Product("사이다", 1000, 10, 10);

        productRepository.save("콜라", coke);
        productRepository.save("사이다", cider);
    }

    @AfterEach
    void clear() {
        productRepository.clear();
    }

    @DisplayName("정상적인 사용자 상품 정보가 들어올 경우 주문상품 목록을 반환해야 한다.")
    @Test
    void createOrder() {
        //given
        String items = "[콜라- 2], [사이다- 1]";

        //when
        Order order  = orderService.createOrder(items);

        //then
        assertThat(order.size()).isEqualTo(2);
    }


    @DisplayName("주문 수량에 숫자가 아닌 다른 문자열이 들어올 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라-문자열]", "[콜라-둘], [사이다- 삼]", "[콜라-2],[사이다-문자열]"})
    void invalidProductQuantity(String items) {

        assertThatThrownBy(() -> orderService.createOrder(items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량은 정수만 입력할 수 있습니다.");

    }
}
