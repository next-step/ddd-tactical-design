package kitchenpos.eatinordertables.ui.request;

public class EatInOrderTableCreateRequest {

    private String name;

    protected EatInOrderTableCreateRequest() {
    }

    public EatInOrderTableCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
