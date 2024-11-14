package store.service;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;
import store.dto.StockStatus;
import store.repository.ProductRepository;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<StockStatus> getAllProductsStockStatus() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(Product::getStockStatus)
                .collect(Collectors.toList());
    }
}
