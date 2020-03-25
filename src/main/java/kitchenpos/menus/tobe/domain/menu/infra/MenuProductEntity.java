package kitchenpos.menus.tobe.domain.menu.infra;

import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import javax.persistence.*;

@Entity
@Table(name = "menu_product")
public class MenuProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "menu_id")
//    private Menu menu;

    @Embedded
    @AttributeOverride(
        name = "number",
        column = @Column(name = "menu_id")
    )
    private PositiveNumber menuId;

    @Embedded
    @AttributeOverride(
        name = "number",
        column = @Column(name = "product_id")
    )
    private PositiveNumber productId;

    @Embedded
    @AttributeOverride(
        name = "number",
        column = @Column(name = "quantity")
    )
    private PositiveNumber quantity;

    @Transient
    private Price price;

    protected MenuProductEntity(){}

    public MenuProductEntity(Long id,
                             PositiveNumber menuId,
                             PositiveNumber productId,
                             PositiveNumber quantity){
        this.id = id;
        this.menuId = menuId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public PositiveNumber getProductId() {
        return productId;
    }

    public PositiveNumber getQuantity() {
        return quantity;
    }
}
