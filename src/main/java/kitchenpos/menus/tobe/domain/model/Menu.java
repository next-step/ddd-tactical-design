package kitchenpos.menus.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

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

    public Menu(final DisplayedName name, final Price price, final MenuGroup menuGroup, final boolean displayed, final MenuProducts menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu changePrice(final Price price) {
        this.price = price;
        return this;
    }

    public Menu display() {
        this.displayed = true;
        return this;
    }

    public Menu hide() {
        this.displayed = false;
        return this;
    }

    public DisplayedName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }


}
