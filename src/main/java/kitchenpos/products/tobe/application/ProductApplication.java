package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductApplication {
    public Product RegisterNewProduct(String name, BigDecimal price){
        Product product = new Product(name, price);

        return product;
    }
}
