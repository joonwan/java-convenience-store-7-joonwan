package store.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Promotion;

public class PromotionFileParser {

    private static final int NAME_INDEX = 0;
    private static final int BUY_INDEX = 1;
    private static final int GET_INDEX = 2;
    private static final int START_DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;
    private static final int PROMOTION_CONTENT_SIZE = 5;
    private static final String REGEX = ",";
    private static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

    public static Promotion parseToPromotion(String rawPromotion) {
        List<String> promotionContents = Arrays.stream(rawPromotion.split(REGEX))
                .map(String::trim)
                .collect(Collectors.toList());

        validateSize(promotionContents);
        return createPromotion(promotionContents);
    }

    private static void validateSize(final List<String> promotionContents) {
        if (promotionContents.size() != PROMOTION_CONTENT_SIZE) {
            throw new IllegalArgumentException("프로모션 데이터 형식은 {프로모션 이름},{구매수량},{증정수량},{시작날짜},{종료날짜} 여야 합니다.");
        }
    }

    private static Promotion createPromotion(final List<String> promotionContents) {
        String name = promotionContents.get(NAME_INDEX);
        int buy = parseToQuantity(promotionContents.get(BUY_INDEX));
        int get = parseToQuantity(promotionContents.get(GET_INDEX));
        LocalDateTime startDate = parseToStartDateTime(promotionContents.get(START_DATE_INDEX));
        LocalDateTime endDate = parseToEndDateTime(promotionContents.get(END_DATE_INDEX));

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private static int parseToQuantity(String rawQuantity) {
        try {
            return Integer.parseInt(rawQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("수량은 정수 만 가능합니다.");
        }
    }

    private static LocalDateTime parseToStartDateTime(String rawLocalDate) {
        try {
            LocalDate startDate = LocalDate.parse(rawLocalDate, DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
            return  startDate.atTime(0, 0, 0);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 입력 형식은 yyyy-MM-dd 입니다.");
        }
    }

    private static LocalDateTime parseToEndDateTime(String rawLocalDate) {
        try {
            LocalDate date = LocalDate.parse(rawLocalDate, DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
            return  date.atTime(23, 59, 59);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 입력 형식은 yyyy-MM-dd 입니다.");
        }
    }
}
