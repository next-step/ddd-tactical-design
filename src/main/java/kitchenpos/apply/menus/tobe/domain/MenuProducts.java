package kitchenpos.apply.menus.tobe.domain;

import kitchenpos.apply.menus.tobe.ui.MenuProductRequest;
import kitchenpos.support.domain.ValueObject;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts extends ValueObject {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="menu_id")
    private List<MenuProduct> menuProductList = new ArrayList<>();

    protected MenuProducts() { }

    public MenuProducts(List<MenuProduct> menuProductList) {
        if (Objects.isNull(menuProductList) || menuProductList.isEmpty()) {
            throw new IllegalArgumentException("구성품은 비어있 을 수 없습니다");
        }
        this.menuProductList = menuProductList;
    }

    public static MenuProducts of(List<MenuProductRequest> menuProductRequests) {
        final List<MenuProduct> menuProductList = menuProductRequests.stream()
                .map(it -> new MenuProduct(it.getSeq(), it.getProductId(), it.getQuantity()))
                .collect(Collectors.toList());

        return new MenuProducts(menuProductList);
    }

    public List<Long> getSequences() {
        return menuProductList.stream()
                .map(MenuProduct::getSeq)
                .collect(Collectors.toList());
    }

    public List<MenuProduct> getMenuProductList() {
        return menuProductList;
    }

    public int size() {
        return menuProductList.size();
    }
}
