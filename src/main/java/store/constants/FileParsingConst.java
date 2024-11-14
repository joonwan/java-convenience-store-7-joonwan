package store.constants;

public final class FileParsingConst {

    public static final int PRODUCT_NAME_INDEX = 0;
    public static final int PRODUCT_PRICE_INDEX = 1;
    public static final int PRODUCT_QUANTITY_INDEX = 2;
    public static final int PRODUCT_FILE_PROMOTION_NAME_INDEX = 3;

    public static final int PROMOTION_FILE_PROMOTION_NAME_INDEX = 0;
    public static final int PROMOTION_BUY_INDEX = 1;
    public static final int PROMOTION_GET_INDEX = 2;
    public static final int PROMOTION_START_DATE_INDEX = 3;
    public static final int PROMOTION_END_DATE_INDEX = 4;
    public static final int PROMOTION_CONTENT_SIZE = 5;

    public static final String REGEX = ",";
    public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

    public static final int START_HOUR = 0;
    public static final int START_MINUTE = 0;
    public static final int START_SECOND = 0;

    public static final int FINISH_HOUR = 23;
    public static final int FINISH_MINUTE = 59;
    public static final int FINISH_SECOND = 59;

    private FileParsingConst() {
    }
}
