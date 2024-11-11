package store.file;

import static store.errormessage.FileReadErrorMessage.NOT_FOUND_FILE_ERROR_MESSAGE;
import static store.errormessage.FileReadErrorMessage.NULL_PATH_ERROR_MESSAGE;
import static store.errormessage.FileReadErrorMessage.READ_FILE_ERROR_MESSAGE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileContentReader {

    private final String filePath;

    public FileContentReader(String filePath) {
        validateNotNull(filePath);
        this.filePath = filePath;
    }

    public List<String> readContents() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(filePath));
            return readFile(bf);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(NOT_FOUND_FILE_ERROR_MESSAGE.getContent());
        } catch (IOException e) {
            throw new IllegalArgumentException(READ_FILE_ERROR_MESSAGE.getContent());
        }
    }

    private void validateNotNull(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException(NULL_PATH_ERROR_MESSAGE.getContent());
        }
    }

    private static List<String> readFile(BufferedReader bf) throws IOException {
        List<String> contents = new ArrayList<>();
        String line = "";

        while ((line = bf.readLine()) != null) {
            contents.add(line);
        }

        return contents;
    }

}
