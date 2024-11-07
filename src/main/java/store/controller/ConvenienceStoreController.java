package store.controller;

import java.util.List;
import store.dto.StockStatus;
import store.service.ProductService;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ProductService productService;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, ProductService productService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.productService = productService;
    }

    public void run() {
        printStartMessage();
        getOrder();
    }

    private void printStartMessage() {
        outputView.printWelcomeMessage();
        printProductsStockStatus();
    }

    private void printProductsStockStatus() {
        List<StockStatus> productsStockStatus = productService.getProductsStockStatus();
        outputView.printStockStatuses(productsStockStatus);
    }

    private void getOrder() {
        while (true) {
            try {
            String items = inputView.readItem();
            } catch (IllegalArgumentException e) {

            }
        }

    }
}
