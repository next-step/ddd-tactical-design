package kitchenpos.menus.tobe.domain.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.menus.tobe.domain.service.MenuDomainService;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    public MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts, MenuDomainService menuDomainService) {
        menuDomainService.validateMenuProducts(menuProducts);
        this.menuProducts = menuProducts;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
