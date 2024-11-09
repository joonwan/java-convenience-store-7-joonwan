package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.domain.OrderProductType.*;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.dto.OrderProductStatus;

class OrderProductTest {

    private Product product = new Product("name", 100, 7, 10);
    private LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
    private LocalDateTime endDateTime = LocalDateTime.of(2024, 11, 30, 23, 59, 59);
    private Promotion promotion = new Promotion("name", 2, 1, startDateTime, endDateTime);

    @BeforeEach
    void registerPromotion() {
        product.registerPromotion(promotion);
    }

    @DisplayName("프로모션 기간이 아닐때 주문 수량은 기본 재고 수량을 초과할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {11, 12, 13, 14, 15})
    void invalidOrderQuantityAtNotPromotionDuration(int orderQuantity) {

        LocalDateTime notPromotionApplicableDateTime = LocalDateTime.of(2024, 12, 1, 0, 0, 0);

        assertThatThrownBy(() -> new OrderProduct(product, orderQuantity, notPromotionApplicableDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량은 총 재고수량을 초과할 수 없습니다.");

    }

    @DisplayName("프로모션 기간일 경우 주문 수량은 기본 재고 수량과 프로모션 재고 수량의 합을 을 초과할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {18, 19, 20, 21})
    void invalidOrderQuantityAtPromotionDuration(int orderQuantity) {

        LocalDateTime notPromotionApplicableDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);

        assertThatThrownBy(() -> new OrderProduct(product, orderQuantity, notPromotionApplicableDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량은 총 재고수량을 초과할 수 없습니다.");

    }

    @DisplayName("주문 수량은 양수만 가능하다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void notPositiveOrderQuantity(int orderQuantity) {

        assertThatThrownBy(() -> new OrderProduct(product, orderQuantity, DateTimes.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 수량 입력시 양수만 입력할 수 있습니다.");

    }

    @DisplayName("프로모션이 적용되며 프로모션 재고를 초과하여 일부 재고에 프로모션이 적용되지 않을 경우 주문상품의 타입은 PARTIAL_APPLIED 이다.")
    @ParameterizedTest
    @CsvSource(value = {"8,2,2", "9,2,3", "10,2,4", "11,2,5"})
    void partialPromotionType(int orderQuantity, int additionalGiftProductCount, int notApplicableProductCount) {

        LocalDateTime promotionApplicableDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        OrderProduct orderProduct = new OrderProduct(product, orderQuantity, promotionApplicableDateTime);
        OrderProductStatus orderProductStatus = orderProduct.getOrderProductStatus();

        assertThat(orderProductStatus.getOrderProductType()).isEqualTo(PARTIAL_APPLIED);
        assertThat(orderProductStatus.getAdditionalReceiveCount()).isEqualTo(additionalGiftProductCount);
        assertThat(orderProductStatus.getNotApplicableProductCount()).isEqualTo(notApplicableProductCount);
    }


    @DisplayName("주문 수량을 프로모션 재고 내에서 처리 가능하며 증정이 가능한 경우 주문 상품의 타입은 ADDITIONAL_PRODUCT 이다.")
    @ParameterizedTest
    @CsvSource(value = {"5,1,0", "2,0,0"})
    void additionalPromotion(int orderQuantity, int additionalGiftProductCount, int notApplicableProductCount) {

        LocalDateTime promotionApplicableDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        OrderProduct orderProduct = new OrderProduct(product, orderQuantity, promotionApplicableDateTime);

        OrderProductStatus orderProductStatus = orderProduct.getOrderProductStatus();

        assertThat(orderProductStatus.getOrderProductType()).isEqualTo(CAN_RECEIVE);
        assertThat(orderProductStatus.getAdditionalReceiveCount()).isEqualTo(additionalGiftProductCount);
        assertThat(orderProductStatus.getNotApplicableProductCount()).isEqualTo(notApplicableProductCount);
    }

    @DisplayName("프로모션이 존재하지 않는 주문 상품일 경우 해당 주문 상품의 타입은 NOT_APPLIED 이다.")
    @ParameterizedTest
    @CsvSource(value = {"10,0,0", "4,0,0"})
    void notPromotionProduct(int orderQuantity, int additionalGiftProductCount, int notApplicableProductCount) {
        product.clearPromotion();

        LocalDateTime promotionApplicableDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        OrderProduct orderProduct = new OrderProduct(product, orderQuantity, promotionApplicableDateTime);

        OrderProductStatus orderProductStatus = orderProduct.getOrderProductStatus();

        assertThat(orderProductStatus.getOrderProductType()).isEqualTo(NOT_APPLIED);
        assertThat(orderProductStatus.getAdditionalReceiveCount()).isEqualTo(additionalGiftProductCount);
        assertThat(orderProductStatus.getNotApplicableProductCount()).isEqualTo(notApplicableProductCount);
    }

    @DisplayName("프로모션이 적용되지 않는 시간 범위의 주문 상품일 경우 타입은 NOT_APPLIED 이다.")
    @ParameterizedTest
    @CsvSource(value = {"10,0,0", "4,0,0"})
    void notPromotionDateTime(int orderQuantity, int additionalGiftProductCount, int notApplicableProductCount) {

        LocalDateTime promotionNotApplicableDateTime = LocalDateTime.of(2024, 12, 1, 0, 0, 0);
        OrderProduct orderProduct = new OrderProduct(product, orderQuantity, promotionNotApplicableDateTime);
        OrderProductStatus orderProductStatus = orderProduct.getOrderProductStatus();

        assertThat(orderProductStatus.getOrderProductType()).isEqualTo(NOT_APPLIED);
        assertThat(orderProductStatus.getAdditionalReceiveCount()).isEqualTo(additionalGiftProductCount);
        assertThat(orderProductStatus.getNotApplicableProductCount()).isEqualTo(notApplicableProductCount);
    }

    @DisplayName("프로모션이 존재하며 프로모션 수량 내에서 주문 수량을 처리가능하지만 추가증정이 더이상 안될경우 타입은 DEFAULT 이다.")
    @ParameterizedTest
    @CsvSource(value = {"7,2,0", "6,2,0"})
    void notEnoughToAdditionalPromotion(int orderQuantity, int additionalGiftProductCount,
                                        int notApplicableProductCount) {

        LocalDateTime promotionApplicableDateTime = LocalDateTime.of(2024, 11, 1, 0, 0, 0);
        OrderProduct orderProduct = new OrderProduct(product, orderQuantity, promotionApplicableDateTime);
        OrderProductStatus orderProductStatus = orderProduct.getOrderProductStatus();

        assertThat(orderProductStatus.getOrderProductType()).isEqualTo(CANNOT_RECEIVE);
        assertThat(orderProductStatus.getAdditionalReceiveCount()).isEqualTo(additionalGiftProductCount);
        assertThat(orderProductStatus.getNotApplicableProductCount()).isEqualTo(notApplicableProductCount);
    }
}
