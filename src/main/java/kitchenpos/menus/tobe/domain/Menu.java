package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

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
    private MenuName name;

    @Embedded
    private Price price;

    @Column(name = "menu_group_id", columnDefinition = "varbinary(16)", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(PurgomalumClient purgomalumClient, UUID id, String name, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = new MenuName(purgomalumClient, name);
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(PurgomalumClient purgomalumClient, UUID id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this(purgomalumClient, id, name, new Price(price), menuGroupId, displayed, menuProducts);
    }

    public Menu(PurgomalumClient purgomalumClient, String name, BigDecimal price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this(purgomalumClient, null, name, price, menuGroupId, displayed, menuProducts);

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

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void changePrice(Price price, List<Product> findProducts) {
        if (menuProducts.isExpensiveMenuPrice(price, findProducts)) {
            throw new IllegalArgumentException("메뉴의 가격은 상품가격의 총합보다 작거나 같아야합니다.");
        }

        this.price = price;
    }

    public void show(List<Product> findProducts) {
        if (menuProducts.isExpensiveMenuPrice(price, findProducts)) {
            throw new IllegalStateException("메뉴의 가격이 상품의 가격보다 비쌀경우 메뉴를 노출할수 없습니다.");
        }

        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public List<UUID> getProductIds() {
        return menuProducts.getProductIds();
    }
}
