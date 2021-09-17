package kitchenpos.eatinorders.tobe.dto;

import java.util.UUID;

public class MenuGroupResponse {
    private UUID id;
    private String name;

    protected MenuGroupResponse() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
