package kitchenpos.menus.domain;

import kitchenpos.products.domain.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private MenuDisplayedName name;

    @Column(name = "price", nullable = false)
    private MenuPrice price;


    @Column(name = "menu_group_id", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();


    public Menu() {
    }

    public Menu(String name,
                PurgomalumClient purgomalumClient,
                BigDecimal price,
                UUID menuGroupId,
                boolean displayed,
                List<MenuProduct> menuProducts
    ) {
        this.id = UUID.randomUUID();
        this.name = new MenuDisplayedName(name, purgomalumClient);
        this.price = new MenuPrice(price);
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts.addAll(menuProducts);
        validatePrice();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.get();
    }

    public void hide() {
        this.displayed = false;
    }

    public void show() {
        validatePrice();
        this.displayed = true;
    }

    public void changePrice(BigDecimal price) {
        this.price = new MenuPrice(price);
        validatePrice();
    }

    private void validatePrice() {
        if (menuProducts.hasTotalPriceLowerThan(this.price.getValue())) {
            throw new IllegalArgumentException();
        }
    }
}
