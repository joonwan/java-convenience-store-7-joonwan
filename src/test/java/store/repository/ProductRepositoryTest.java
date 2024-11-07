package store.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Product;

class ProductRepositoryTest {

    private final ProductRepository productRepository = new ProductRepository();

    @AfterEach
    void clear() {
        productRepository.clear();
    }

    @DisplayName("상품을 저장할 수 있어야 한다.")
    @Test
    void save() {
        Product coke = new Product("coke", 1000, 10);
        Product juice = new Product("juice", 1000, 10);

        productRepository.save("coke", coke);
        productRepository.save("juice", juice);

        assertThat(productRepository.size()).isEqualTo(2);
        assertThat(productRepository.containsName("coke")).isTrue();
        assertThat(productRepository.containsName("juice")).isTrue();
    }

    @DisplayName("저장소에 해당 이름을 가진 상품이 존재할 경우 해당 이름을 가진 상품을 반환해야 한다.")
    @Test
    void findByName() {
        //given
        Product coke = new Product("coke", 1000, 10);
        productRepository.save("coke", coke);

        //when
        Product findProduct = productRepository.findByName("coke");

        //then
        assertThat(findProduct).isEqualTo(coke);
    }

    @DisplayName("저장소에 해당 이름을 가진 상품이 없을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"없는이름1", "없는이름2"})
    void findByNotExistName(String name) {

        Product coke = new Product("coke", 1000, 10);
        Product juice = new Product("juice", 1000, 10);
        productRepository.save("coke", coke);
        productRepository.save("juice", juice);

        assertThatThrownBy(() -> productRepository.findByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이름을 가진 상품이 존재하지 않습니다.");
    }

    @DisplayName("이름으로 상품을 찾을 때 이름에 null 이 들어올 수 없다.")
    @ParameterizedTest
    @NullSource
    void findByNull(String name) {

        Product coke = new Product("coke", 1000, 10);
        Product juice = new Product("juice", 1000, 10);
        productRepository.save("coke", coke);
        productRepository.save("juice", juice);

        assertThatThrownBy(() -> productRepository.findByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름으로 상품을 찾을 때 null 을 전달할 수 없습니다.");
    }

}