package kitchenpos.menus.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.menus.exception.InvalidMenuProductsException;

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
    }

    private void validateEmpty(List<MenuProduct> values) {
        if (values.isEmpty()) {
            throw new InvalidMenuProductsException("메뉴의 상품은 1개 이상이어야 합니다.");
        }
    }

    public long getSumOfPrice() {
        return values.stream()
            .map(MenuProduct::getQuantityMultipliedPrice)
            .mapToLong(BigDecimal::longValue)
            .sum();
    }

    public List<MenuProduct> getValues() {
        return values;
    }
}
