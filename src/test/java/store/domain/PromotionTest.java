package store.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {

    private final LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
    private final LocalDateTime endDateTime = LocalDateTime.of(2024, 11, 30, 23, 59, 59);
    private final Promotion promotion = new Promotion("test", 1, 1, startDateTime, endDateTime);


    @DisplayName("날짜가 프로모션 기간에 속할 경우 프로모션 적용이 가능하다.")
    @Test
    void promotionAvailableDateTime() {
        //given

        LocalDateTime startThreshHold = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        LocalDateTime endThreshHold = LocalDateTime.of(2024, 11, 30, 23, 59, 59);

        Assertions.assertThat(promotion.isAvailableApplyPromotion(startThreshHold)).isTrue();
        Assertions.assertThat(promotion.isAvailableApplyPromotion(endThreshHold)).isTrue();
    }

    @DisplayName("날짜가 프로모션 기간에 속하지 않을 경우 프로모션 적용이 불가능 하다.")
    @Test
    void test() {

        LocalDateTime startThreshHold = LocalDateTime.of(2024, 10, 31, 23, 59, 59);
        LocalDateTime endThreshHold = LocalDateTime.of(2024, 12, 31, 0, 0, 0);

        Assertions.assertThat(promotion.isAvailableApplyPromotion(startThreshHold)).isFalse();
        Assertions.assertThat(promotion.isAvailableApplyPromotion(endThreshHold)).isFalse();
    }

}