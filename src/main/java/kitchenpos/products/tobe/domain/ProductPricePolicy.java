package kitchenpos.products.tobe.domain;

public interface ProductPricePolicy {
    void changePrice(Product product, ProductPrice price);
}
