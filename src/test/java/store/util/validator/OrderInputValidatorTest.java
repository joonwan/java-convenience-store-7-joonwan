package store.util.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderInputValidatorTest {

    @DisplayName("빈 문자열이 들어올 경우 예외를 발생 시킨다.")
    @ParameterizedTest
    @EmptySource
    void inputEmptyString(String items) {

        assertThatThrownBy(() -> OrderInputValidator.validateItemFormat(items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문을 입력할 때 빈 문자열을 입력할 수 없습니다. 다시 입력해 주세요.");

    }

    @DisplayName("주문 상품과 수량이 [] 사이에 존재하지 않을경우 예외가 발생한다. ")
    @ParameterizedTest
    @ValueSource(strings = {"콜라-2]", "[콜라-2", "[콜라-2], 사이다-1]", "콜라-2"})
    void notSurroundedBySquareBrackets(String items) {

        assertThatThrownBy(() -> OrderInputValidator.validateItemFormat(items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 상품과 수량은 [ ] 사이에 있어야 합니다. 다시 입력해 주세요.");
    }

    @DisplayName("주문 상품과 수량이 - 로 구분되어 있지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라2]", "[콜라-2], [사이다3]", "[콜라]"})
    void notSeparatedByHyphen(String items) {

        assertThatThrownBy(() -> OrderInputValidator.validateItemFormat(items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문상품과 수량을 - 로 구분해서 입력해야 합니다. 다시 입력해 주세요.");

    }

    @DisplayName("- 는 한번만 입력할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라--2]", "[콜라-2], [사이다--3]"})
    void notHyphenOnce(String items) {

        assertThatThrownBy(() -> OrderInputValidator.validateItemFormat(items))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 상품과 수량을 구분할 때 - 는 한번만 입력할 수 있습니다. 다시 입력해 주세요.");

    }

}