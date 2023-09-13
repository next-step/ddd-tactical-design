package kitchenpos.products.domain.vo;

import kitchenpos.products.domain.Product;

import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int size() {
        return products.size();
    }
}