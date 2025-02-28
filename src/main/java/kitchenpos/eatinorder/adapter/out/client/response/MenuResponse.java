package kitchenpos.eatinorder.adapter.out.client.response;

import java.util.UUID;

public class MenuResponse {
    private UUID id;
    private long price;
    private boolean isDisplayed;

    public UUID getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }
}
