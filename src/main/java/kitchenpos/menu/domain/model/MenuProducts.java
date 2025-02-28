package kitchenpos.menu.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.domain.entity.MenuProduct;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    @JsonManagedReference
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public static MenuProducts of(final List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_MENU_PRODUCT.toString());
        }
        return new MenuProducts(menuProducts);
    }

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public List<MenuProduct> get() {
        return Collections.unmodifiableList(new ArrayList<>(menuProducts));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuProducts);
    }
}
