package kitchenpos.menus.domain;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.menus.exception.InvalidMenuProductsException;
import kitchenpos.menus.exception.MenuNotFoundException;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id", nullable = false, columnDefinition = "binary(16)")
    private List<MenuProduct> values = new ArrayList<>();

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> values) {
        this.values.addAll(values);
        validateEmpty(this.values);
        validateMenuPrice(this.values);
    }

    private void validateEmpty(List<MenuProduct> values) {
        if (values.isEmpty()) {
            throw new InvalidMenuProductsException("메뉴의 상품은 1개 이상이어야 합니다.");
        }
    }

    private void validateMenuPrice(List<MenuProduct> values) {
        long sumOfPrice = getSumOfPrice(values);
        long menuPrice = getMenuPrice(values);

        if (menuPrice > sumOfPrice) {
            throw new InvalidMenuProductsException("메뉴의 가격은 상품들의 가격 합보다 작거나 같아야 합니다.");
        }
    }

    private long getSumOfPrice(List<MenuProduct> values) {
        return values.stream()
            .map(MenuProduct::getQuantityMultipliedPrice)
            .mapToLong(BigDecimal::longValue)
            .sum();
    }

    private long getMenuPrice(List<MenuProduct> values) {
        return values.stream()
            .map(MenuProduct::getMenu)
            .findAny()
            .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."))
            .getPriceValue()
            .longValue();
    }

    public void addMenuProduct(MenuProduct menuProduct) {
        if (isNotContains(menuProduct)) {
            values.add(menuProduct);
        }
    }

    private boolean isNotContains(MenuProduct menuProduct) {
        return !values.contains(menuProduct);
    }

    public List<UUID> getProductIds() {
        return values.stream()
            .map(MenuProduct::getProductId)
            .collect(toList());
    }

    public List<MenuProduct> getValues() {
        return values;
    }

    public int size() {
        return values.size();
    }
}
