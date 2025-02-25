package kitchenpos.menu.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuName;
import kitchenpos.menu.domain.model.MenuPrice;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "menu")
@Entity
@DynamicUpdate
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @Column(name = "menu_group_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected Menu() {}

    public Menu(UUID uuid, MenuName name, MenuPrice price, UUID menuGroupId, boolean displayed, List<MenuProduct> menuProducts) {
        this.id = uuid;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public MenuName getName() {
        return name;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void updateDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public void updatePrice(MenuPrice price) {
        this.price = price;
    }

    public boolean isLessThanOrEqual(BigDecimal diff) {
        return price.isLessThanOrEqual(diff);
    }

    public boolean isEqual(BigDecimal diff) {
        return price.isEqual(diff);
    }
}
