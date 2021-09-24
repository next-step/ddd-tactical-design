package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Amount amount;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected Menu() {
    }

    public Menu(final Name name, final Amount amount, final MenuGroup menuGroup, final List<MenuProduct> menuProducts) {
        this(randomUUID(), name, amount, menuGroup, false, menuProducts);
    }

    public Menu(final UUID id, final Name name, final Amount amount, final MenuGroup menuGroup, final boolean displayed, final List<MenuProduct> menuProducts) {
        verify(menuProducts);
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private void verify(final List<MenuProduct> menuProducts) {
        if(Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴는 1개 이상의 상품이 필요합니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Menu menu = (Menu) o;
        return Objects.equals(id, menu.id) || (displayed == menu.displayed && Objects.equals(name, menu.name) && Objects.equals(amount, menu.amount) && Objects.equals(menuGroup, menu.menuGroup) && Objects.equals(menuProducts, menu.menuProducts));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, menuGroup, displayed, menuProducts);
    }
}
