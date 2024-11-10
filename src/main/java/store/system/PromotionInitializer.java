package store.system;

import static store.constants.FileInitializeConst.COLUMN_INFO_LINE_NUMBER;

import java.util.List;
import store.file.FileContentReader;
import store.repository.PromotionRepository;
import store.util.parser.PromotionFileParser;

public class PromotionInitializer {

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
                .forEach(promotion -> promotionRepository.save(promotion.getName(), promotion));
    }
}
