package kitchenpos.menus.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.springframework.util.CollectionUtils;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> items;

    protected MenuProducts() { }

    private MenuProducts(List<MenuProduct> items) {
        validItems(items);
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }

    private void validItems(List<MenuProduct> items) {
        if (CollectionUtils.isEmpty(items)) {
            throw new IllegalArgumentException("MenuProduct 목록은 비어있을 수 없습니다.");
        }
    }

    public static MenuProducts from(List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }

    public List<MenuProduct> getItems() {
        return items;
    }

    public long getSumProductsPrice() {
        return items.stream()
                    .mapToLong(MenuProduct::getPrice)
                    .sum();
    }
}
