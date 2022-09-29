package kitchenpos.menus.tobe.dto;

import java.io.Serializable;
import java.util.UUID;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.menus.tobe.domain.vo.Product;

public class ProductDTO implements Serializable {

    private final UUID id;
    private final Name name;
    private final Price price;

    public ProductDTO(UUID id, Name name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product toProduct() {
        return new Product(
                id,
                name,
                price
        );
    }

    public UUID getId() {
        return id;
    }
}
