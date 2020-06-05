package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.model.Price;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    @AttributeOverride(column = @Column(name = "price"), name = "value")
    private Price price;

    private Long menuGroupId;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    private Menu(Long id, String name, Price price, Long menuGroupId, MenuProducts menuProducts) {
        validatePrice(price, menuProducts);

        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public static Menu of(String name, BigDecimal price, Long menuGroupId,
        MenuProducts menuProducts) {
        return Menu.of(null, name, price, menuGroupId, menuProducts);
    }

    public static Menu of(Long id, String name, BigDecimal price, Long menuGroupId,
        MenuProducts menuProducts) {
        return new Menu(id, name, Price.of(price), menuGroupId, menuProducts);
    }

    private void validatePrice(Price price, MenuProducts menuProducts) {
        if (price.compareTo(menuProducts.computeMenuProductsPriceSum()) > 0) {
            throw new IllegalArgumentException("price를 확인해주세요.");
        }
    }

    public Price computeMenuProductsPriceSum() {
        return menuProducts.computeMenuProductsPriceSum();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
