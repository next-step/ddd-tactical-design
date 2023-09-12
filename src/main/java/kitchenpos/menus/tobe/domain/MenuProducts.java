package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.ui.MenuProductRequest;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() { }

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("구성품은 비어있 을 수 없습니다");
        }
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(List<MenuProductRequest> menuProductRequests) {
        final List<MenuProduct> menuProductList = menuProductRequests.stream()
                .map(it -> new MenuProduct(it.getSeq(), it.getProductId(), it.getQuantity()))
                .collect(Collectors.toList());

        return new MenuProducts(menuProductList);
    }

    public List<Long> getSequences() {
        return menuProducts.stream()
                .map(MenuProduct::getSeq)
                .collect(Collectors.toList());
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public int size() {
        return menuProducts.size();
    }
}
