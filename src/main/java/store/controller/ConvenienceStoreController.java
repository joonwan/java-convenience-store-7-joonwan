package store.controller;

import static store.domain.Answer.*;
import static store.domain.Answer.getAnswerType;
import static store.domain.OrderProductType.*;

import java.util.ArrayList;
import java.util.List;
import store.domain.Answer;
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
            printStartMessage();
            Order order = createOrder();
            List<OrderProductStatus> confirmedOrderProductStatuses = applyUserConfirm(order);
            BillPaper billPaper = orderService.getBillPaper(confirmedOrderProductStatuses);
            attemptMembershipDiscount(billPaper);
            printBillPaper(billPaper);
            Answer retryAnswer = getRetryAnswer();
            if (retryAnswer.equals(N)) {
                break;
            }
        }
    }

    private static Answer getRetryAnswer() {
        while (true) {
            try {
                return getAnswerType(InputView.getContinueAnswer());
            } catch (IllegalArgumentException e) {
                OutputView.printError(e);
            }
        }
    }

    private void printStartMessage() {
        OutputView.printWelcomeMessage();
        printProductsStockStatus();
    }

    private void printProductsStockStatus() {
        List<StockStatus> productsStockStatus = productService.getProductsStockStatus();
        OutputView.printStockStatuses(productsStockStatus);
    }

    private Order createOrder() {
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
            checkConfirm(confirmedOrderProductStatues, orderProductStatus);
        }
        return confirmedOrderProductStatues;
    }

    private void checkConfirm(List<OrderProductStatus> confirmedStatuses, OrderProductStatus orderProductStatus) {
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
        Answer answer = getAnswerType(InputView.confirmReceivePromotion(status));
        if (answer.equals(Y)) {
            int orderQuantity = status.getOrderQuantity();
            int additionalReceiveCount = status.getAdditionalReceiveCount();
            status.setOrderQuantity(orderQuantity + 1);
            status.setAdditionalReceiveCount(additionalReceiveCount + 1);
        }
        statuses.add(status);
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
        Answer answer = getAnswerType(InputView.confirmPartialPromotion(status));
        if (answer.equals(Y)) {
            statuses.add(status);
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
        Answer answer = getAnswerType(InputView.confirmMembershipDiscount());
        if (answer.equals(Y)) {
            billPaper.applyMembershipDiscount();
        }
    }

    private void printBillPaper(BillPaper billPaper) {
        OutputView.printBillPaper(billPaper);
    }
}
