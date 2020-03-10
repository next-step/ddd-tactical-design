package kitchenpos.products.tobe.domain;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super("ProductNotFoundException product id : " + id);
    }
}
