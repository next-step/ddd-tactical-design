package kitchenpos.menu.domain.menu;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProductNew> values;

    protected MenuProducts() {
    }

    private MenuProducts(final List<MenuProductNew> values) {
        this.values = values;
    }

    public static MenuProducts create(final List<MenuProductNew> values) {
        return new MenuProducts(List.copyOf(values));
    }

    int totalAmount() {
        return values.stream()
            .mapToInt(MenuProductNew::amount)
            .sum();
    }

    public List<MenuProductNew> getValues() {
        return values;
    }
}
