package store.util.validator;

public class OrderInputValidator {

    public static void validateItemFormat(String items) {
        validateNotEmptyString(items);

        for (String item : items.split(",")) {
            String trimmedItem = item.trim();
            validateSquareBrackets(trimmedItem);
            validateContainSeparator(trimmedItem);
            validateSeparatorOnce(trimmedItem);
        }
    }

    private static void validateNotEmptyString(String items) {
        if (items.isBlank()) {
            throw new IllegalArgumentException("주문을 입력할 때 빈 문자열을 입력할 수 없습니다.");
        }
    }

    private static void validateSquareBrackets(String item) {
        if (!isSurroundedBySquareBrackets(item)) {
            throw new IllegalArgumentException("주문 상품과 수량은 [ ] 사이에 있어야 합니다.");
        }
    }

    private static boolean isSurroundedBySquareBrackets(String item) {
        return item.startsWith("[") && item.endsWith("]");
    }

    private static void validateContainSeparator(String item) {
        if (!item.contains("-")) {
            throw new IllegalArgumentException("주문상품과 수량을 - 로 구분해서 입력해야 합니다.");
        }
    }

    private static void validateSeparatorOnce(String item) {
        int separatorCount = 0;
        for (int i = 0; i < item.length(); i++) {
            if (item.charAt(i) == '-') {
                separatorCount++;
            }
        }
        if (separatorCount != 1) {
            throw new IllegalArgumentException("주문 상품과 수량을 구분할 때 - 는 한번만 입력할 수 있습니다.");
        }
    }
}
