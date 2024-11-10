package store.util.validator;

import static store.constants.OrderInputConstants.PREFIX;
import static store.constants.OrderInputConstants.REGEX;
import static store.constants.OrderInputConstants.SEPARATOR;
import static store.constants.OrderInputConstants.SUFFIX;
import static store.errormessage.OrderInputErrorMessage.EMPTY_STRING_ERROR_MESSAGE;
import static store.errormessage.OrderInputErrorMessage.INVALID_SEPARATOR_COUNT_ERROR_MESSAGE;
import static store.errormessage.OrderInputErrorMessage.INVALID_SEPARATOR_ERROR_MESSAGE;
import static store.errormessage.OrderInputErrorMessage.INVALID_SQUARE_BRACKETS_ERROR_MESSAGE;

public class OrderInputValidator {

    public static void validateItemFormat(String items) {
        validateNotEmptyString(items);

        for (String item : items.split(REGEX)) {
            String trimmedItem = item.trim();
            validateSquareBrackets(trimmedItem);
            validateContainSeparator(trimmedItem);
            validateSeparatorOnce(trimmedItem);
        }
    }

    private static void validateNotEmptyString(String items) {
        if (items.isBlank()) {
            throw new IllegalArgumentException(EMPTY_STRING_ERROR_MESSAGE);
        }
    }

    private static void validateSquareBrackets(String item) {
        if (!isSurroundedBySquareBrackets(item)) {
            throw new IllegalArgumentException(INVALID_SQUARE_BRACKETS_ERROR_MESSAGE);
        }
    }

    private static boolean isSurroundedBySquareBrackets(String item) {
        return item.startsWith(PREFIX) && item.endsWith(SUFFIX);
    }

    private static void validateContainSeparator(String item) {
        if (!item.contains(SEPARATOR)) {
            throw new IllegalArgumentException(INVALID_SEPARATOR_ERROR_MESSAGE);
        }
    }

    private static void validateSeparatorOnce(String item) {
        int separatorCount = 0;
        for (int i = 0; i < item.length(); i++) {
            if (item.charAt(i) == SEPARATOR.charAt(0)) {
                separatorCount++;
            }
        }
        if (separatorCount != 1) {
            throw new IllegalArgumentException(INVALID_SEPARATOR_COUNT_ERROR_MESSAGE);
        }
    }
}
