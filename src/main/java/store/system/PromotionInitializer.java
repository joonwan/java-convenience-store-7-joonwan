package store.system;

import java.util.List;
import store.file.FileContentReader;
import store.repository.PromotionRepository;
import store.util.PromotionFileParser;

public class PromotionInitializer {

    private final long COLUMN_INFO_LINE_NUMBER = 1;
    private final PromotionRepository promotionRepository;
    private final String promotionFilePath;

    public PromotionInitializer(PromotionRepository promotionRepository, String promotionFilePath) {
        this.promotionRepository = promotionRepository;
        this.promotionFilePath = promotionFilePath;
    }

    public void initializePromotion() {
        FileContentReader fileContentReader = new FileContentReader(promotionFilePath);
        List<String> contents = fileContentReader.readContents();
        contents.stream()
                .skip(COLUMN_INFO_LINE_NUMBER)
                .map(PromotionFileParser::parseToPromotion)
                .forEach(promotionRepository::save);
    }
}
