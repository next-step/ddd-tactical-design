package kitchenpos.menu.adapter.out.client.response;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponse {
    private String id;
    private long price;

    public UUID getUUID() {
        return UUID.fromString(id);
    }

    public BigDecimal getBigDecimalPrice() {
        return BigDecimal.valueOf(price);
    }

    public String getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }
}
