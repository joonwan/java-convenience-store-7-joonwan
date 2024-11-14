package store.viewmessage;

public final class RequestMessage {

    public static final String READ_ITEM_REQUEST_MESSAGE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public static final String ADDITIONAL_PRODUCT_REQUEST_MESSAGE = "\n현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String PARTIAL_PROMOTION_CONFIRM_REQUEST_MESSAGE = "\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    public static final String MEMBERSHIP_DISCOUNT_REQUEST_MESSAGE = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    public static final String CONTINUE_PROGRAM_REQUEST_MESSAGE = "\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    private RequestMessage() {
    }
}
