package kitchenpos.menus.tobe.domain;

import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Menu는 번호와 이름, 가격, MenuProducts를 가진다.
 * Menu는 특정 MenuGroup에 속한다.
 */
public class Menu {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long menuGroupId;
    private MenuProducts menuProducts;

    public Menu(String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        validName(name);
        validPrice(price);
        validMenuProducts(menuProducts);
        validMenuGroup(menuGroupId);

        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = new MenuProducts(menuProducts);
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

    private void validPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validName(String name) {
        if(Objects.isNull(name) || StringUtils.isEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException();
        }
    }

    public void changeMenuProducts(MenuProducts menuProducts) {
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
