package store.errormessage;

public enum OrderInputErrorMessage {

    EMPTY_STRING_ERROR_MESSAGE("주문을 입력할 때 빈 문자열을 입력할 수 없습니다. 다시 입력해 주세요."),
    INVALID_SQUARE_BRACKETS_ERROR_MESSAGE("주문 상품과 수량은 [ ] 사이에 있어야 합니다. 다시 입력해 주세요."),
    INVALID_SEPARATOR_ERROR_MESSAGE("주문상품과 수량을 - 로 구분해서 입력해야 합니다. 다시 입력해 주세요."),
    INVALID_SEPARATOR_COUNT_ERROR_MESSAGE("주문 상품과 수량을 구분할 때 - 는 한번만 입력할 수 있습니다. 다시 입력해 주세요.");

    private final String content;

    OrderInputErrorMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
