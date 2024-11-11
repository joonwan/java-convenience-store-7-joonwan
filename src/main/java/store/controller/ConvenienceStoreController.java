package store.controller;

import static store.domain.UserAnswer.*;
import static store.domain.UserAnswer.getAnswerType;
import static store.domain.OrderProductType.*;

import java.util.ArrayList;
import java.util.List;
import store.domain.UserAnswer;
import store.domain.BillPaper;
import store.domain.Order;
import store.domain.OrderProductType;
import store.dto.OrderProductStatus;
import store.dto.StockStatus;
import store.service.OrderService;
import store.service.ProductService;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {

    private final ProductService productService;
    private final OrderService orderService;

    public ConvenienceStoreController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    public void run() {
        while (true) {
            printStockQuantity();
            Order order = getOrder();
            List<OrderProductStatus> finalOrderStatus = applyUserConfirm(order);
            attemptPrintBillPaper(finalOrderStatus);
            UserAnswer retryAnswer = getRetryAnswer();
            if (retryAnswer.equals(N)) {
                break;
            }
        }
    }

    private void printStockQuantity() {
        OutputView.printWelcomeMessage();
        printProductsStockStatus();
    }

    private void printProductsStockStatus() {
        List<StockStatus> productsStockStatus = productService.getProductsStockStatus();
        OutputView.printStockStatuses(productsStockStatus);
    }

    private Order getOrder() {
        while (true) {
            try {
                String items = InputView.readItem();
                return orderService.createOrder(items);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e);
            }
        }
    }

    private List<OrderProductStatus> applyUserConfirm(Order order) {
        List<OrderProductStatus> confirmedOrderProductStatues = new ArrayList<>();
        for (OrderProductStatus orderProductStatus : order.getOrderProductStatuses()) {
            confirmToUser(confirmedOrderProductStatues, orderProductStatus);
        }
        return confirmedOrderProductStatues;
    }

    private void confirmToUser(List<OrderProductStatus> confirmedStatuses, OrderProductStatus orderProductStatus) {
        OrderProductType type = orderProductStatus.getOrderProductType();
        if (type.equals(NOT_APPLIED) || type.equals(CANNOT_RECEIVE)) {
            confirmedStatuses.add(orderProductStatus);
            return;
        }
        if (type.equals(CAN_RECEIVE)) {
            confirmAdditionalReceive(confirmedStatuses, orderProductStatus);
            return;
        }
        confirmPartialPromotion(confirmedStatuses, orderProductStatus);
    }

    private void confirmAdditionalReceive(List<OrderProductStatus> statuses, OrderProductStatus status) {
        while (true) {
            try {
                updateAdditionalReceive(statuses, status);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e);
            }
        }
    }

    private static void updateAdditionalReceive(List<OrderProductStatus> statuses, OrderProductStatus status) {
        UserAnswer answer = getAnswerType(InputView.getAdditionalProductAnswer(status));
        if (answer.equals(Y)) {
            int orderQuantity = status.getOrderQuantity();
            int additionalReceiveCount = status.getAdditionalReceiveCount();
            increasePromotionProduct(status, orderQuantity, additionalReceiveCount);
        }
        statuses.add(status);
    }

    private static void increasePromotionProduct(OrderProductStatus status, int orderQuantity,
                                                 int additionalReceiveCount) {
        status.setOrderQuantity(orderQuantity + 1);
        status.setAdditionalReceiveCount(additionalReceiveCount + 1);
    }

    private void confirmPartialPromotion(List<OrderProductStatus> statuses, OrderProductStatus status) {
        while (true) {
            try {
                updatePartialPromotion(statuses, status);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e);
            }
        }
    }

    private void updatePartialPromotion(List<OrderProductStatus> statuses, OrderProductStatus status) {
        UserAnswer answer = getAnswerType(InputView.getPartialPromotionAnswer(status));
        if (answer.equals(Y)) {
            statuses.add(status);
        }
    }

    private void attemptPrintBillPaper(List<OrderProductStatus> confirmedOrderProductStatuses) {
        if (!confirmedOrderProductStatuses.isEmpty()) {
            BillPaper billPaper = orderService.getBillPaper(confirmedOrderProductStatuses);
            attemptMembershipDiscount(billPaper);
            printBillPaper(billPaper);
        }
    }

    private void attemptMembershipDiscount(BillPaper billPaper) {
        while (true) {
            try {
                confirmMembershipDiscount(billPaper);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e);
            }
        }
    }

    private void confirmMembershipDiscount(BillPaper billPaper) {
        UserAnswer answer = getAnswerType(InputView.getMembershipDiscountAnswer());
        if (answer.equals(Y)) {
            billPaper.applyMembershipDiscount();
        }
    }

    private void printBillPaper(BillPaper billPaper) {
        OutputView.printBillPaper(billPaper);
    }

    private static UserAnswer getRetryAnswer() {
        while (true) {
            try {
                return getAnswerType(InputView.getContinueAnswer());
            } catch (IllegalArgumentException e) {
                OutputView.printError(e);
            }
        }
    }
}
