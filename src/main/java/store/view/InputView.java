package store.view;

import static store.viewmessage.RequestMessage.*;

import camp.nextstep.edu.missionutils.Console;
import store.dto.OrderProductStatus;

public class InputView {

    public static String readItem() {
        System.out.println(READ_ITEM_REQUEST_MESSAGE);
        return Console.readLine();
    }

    public static String getAdditionalProductAnswer(OrderProductStatus orderProductStatus) {
        String name = orderProductStatus.getProductName();
        String message = String.format(ADDITIONAL_PRODUCT_REQUEST_MESSAGE, name);
        System.out.println(message);

        return Console.readLine();
    }

    public static String getPartialPromotionAnswer(OrderProductStatus orderProductStatus) {
        String name = orderProductStatus.getProductName();
        int count = orderProductStatus.getNotApplicableProductCount();

        String message = String.format(PARTIAL_PROMOTION_CONFIRM_REQUEST_MESSAGE, name, count);
        System.out.println(message);
        return Console.readLine();
    }

    public static String getMembershipDiscountAnswer() {
        System.out.println(MEMBERSHIP_DISCOUNT_REQUEST_MESSAGE);
        return Console.readLine();
    }

    public static String getContinueAnswer() {
        System.out.println(CONTINUE_PROGRAM_REQUEST_MESSAGE);
        return Console.readLine();
    }
}
