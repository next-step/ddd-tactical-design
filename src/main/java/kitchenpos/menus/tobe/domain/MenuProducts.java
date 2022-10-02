package kitchenpos.menus.tobe.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        validate(menuProducts);
        this.menuProducts.addAll(menuProducts);
    }

    private void validate(List<MenuProduct> menuProducts) {
        if (menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴 상품은 비어있을 수 없습니다");
        }
    }

    public void add(List<MenuProduct> menuProducts) {
        this.menuProducts.addAll(menuProducts);
    }

    public void add(Long productId, int count) {
        menuProducts.add(new MenuProduct(productId, count));
    }

    public void remove(Long productId) {
        MenuProduct menuProduct = findProductById(productId);
        menuProducts.remove(menuProduct);
    }

    public void changeCount(Long productId, int count) {
        MenuProduct menuProduct = findProductById(productId);
        menuProduct.changeCount(count);
    }

    private MenuProduct findProductById(Long productId) {
        return menuProducts.stream()
                .filter(it -> it.getProductId().equals(productId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<MenuProduct> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }
}
