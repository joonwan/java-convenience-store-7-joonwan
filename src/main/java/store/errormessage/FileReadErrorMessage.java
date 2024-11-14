package store.errormessage;

public enum FileReadErrorMessage {

    NOT_FOUND_FILE_ERROR_MESSAGE("해당 경로 또는 파일이 존재하지 않습니다."),
    NULL_PATH_ERROR_MESSAGE("파일 경로 입력시 null 값을 입력할수 없습니다."),
    READ_FILE_ERROR_MESSAGE("파일을 읽는 중 알수없는 오류가 발생했습니다. 다시 시도해 주세요.");

    private final String content;

    FileReadErrorMessage(java.lang.String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
