package kitchenpos.menus.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private MenuPrice price;

    @Column(name = "menu_group_id", nullable = false)
    private Long menuGroupId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private List<MenuProduct> menuProducts;

    public Menu(Long id, String name, MenuPrice price, Long menuGroupId, List<MenuProduct> menuProducts) {
        validate(name, menuGroupId, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public Menu(Long id, String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        this(id, name, new MenuPrice(price), menuGroupId, menuProducts);
    }

    public Menu() {
    }

    private void validate(String name, Long menuGroupId, List<MenuProduct> menuProducts) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        if (menuGroupId == null) {
            throw new IllegalArgumentException();
        }
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void priceValidate(BigDecimal totalMenuProductsPrice) {
        if (price.compareTo(totalMenuProductsPrice) > 0) {
            throw new IllegalArgumentException("price cannot bigger than total menu products price . ");
        }
    }
}
