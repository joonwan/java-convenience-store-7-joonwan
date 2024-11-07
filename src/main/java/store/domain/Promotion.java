package store.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Promotion(String name, int buy, int get, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotion promotion = (Promotion) o;
        return buy == promotion.buy && get == promotion.get && Objects.equals(name, promotion.name)
                && Objects.equals(startDateTime, promotion.startDateTime) && Objects.equals(endDateTime,
                promotion.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buy, get, startDateTime, endDateTime);
    }
}
