package store.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class FileContentReaderTest {

    @DisplayName("파일 경로를 입력할 때 null 을 전달할 수 없다.")
    @ParameterizedTest
    @NullSource
    void inputNullFilePath(String filePath) {
        assertThatThrownBy(() -> new FileContentReader(filePath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일 경로 입력시 null 값을 입력할수 없습니다.");

    }

    @DisplayName("입력 받은 파일 경로에 파일이 경로에 없다면 IllegalArgumentException 을 발생 시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/not_exist_filename", "src/foo/file/foo"})
    void inputInvalidFilePath(String filePath) {

        FileContentReader fileContentReader = new FileContentReader(filePath);
        assertThatThrownBy(() -> fileContentReader.readContents())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로 또는 파일이 존재하지 않습니다.");

    }

    @DisplayName("입력 받은 파일 경로에 파일이 존재할 경우 해당 파일의 내용을 한줄씩 리스트에 담아 반환해야 한다.")
    @ParameterizedTest
    @CsvSource(value = {"src/test/resources/existfile.md, 10", "src/test/resources/existfile2.md, 20"})
    void inputValidFilePath(String filePath, int expected) {

        //given
        FileContentReader fileContentReader = new FileContentReader(filePath);

        //when
        List<String> contents = fileContentReader.readContents();

        //then
        assertThat(contents).hasSize(expected);
    }

}