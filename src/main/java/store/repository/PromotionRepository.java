package store.repository;

import java.util.HashMap;
import java.util.Map;
import store.domain.Promotion;

public class PromotionRepository {

    private final Map<String, Promotion> store = new HashMap<>();

    public void save(String name, Promotion promotion) {
        store.put(name, promotion);
    }

    public Promotion findByPromotionName(String name) {
        validateNotNull(name);

        Promotion findPromotion = store.get(name);
        if (findPromotion == null) {
            throw new IllegalArgumentException("해당 이름을 가진 프로모션이 존재하지 않습니다.");
        }
        return findPromotion;

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
