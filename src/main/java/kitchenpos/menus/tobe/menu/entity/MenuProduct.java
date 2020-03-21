package kitchenpos.menus.tobe.menu.entity;

import kitchenpos.common.PositiveNumber;

import javax.persistence.*;

@Entity
public class MenuProduct {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(
        name = "number",
        column = @Column(name = "menuId")
    )
    private PositiveNumber menuId;

    @Embedded
    @AttributeOverride(
        name = "number",
        column = @Column(name = "productId")
    )
    private PositiveNumber productId;

    @Embedded
    @AttributeOverride(
        name = "number",
        column = @Column(name = "quantity")
    )
    private PositiveNumber quantity;

}
