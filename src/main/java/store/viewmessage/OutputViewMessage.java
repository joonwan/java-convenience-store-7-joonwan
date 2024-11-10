package store.viewmessage;

public final class OutputViewMessage {

    public static final String WELCOME_MESSAGE = "\n안녕하세요. W편의점입니다.";
    public static final String ERROR_PREFIX = "[ERROR] ";
    public static final String STOCK_STATUS_MESSAGE = "현재 보유하고 있는 상품입니다.\n";

    public static final String ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT = "- %s %,d원 %,d개 %s\n";
    public static final String NOT_ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT = "- %s %,d원 재고 없음 %s\n";

    public static final String ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT = "- %s %,d원 %,d개\n";
    public static final String NOT_ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT = "- %s %,d원 재고 없음\n";

    private OutputViewMessage() {
    }
}
