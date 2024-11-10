package store.util.parser;

import static store.constants.FileParsingConst.FINISH_HOUR;
import static store.constants.FileParsingConst.FINISH_MINUTE;
import static store.constants.FileParsingConst.FINISH_SECOND;
import static store.constants.FileParsingConst.LOCAL_DATE_FORMAT;
import static store.constants.FileParsingConst.PROMOTION_BUY_INDEX;
import static store.constants.FileParsingConst.PROMOTION_CONTENT_SIZE;
import static store.constants.FileParsingConst.PROMOTION_END_DATE_INDEX;
import static store.constants.FileParsingConst.PROMOTION_FILE_PROMOTION_NAME_INDEX;
import static store.constants.FileParsingConst.PROMOTION_GET_INDEX;
import static store.constants.FileParsingConst.PROMOTION_START_DATE_INDEX;
import static store.constants.FileParsingConst.REGEX;
import static store.constants.FileParsingConst.START_HOUR;
import static store.constants.FileParsingConst.START_MINUTE;
import static store.constants.FileParsingConst.START_SECOND;
import static store.errormessage.FileParsingErrorMessage.INVALID_DATE_FORMAT_ERROR_MESSAGE;
import static store.errormessage.FileParsingErrorMessage.INVALID_PROMOTION_FILE_FORMAT_ERROR_MESSAGE;
import static store.errormessage.FileParsingErrorMessage.INVALID_PROMOTION_QUANTITY_ERROR_MESSAGE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Promotion;

public class PromotionFileParser {

    public static Promotion parseToPromotion(String rawPromotion) {
        List<String> promotionContents = Arrays.stream(rawPromotion.split(REGEX))
                .map(String::trim)
                .collect(Collectors.toList());

        validateFormat(promotionContents);
        return createPromotion(promotionContents);
    }

    private static void validateFormat(final List<String> promotionContents) {
        if (promotionContents.size() != PROMOTION_CONTENT_SIZE) {
            throw new IllegalArgumentException(INVALID_PROMOTION_FILE_FORMAT_ERROR_MESSAGE);
        }
    }

    private static Promotion createPromotion(final List<String> promotionContents) {
        String name = promotionContents.get(PROMOTION_FILE_PROMOTION_NAME_INDEX);
        int buy = parseToQuantity(promotionContents.get(PROMOTION_BUY_INDEX));
        int get = parseToQuantity(promotionContents.get(PROMOTION_GET_INDEX));
        LocalDateTime startDate = parseToStartDateTime(promotionContents.get(PROMOTION_START_DATE_INDEX));
        LocalDateTime endDate = parseToEndDateTime(promotionContents.get(PROMOTION_END_DATE_INDEX));

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private static int parseToQuantity(String rawQuantity) {
        try {
            return Integer.parseInt(rawQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PROMOTION_QUANTITY_ERROR_MESSAGE);
        }
    }

    private static LocalDateTime parseToStartDateTime(String rawLocalDate) {
        try {
            LocalDate startDate = LocalDate.parse(rawLocalDate, DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
            return startDate.atTime(getStartTime());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }
    }

    private static LocalTime getStartTime() {
        return LocalTime.of(START_HOUR, START_MINUTE, START_SECOND);
    }

    private static LocalDateTime parseToEndDateTime(String rawLocalDate) {
        try {
            LocalDate date = LocalDate.parse(rawLocalDate, DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
            return date.atTime(getEndTime());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }
    }

    private static LocalTime getEndTime() {
        return LocalTime.of(FINISH_HOUR, FINISH_MINUTE, FINISH_SECOND);
    }
}
