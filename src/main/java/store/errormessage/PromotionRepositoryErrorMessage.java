package store.errormessage;

public enum PromotionRepositoryErrorMessage {

    NOT_FOUND_PROMOTION_ERROR_MESSAGE("해당 이름을 가진 프로모션이 존재하지 않습니다."),
    FIND_BY_NULL_ERROR_MESSAGE("프로모션을 이름으로 검색할 때 null 을 입력할 수 없습니다.");

    private final String content;

    PromotionRepositoryErrorMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
