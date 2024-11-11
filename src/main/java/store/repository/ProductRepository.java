package store.repository;

import static store.errormessage.ProductRepositoryErrorMessage.DUPLICATED_PRODUCT_NAME_ERROR_MESSAGE;
import static store.errormessage.ProductRepositoryErrorMessage.FIND_BY_NULL_ERROR_MESSAGE;
import static store.errormessage.ProductRepositoryErrorMessage.NOT_FOUND_PRODUCT_ERROR_MESSAGE;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.Product;

public class ProductRepository {

    private final Map<String, Product> store = new LinkedHashMap<>();

    public void save(String name, Product product) {
        validateNotDuplicatedKey(name);
        store.put(name, product);
    }

    public List<Product> findAll() {
        return store.keySet().stream()
                .map(store::get)
                .collect(Collectors.toList());
    }


    public Product findByName(String name) {
        validateNotNull(name);

        Product product = store.get(name);
        if (product == null) {
            throw new IllegalArgumentException(NOT_FOUND_PRODUCT_ERROR_MESSAGE.getContent());
        }

        return product;
    }

    public int size() {
        return store.size();
    }

    public boolean containsName(String name) {
        return store.containsKey(name);
    }

    public void clear() {
        store.clear();
    }

    private void validateNotDuplicatedKey(String name) {
        if (store.containsKey(name)) {
            throw new IllegalArgumentException(DUPLICATED_PRODUCT_NAME_ERROR_MESSAGE.getContent());
        }
    }

    private void validateNotNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException(FIND_BY_NULL_ERROR_MESSAGE.getContent());
        }
    }
}
