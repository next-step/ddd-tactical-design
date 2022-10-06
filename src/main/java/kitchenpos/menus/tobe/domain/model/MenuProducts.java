package kitchenpos.menus.tobe.domain.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(name = "menu_id")
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    long amounts() {
        return menuProducts.stream()
                .mapToLong(MenuProduct::amount)
                .reduce(0, Long::sum);
    }

    void update(UUID productId, Long changedPrice) {
        MenuProduct menuProduct = menuProducts.stream()
                .filter(it -> it.matchProductId(productId))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("메뉴에 존재하지 않는 상품입니다."));

        menuProduct.updatePrice(changedPrice);
    }
}
