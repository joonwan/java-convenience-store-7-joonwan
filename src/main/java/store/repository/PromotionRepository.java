package store.repository;

import static store.errormessage.PromotionRepositoryErrorMessage.FIND_BY_NULL_ERROR_MESSAGE;
import static store.errormessage.PromotionRepositoryErrorMessage.NOT_FOUND_PROMOTION_ERROR_MESSAGE;

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
            throw new IllegalArgumentException(NOT_FOUND_PROMOTION_ERROR_MESSAGE.getContent());
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
            throw new IllegalArgumentException(FIND_BY_NULL_ERROR_MESSAGE.getContent());
        }
    }

}
