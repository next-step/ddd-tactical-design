package kitchenpos.products.tobe.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private List<Product> values = new ArrayList<>();


    protected Products() {
    }
    public Products(List<Product> inputs) {
        this.values = inputs;
    }

}
