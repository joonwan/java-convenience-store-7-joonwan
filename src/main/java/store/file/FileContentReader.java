package store.file;

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

    public List<String> readContents (){
        try {
            List<String> contents = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = bf.readLine()) != null) {
                contents.add(line);
            }
            return contents;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("해당 경로 또는 파일이 존재하지 않습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateNotNull(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("파일 경로 입력시 null 값을 입력할수 없습니다.");
        }
    }
}
