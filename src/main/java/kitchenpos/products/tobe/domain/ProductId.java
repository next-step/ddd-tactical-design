package kitchenpos.products.tobe.domain;

import java.util.Optional;

public class ProductId {
    private static Long maxId  = 0L;

    private Long id;

    private ProductId(Long val){
        this.id = val;
    }

    private ProductId(){
        maxId = maxId + 1;
        this.id = maxId;
    }

    protected static ProductId newProduct() { return new ProductId(); }

    public Optional<ProductId> valueOf(Long val) {
        if (val > 0L && val <= maxId) {
            return Optional.of(new ProductId(val));
        }
        return null;
    }

    public Long getValue() {
        return this.id;
    }
}
