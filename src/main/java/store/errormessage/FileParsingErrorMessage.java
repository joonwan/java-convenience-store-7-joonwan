package store.errormessage;

public enum FileParsingErrorMessage {

    INVALID_PRICE_TYPE_ERROR_MESSAGE("가격은 숫자만 파일에 입력할 수 있습니다."),
    INVALID_QUANTITY_TYPE_ERROR_MESSAGE("재고 수량은 숫자만 입력할 수 있습니다."),
    INVALID_PROMOTION_FILE_FORMAT_ERROR_MESSAGE("프로모션 데이터 형식은 {프로모션 이름},{구매수량},{증정수량},{시작날짜},{종료날짜} 여야 합니다."),
    INVALID_PROMOTION_QUANTITY_ERROR_MESSAGE("수량은 정수 만 가능합니다."),
    INVALID_DATE_FORMAT_ERROR_MESSAGE("날짜 입력 형식은 yyyy-MM-dd 입니다.");

    private final String content;

    FileParsingErrorMessage(java.lang.String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
