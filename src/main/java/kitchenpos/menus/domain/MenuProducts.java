package kitchenpos.menus.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id", nullable = false, columnDefinition = "binary(16)")
    private List<MenuProduct> values = new ArrayList<>();

    public MenuProducts() {
    }

    public void addMenuProduct(MenuProduct menuProduct) {
        if (isNotContains(menuProduct)) {
            values.add(menuProduct);
        }
    }

    private boolean isNotContains(MenuProduct menuProduct) {
        return !values.contains(menuProduct);
    }

    public List<MenuProduct> getValues() {
        return values;
    }
}
