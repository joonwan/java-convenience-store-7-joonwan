package store.domain;

public enum Answer {
    Y("Y"),
    N("N");

    private final String content;

    Answer(String content) {
        this.content = content;
    }

    public static Answer getAnswerType(String answer) {
        for (Answer answerType : values()) {
            if (answerType.equalWithContent(answer)) {
                return answerType;
            }
        }
        throw new IllegalArgumentException("Y 또는 N 만 입력할 수 있습니다. 다시 입력해 주세요.");
    }

    private boolean equalWithContent(String answer) {
        return content.equals(answer);
    }
}
