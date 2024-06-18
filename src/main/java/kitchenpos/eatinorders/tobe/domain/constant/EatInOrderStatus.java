package kitchenpos.eatinorders.tobe.domain.constant;

public enum EatInOrderStatus {
    WAITING("대기"),
    ACCEPTED("접수"),
    SERVED("서빙"),
    COMPLETED("완료");

    private final String name;

    EatInOrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
