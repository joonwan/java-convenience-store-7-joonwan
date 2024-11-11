package store.errormessage;

public enum OrderErrorMessage {

    NEGATIVE_ORDER_QUANTITY_ERROR_MESSAGE("주문 수량 입력시 양수만 입력할 수 있습니다. 다시 입력해 주세요."),
    NOT_ENOUGH_STOCK_QUANTITY_ERROR_MESSAGE("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private final String content;

    OrderErrorMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
