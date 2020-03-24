package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.tool.Price;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Menu는 번호와 이름, 가격, MenuProducts를 가진다.
 * Menu는 특정 MenuGroup에 속한다.
 */
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Price price;

    private Long menuGroupId;

    @OneToMany(mappedBy = "menu")
    private List<MenuProduct> menuProducts;

    protected Menu() {
    }

    public Menu(String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        validName(name);
        validMenuProducts(menuProducts);
        validMenuGroup(menuGroupId);

        this.name = name;
        this.price = new Price(price);
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    private void validMenuGroup(Long menuGroupId) {
        if (Objects.isNull(menuGroupId) || menuGroupId <= 0L) {
            throw new IllegalArgumentException();
        }
    }

    private void validMenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.size() == 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validName(String name) {
        if(Objects.isNull(name) || StringUtils.isEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException();
        }
    }

    public void changeMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public String menuName() {
        return name;
    }

    public BigDecimal menuPrice() {
        return price.getPrice();
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
