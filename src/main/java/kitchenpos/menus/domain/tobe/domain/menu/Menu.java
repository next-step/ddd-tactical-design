package kitchenpos.menus.domain.tobe.domain.menu;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Price;

@Table(name = "menu")
@Entity
public class Menu {

    @EmbeddedId
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private MenuId id;

    @Embedded
    @Column(name = "displayed_name", nullable = false)
    private DisplayedName displayedName;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Embedded
    @Column(name = "menu_group_id", columnDefinition = "varbinary(16)")
    private MenuGroupId menuGroupId;

    @Embedded
    @Column(name = "displayed", nullable = false)
    private Displayed displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(
        final MenuId id,
        final DisplayedName name,
        final Price price,
        final MenuGroupId menuGroupId,
        final Displayed displayed,
        final MenuProducts menuProducts
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
