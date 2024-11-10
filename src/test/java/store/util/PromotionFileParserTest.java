package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Promotion;
import store.util.parser.PromotionFileParser;

class PromotionFileParserTest {

    @DisplayName("프로모션 파일의 형식이 올바를 경우 해당 내용을 기반으로 Promotion 의 인스턴스를 생성해야 한다.")
    @Test
    void parseToPromotion() {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 11, 30, 23, 59, 59);
        Promotion expectedPromotion = new Promotion("반짝할인", 1, 1, startDateTime, endDateTime);
        String rawPromotion = "반짝할인,1,1,2024-11-01,2024-11-30";

        Promotion promotion = PromotionFileParser.parseToPromotion(rawPromotion);
        assertThat(promotion.getName()).isEqualTo(expectedPromotion.getName());
    }

    @DisplayName("프로모션내용은 이름, 구매수량, 증정 수량, 시작날짜, 종료 날짜 로 총 5가지여야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"반짝할인,1,1,2024-11-01,2024-11-30,이상한 데이터", "반짝할인,1,2024-11-01,2024-11-30"})
    void inputInvalidContentSize(String rawPromotion) {

        assertThatThrownBy(() -> PromotionFileParser.parseToPromotion(rawPromotion))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로모션 데이터 형식은 {프로모션 이름},{구매수량},{증정수량},{시작날짜},{종료날짜} 여야 합니다.");
    }

    @DisplayName("프로모션의 구매 수량과 증정 수량은 정수형 데이터만 가능하다.")
    @ParameterizedTest
    @ValueSource(strings = {"반짝할인,문자열,문자열,2024-11-01,2024-11-30", "반짝할인,2147483648,10,2024-11-01,2024-11-30"})
    void inputInvalidQuantity(String rawPromotion) {

        assertThatThrownBy(() -> PromotionFileParser.parseToPromotion(rawPromotion))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 정수 만 가능합니다.");
    }

    @DisplayName("프로모션의 시작 날짜와 종료 날짜의 형식은 yyyy-MM-dd 이다.")
    @ParameterizedTest
    @ValueSource(strings = {"반짝할인,1,2,20241101,2024-11-30", "반짝할인,1,2,2024-11-01,2411030", "반짝할인,1,2,문자열,문자열"})
    void inputInvalidLocalTimeFormat(String rawPromotion) {

        assertThatThrownBy(() -> PromotionFileParser.parseToPromotion(rawPromotion))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜 입력 형식은 yyyy-MM-dd 입니다.");
    }
}
