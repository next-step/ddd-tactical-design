package kitchenpos.menu.tobe.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id", nullable = false)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {

    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        validate(menuProducts);
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    private static void validate(List<MenuProduct> menuProducts) {
        if (menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴상품 정보는 빈값일 수 없습니다.");
        }
    }

    public List<MenuProduct> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }
}
