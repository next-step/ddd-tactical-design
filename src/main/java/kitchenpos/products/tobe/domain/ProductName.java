package kitchenpos.products.tobe.domain;

public class ProductName {
    private String name;

    public ProductName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static ProductName changeProductName(String name){
        return new ProductName(name);
    }
}
