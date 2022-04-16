package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.MenuGroup;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private MenuPrice price;

    @Embedded
    private MenuProducts menuProducts;


    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;



    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(final DisplayedName name, final BigDecimal price, final List<MenuProduct> menuProducts) {
        this(null, name, new MenuPrice(price), menuProducts);
    }

    public Menu(final UUID id, final DisplayedName name, final BigDecimal price, final List<MenuProduct> menuProducts) {
        this(id, name, new MenuPrice(price), menuProducts);
    }

    public Menu(final UUID id, final DisplayedName name, final MenuPrice price, final List<MenuProduct> menuProducts) {
        validate(price, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuProducts = new MenuProducts(menuProducts);
    }

    private void validate(final MenuPrice price, final List<MenuProduct> menuProducts) {
        final int sum = menuProducts.stream().mapToInt(MenuProduct::amount).sum();
        if (price.isGreaterThan(sum)) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품 금액의 합보다 클 수 없습니다.");
        }
    }


}
