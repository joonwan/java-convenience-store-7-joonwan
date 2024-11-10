package store.domain;

public enum UserAnswer {
    Y("Y"),
    N("N");

    private static final String INVALID_ANSWER_ERROR_MESSAGE = "Y 또는 N 만 입력할 수 있습니다. 다시 입력해 주세요.";
    private final String content;

    UserAnswer(String content) {
        this.content = content;
    }

    public static UserAnswer getAnswerType(String answer) {
        for (UserAnswer answerType : values()) {
            if (answerType.equalWithContent(answer)) {
                return answerType;
            }
        }
        throw new IllegalArgumentException(INVALID_ANSWER_ERROR_MESSAGE);
    }

    private boolean equalWithContent(String answer) {
        return content.equals(answer);
    }
}
