package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.ui.MenuRequest;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public static MenuProducts of(MenuRequest request, Map<String, Product> productMap) {
        final List<MenuProduct> menuProductList = request.getMenuProductRequests().stream()
                .map(it -> new MenuProduct(it.getSeq(), productMap.get(it.getProductId().toString()), it.getQuantity()))
                .collect(Collectors.toList());

        return new MenuProducts(menuProductList);
    }

    public BigDecimal getTotalPrice() {
        return menuProducts.stream()
                .map(it -> it.getProduct().getPrice().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Long> getSequences() {
        return menuProducts.stream()
                .map(MenuProduct::getSeq)
                .collect(Collectors.toList());
    }

    public int size() {
        return menuProducts.size();
    }
}
