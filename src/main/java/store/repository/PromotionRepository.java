package store.repository;

import java.util.HashMap;
import java.util.Map;
import store.domain.Promotion;

public class PromotionRepository {

    private static long id;
    private final Map<Long, Promotion> store = new HashMap<>();

    public void save(Promotion promotion) {
        store.put(++id, promotion);
    }

    public Promotion findByPromotionName(String name) {
        validateNotNull(name);
        for (Long id : store.keySet()) {
            Promotion promotion = store.get(id);
            if (promotion.hasName(name)) {
                return promotion;
            }
        }
        throw new IllegalArgumentException("해당 이름을 가진 프로모션이 존재하지 않습니다.");
    }

    public int size() {
        return store.size();
    }

    public boolean contains(Promotion promotion) {
        return store.containsValue(promotion);
    }

    public void clear() {
        store.clear();
    }

    private void validateNotNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException("프로모션을 이름으로 검색할 때 null 을 입력할 수 없습니다.");
        }
    }
}
