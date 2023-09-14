package kitchenpos.product.tobe.domain.port.inp;

import kitchenpos.product.tobe.domain.Name;
import kitchenpos.product.tobe.domain.Price;

public class NewProductCreatorCommand {
    private final Name name;
    private final Price price;

    public NewProductCreatorCommand(Name name, Price price) {
        this.name = name;
        this.price = price;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
