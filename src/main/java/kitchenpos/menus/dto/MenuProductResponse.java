package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductResponse {
    private Long seq;
    private UUID productId;
    private long quantity;
    private BigDecimal price;
    private String name;

    public MenuProductResponse() {}

    public MenuProductResponse(Long seq, UUID productId, long quantity, BigDecimal price, String name) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
