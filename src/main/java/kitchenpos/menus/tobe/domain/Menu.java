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

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(final Name name, final Amount amount, final MenuGroup menuGroup, final List<MenuProduct> menuProducts) {
        this(randomUUID(), name, amount, menuGroup, false, new MenuProducts(menuProducts));
    }

    public Menu(final Name name, final Amount amount, final MenuGroup menuGroup, boolean displayed, final MenuProducts menuProducts) {
        this(randomUUID(), name, amount, menuGroup, displayed, menuProducts);
    }

    public Menu(final UUID id, final Name name, final Amount amount, final MenuGroup menuGroup, final boolean displayed, final MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
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
