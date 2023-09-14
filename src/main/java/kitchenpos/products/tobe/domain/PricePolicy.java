package kitchenpos.products.tobe.domain;

public interface PricePolicy {
    void changePrice(Product product, ProductPrice price);
}
