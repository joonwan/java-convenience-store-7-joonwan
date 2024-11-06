package store.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Promotion;

class PromotionRepositoryTest {

    private final PromotionRepository promotionRepository = new PromotionRepository();
    private final LocalDate startDate = LocalDate.of(2024, 11, 2);
    private final LocalDate endDate = LocalDate.of(2024, 11, 10);

    @AfterEach
    void clearRepository() {
        promotionRepository.clear();
    }

    @DisplayName("프로모션을 저장할 수 있어야 한다.")
    @Test
    void save() {
        Promotion carbonatedDrinkTwoPlusOne = new Promotion("탄산2+1", 1, 2, startDate, endDate);
        Promotion mdRecommendedProduction = new Promotion("MD추천상품", 1, 2, startDate, endDate);

        promotionRepository.save(carbonatedDrinkTwoPlusOne);
        promotionRepository.save(mdRecommendedProduction);

        assertThat(promotionRepository.contains(carbonatedDrinkTwoPlusOne)).isTrue();
        assertThat(promotionRepository.contains(mdRecommendedProduction)).isTrue();
        assertThat(promotionRepository.size()).isEqualTo(2);
    }

    @DisplayName("저장소에 해당 이름을 가진 프로모션이 있을 경우 해당 프로모션을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"탄산2+1", "MD추천상품"})
    void findByName(String name) {
        Promotion promotion = new Promotion(name, 1, 2, startDate, endDate);
        promotionRepository.save(promotion);

        Promotion findPromotion = promotionRepository.findByPromotionName(name);

        assertThat(findPromotion).isEqualTo(promotion);
    }

    @DisplayName("만약 프로모션 저장소에 들어있지 않은 이름이 들어올 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"없는이름1", "없는이름2", "없는이름3"})
    void notFoundName(String name) {
        Promotion carbonatedDrinkTwoPlusOne = new Promotion("탄산2+1", 1, 2, startDate, endDate);
        Promotion mdRecommendedProduction = new Promotion("MD추천상품", 1, 2, startDate, endDate);

        promotionRepository.save(carbonatedDrinkTwoPlusOne);
        promotionRepository.save(mdRecommendedProduction);

        assertThatThrownBy(() -> promotionRepository.findByPromotionName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이름을 가진 프로모션이 존재하지 않습니다.");
    }

    @DisplayName("이름으로 프로모션을 찾을 때 null 이 들어올 수 없다.")
    @ParameterizedTest
    @NullSource
    void inputNullFindByName (String name) {
        Promotion carbonatedDrinkTwoPlusOne = new Promotion("탄산2+1", 1, 2, startDate, endDate);
        Promotion mdRecommendedProduction = new Promotion("MD추천상품", 1, 2, startDate, endDate);

        promotionRepository.save(carbonatedDrinkTwoPlusOne);
        promotionRepository.save(mdRecommendedProduction);

        assertThatThrownBy(() -> promotionRepository.findByPromotionName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로모션을 이름으로 검색할 때 null 을 입력할 수 없습니다.");
    }

}