package kitchenpos.menus.tobe.menu.entity;

import kitchenpos.common.Name;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import javax.persistence.*;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price"))
    private Price price;

    @Embedded
    private Name name;

    @Embedded
    @AttributeOverride(name = "number", column = @Column(name = "menuGroupId"))
    private PositiveNumber menuGroupId;

    @Embedded
    @AttributeOverride(name ="price", column = @Column(name ="amount"))
    private Price amount;

}
