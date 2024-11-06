package store.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDate startTime;
    private LocalDate endTime;

    public Promotion(String name, int buy, int get, LocalDate startTime, LocalDate endTime) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startTime = startTime;
        this.endTime = endTime;
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
                && Objects.equals(startTime, promotion.startTime) && Objects.equals(endTime,
                promotion.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buy, get, startTime, endTime);
    }
}
