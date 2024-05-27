package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;

public class Product {
    final Name name;
    final Price price;

    public Product(Name name, Price price) {
        this.name = name;
        this.price = price;
    }

    public void validateProperty(){
        this.checkValidPrice(this.price);
        this.checkValidName(this.name);
    }

    private void checkValidName(Name name){
      // add third-party service to check name
    }

    private void checkValidPrice(Price price) {
        if (price.getValue() <= 0) {
            throw new IllegalArgumentException("Price should be over zero, not negative");
        }
    }
}
