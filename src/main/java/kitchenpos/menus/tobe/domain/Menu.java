package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.vo.DisplayedName;
import kitchenpos.menus.tobe.vo.Price;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    @Column
    private Long menuGroupId;

    @Embedded
    private MenuProducts menuProducts;

    @Column(nullable = false)
    private boolean displayed;

    protected Menu() {
    }

    public Menu(String name, int price, Long menuGroupId, List<MenuProduct> menuProducts) {
        this(null, name, price, menuGroupId, menuProducts);
    }

    public Menu(Long id, String name, int price, Long menuGroupId, List<MenuProduct> menuProducts) {
        this.id = id;
        this.displayedName = new DisplayedName(name);
        this.price = new Price(price);
        this.menuGroupId = menuGroupId;
        this.menuProducts = new MenuProducts(menuProducts);
        this.displayed = true;
    }

    public void addProduct(Long productId, int count) {
        this.menuProducts.add(productId, count);
    }

    public void removeProduct(Long productId) {
        this.menuProducts.remove(productId);
    }

    public void changeProductCount(Long productId, int count) {
        this.menuProducts.changeCount(productId, count);
    }

    public void changePrice(int price) {
        this.price = new Price(price);
    }

    public void display() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public boolean isDisplayed() {
        return this.displayed;
    }

    public boolean equalsId(Long id) {
        return this.id.equals(id);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public Long getId() {
        return id;
    }
}
