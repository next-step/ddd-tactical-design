package kitchenpos.products.tobe.domain;

public class ProductId {
    private Long id;

    private ProductId(Long id){
        this.id = id;
    }

    public static ProductId fromNumber(Long id){
        return new ProductId(id);
    }

    public Long getId() {
        return id;
    }
}
