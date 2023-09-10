package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> values;

    public MenuProducts(List<MenuProduct> values) {
        this.values = values;
    }

    protected MenuProducts() {

    }

    public List<MenuProduct> getValues() {
        return Collections.unmodifiableList(values);
    }
}
