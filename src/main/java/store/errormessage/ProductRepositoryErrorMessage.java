package store.errormessage;

public final class ProductRepositoryErrorMessage {

    public static final String NOT_FOUND_PRODUCT_ERROR_MESSAGE = "해당 이름을 가진 상품이 존재하지 않습니다. 다시 입해 주세요.";
    public static final String DUPLICATED_PRODUCT_NAME_ERROR_MESSAGE = "이미 존재하는 상품입니다.";
    public static final String FIND_BY_NULL_ERROR_MESSAGE = "이름으로 상품을 찾을 때 null 을 전달할 수 없습니다.";

    private ProductRepositoryErrorMessage() {
    }
}
