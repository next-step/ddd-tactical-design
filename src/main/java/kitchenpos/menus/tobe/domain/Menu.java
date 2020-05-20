package kitchenpos.menus.tobe.domain;

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

    public Menu(String name, BigDecimal price, Long menuGroupId, MenuProducts menuProducts) {
        this(null, name, Price.of(price), menuGroupId, menuProducts);
    }

    public Menu(Long id, String name, BigDecimal price, Long menuGroupId,
        MenuProducts menuProducts) {
        this(id, name, Price.of(price), menuGroupId, menuProducts);
    }

    public Menu(Long id, String name, Price price, Long menuGroupId, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
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
