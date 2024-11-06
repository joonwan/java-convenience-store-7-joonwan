package store.domain;

import java.time.LocalDate;

public class Promotion {

    private long id;

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
}
