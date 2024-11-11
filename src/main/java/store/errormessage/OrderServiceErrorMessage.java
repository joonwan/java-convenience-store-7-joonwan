package store.errormessage;

public enum OrderServiceErrorMessage {

    INVALID_ORDER_QUANTITY_TYPE_ERROR_MESSAGE("주문 수량은 정수만 입력할 수 있습니다. 다시 입력해 주세요.");

    private final String content;

    OrderServiceErrorMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
