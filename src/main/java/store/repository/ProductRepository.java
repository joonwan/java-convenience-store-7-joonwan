package store.repository;

import java.util.HashMap;
import java.util.Map;
import store.domain.Product;

public class ProductRepository {

    private static long id;
    private final Map<Long, Product> store = new HashMap<>();

    public void save(Product product) {
        store.put(++id, product);
    }

    public Product findByName(String name) {
        validateNotNull(name);

        for (Long id : store.keySet()) {
            Product product = store.get(id);
            if (product.hasName(name)) {
                return product;
            }
        }
        throw new IllegalArgumentException("해당 이름을 가진 상품이 존재하지 않습니다.");
    }

    public int size() {
        return store.size();
    }

    public boolean contains(Product product) {
        return store.containsValue(product);
    }

    public void clear() {
        store.clear();
    }

    private void validateNotNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름으로 상품을 찾을 때 null 을 전달할 수 없습니다.");
        }
    }
}
