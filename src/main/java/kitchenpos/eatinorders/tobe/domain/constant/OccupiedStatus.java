package kitchenpos.eatinorders.tobe.domain.constant;

public enum OccupiedStatus {
    OCCUPIED_TABLE("사용중 테이블"),
    EMPTY_TABLE("빈 테이블");

    private final String name;

    OccupiedStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
