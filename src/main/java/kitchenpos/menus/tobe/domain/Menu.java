package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kitchenpos.products.tobe.domain.PurgomalumClient;

@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name = new MenuName();

    @Embedded
    private MenuPrice price = new MenuPrice();

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();

    @Enumerated(EnumType.STRING)
    private DisplayStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuGroup menuGroup = new MenuGroup();

    public Menu(String name, BigDecimal price, MenuGroup menuGroup, List<MenuProduct> menuProducts, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.name = new MenuName(name, purgomalumClient);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.menuProducts = new MenuProducts(menuProducts);
        this.status = DisplayStatus.DISPLAY;
    }

    protected Menu() {
    }

    public void display() {
        this.status = DisplayStatus.DISPLAY;
    }

    public void hide() {
        this.status = DisplayStatus.HIDE;
    }

    public MenuProduct makeMenuProduct(UUID productId, BigDecimal productPrice, long quantity) {
        return new MenuProduct(this, productId, productPrice, quantity);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public DisplayStatus getStatus() {
        return status;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public enum DisplayStatus {
        DISPLAY, HIDE
    }
}
