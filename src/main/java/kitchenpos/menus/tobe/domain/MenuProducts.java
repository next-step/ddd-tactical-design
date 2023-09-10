package kitchenpos.menus.tobe.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MenuProduct> values;

    public MenuProducts(List<MenuProduct> values) {
        this.values = values;
    }

    protected MenuProducts() {

    }
}
