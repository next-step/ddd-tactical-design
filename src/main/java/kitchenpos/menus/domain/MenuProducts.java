package kitchenpos.menus.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuProduct> values = new ArrayList<>();

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> values) {
        this.values.addAll(values);
        validateEmpty(this.values);
    }

    private void validateEmpty(List<MenuProduct> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("메뉴의 상품은 1개 이상이어야 합니다.");
        }
    }

    public void enrollMenu(Menu menu) {
        values.forEach(it -> it.enrollMenu(menu));
    }

    public void updatePrice(MenuProductPrice newPrice) {
        values.forEach(it -> it.updatePrice(newPrice));
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
