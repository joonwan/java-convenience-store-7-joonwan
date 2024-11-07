package store.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import store.domain.Product;

public class ProductRepository {

    private final Map<String, Product> store = new LinkedHashMap<>();

    public void save(String name, Product product) {
        validateNotDuplicatedKey(name);
        store.put(name, product);
    }

    public Product findByName(String name) {
        validateNotNull(name);

        Product product = store.get(name);
        if (product == null)
            throw new IllegalArgumentException("해당 이름을 가진 상품이 존재하지 않습니다.");

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
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
    }

    private void validateNotNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름으로 상품을 찾을 때 null 을 전달할 수 없습니다.");
        }
    }
}
