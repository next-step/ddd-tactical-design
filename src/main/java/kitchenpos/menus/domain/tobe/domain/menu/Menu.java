package kitchenpos.menus.domain.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.MenuId;

@Table(name = "menu")
@Entity
public class Menu {

    @EmbeddedId
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private MenuId id;

    @Embedded
    @Column(name = "displayed_name", nullable = false)
    private DisplayedName displayedName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Embedded
    @Column(name = "menu_group_id", columnDefinition = "varbinary(16)")
    private MenuGroupId menuGroupId;

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

    public Menu(
        final MenuId id,
        final DisplayedName name,
        final BigDecimal price,
        final MenuGroupId menuGroupId,
        final boolean displayed,
        final List<MenuProduct> menuProducts
    ) {
        this.id = id;
        this.displayedName = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
