package kitchenpos.products.tobe.domain;

public class ProductId {
    private static Long maxId  = 0L;

    private Long id;

    private ProductId(){
        maxId = maxId + 1;
        this.id = maxId;
    }

    protected static ProductId newProduct() { return new ProductId(); }

    public Long getValue() {
        return this.id;
    }
}
