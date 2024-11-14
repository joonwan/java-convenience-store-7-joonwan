package store.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.repository.PromotionRepository;

class PromotionInitializerTest {

    private final PromotionRepository promotionRepository = new PromotionRepository();

    @AfterEach
    void clear() {
        promotionRepository.clear();
    }

    @DisplayName("파일을 읽으면 컬럼 정보를 제외한 행 만큼의 객체가 repository 에 저장되어 있어야한다.")
    @Test
    void initPromotionRepository() {
        //given
        String promotionFilePath = "src/test/resources/promotions.md";
        PromotionInitializer promotionInitializer = new PromotionInitializer(promotionRepository, promotionFilePath);

        //when
        promotionInitializer.initializePromotion();

        //then
        assertThat(promotionRepository.size()).isEqualTo(3);
    }

    @DisplayName("존재하지 않는 파일경로가 들어올 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"notExistFilePath", "/src/src/src"})
    void invalidFilePath(String promotionFilePath) {

        PromotionInitializer promotionInitializer = new PromotionInitializer(promotionRepository, promotionFilePath);

        assertThatThrownBy(() -> promotionInitializer.initializePromotion())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로 또는 파일이 존재하지 않습니다.");

    }
}