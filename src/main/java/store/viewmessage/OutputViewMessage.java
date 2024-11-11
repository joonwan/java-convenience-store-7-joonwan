package store.viewmessage;

public final class OutputViewMessage {

    public static final String WELCOME_MESSAGE = "\n안녕하세요. W편의점입니다.";
    public static final String ERROR_PREFIX = "[ERROR] ";
    public static final String STOCK_STATUS_MESSAGE = "현재 보유하고 있는 상품입니다.\n";

    public static final String BILL_PAPER_START_LINE = "\n==============W 편의점================";
    public static final String PRICE_INFO_START_LINE = "====================================";

    public static final String GIFT_ITEM_TITLE_PREFIX = "=============증";
    public static final String GIFT_ITEM_TITLE_SUFFIX = "정===============";
    public static final String TOTAL_PRICE = "총구매액";
    public static final String PROMOTION_DISCOUNT = "행사할인";
    public static final String MEMBERSHIP_DISCOUNT = "멤버십 할인";
    public static final String RESULT_PRICE = "내실돈";
    public static final String PRODUCT_NAME = "상품명";
    public static final String QUANTITY = "수량";
    public static final String PRICE = "금액";

    public static final String ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT = "- %s %,d원 %,d개 %s\n";
    public static final String NOT_ENOUGH_PROMOTION_STOCK_QUANTITY_FORMAT = "- %s %,d원 재고 없음 %s\n";

    public static final String ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT = "- %s %,d원 %,d개\n";
    public static final String NOT_ENOUGH_DEFAULT_STOCK_QUANTITY_FORMAT = "- %s %,d원 재고 없음\n";

    public static final String PRODUCT_COLUMN_INFO_FORMAT = "%-19s%-10s%-7s\n";
    public static final String PRODUCT_INFO_FORMAT = "%-19s%-,10d%-,7d\n";

    public static final String GIFT_ITEM_LINE_FORMAT = "%-19s%" + "16s\n";
    public static final String GIFT_ITEM_FORMAT = "%-19s%,-16d\n";
    public static final String TOTAL_PRICE_FORMAT = "%-19s%-,9d%,d\n";
    public static final String PROMOTION_DISCOUNT_PRICE_FORMAT = "%-28s-%,-7d\n";
    public static final String MEMBERSHIP_DISCOUNT_PRICE_FORMAT = "%-27s-%,-8d\n";
    public static final String RESULT_PRICE_FORMAT = "%-29s%,-6d\n";

    private OutputViewMessage() {
    }
}
