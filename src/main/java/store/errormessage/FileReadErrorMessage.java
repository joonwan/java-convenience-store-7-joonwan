package store.errormessage;

public final class FileReadErrorMessage {

    public static final String NOT_FOUND_FILE_ERROR_MESSAGE = "해당 경로 또는 파일이 존재하지 않습니다.";
    public static final String NULL_PATH_ERROR_MESSAGE = "파일 경로 입력시 null 값을 입력할수 없습니다.";
    public static final String READ_FILE_ERROR_MESSAGE = "파일을 읽는 중 알수없는 오류가 발생했습니다. 다시 시도해 주세요;";

    private FileReadErrorMessage() {
    }
}
