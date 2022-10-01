package kitchenpos.eatinorders.tobe.dto;

import java.io.Serializable;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;

public class MenuDTO implements Serializable {

    private final UUID id;
    private final Name name;
    private final Price price;

    public MenuDTO(UUID id, Name name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public DisplayedMenu toMenu() {
        return new DisplayedMenu(
                id,
                name,
                price
        );
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
