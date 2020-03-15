package kitchenpos.menus.tobe.menu.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @Column(name = "menu_group_id", nullable = false)
    private Long menuGroupId;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        if (Strings.isBlank(name)) {
            throw new IllegalArgumentException("메뉴명을 입력해야합니다.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴가격은 0원 이상이여야합니다.");
        }
        if (menuGroupId == null) {
            throw new IllegalArgumentException("메뉴그룹이 지정되어야합니다.");
        }
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("제품을 1개 이상 지정해야합니다.");
        }

        this.name = name;
        this.price = new MenuPrice(price);
        this.menuGroupId = menuGroupId;
        this.menuProducts = new MenuProducts(menuProducts);

        validatePrice(price);
    }

    private void validatePrice(final BigDecimal price) {
        if (this.menuProducts.getTotalPrice().compareTo(price) < 0) {
            throw new IllegalArgumentException("메뉴가격은 구성된 메뉴 제품들의 가격 총합을 초과할 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.get();
    }
}
