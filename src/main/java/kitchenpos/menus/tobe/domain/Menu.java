package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity(name = "tobeMenu")
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProductCatalog menuProductCatalog) {
        validatePrice(price, menuProductCatalog.calculateTotalPrice());
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(menuProductCatalog);
    }

    private void validatePrice(MenuPrice price, BigDecimal total) {
        if (!price.isGreaterThan(total)) {
            throw new IllegalArgumentException("메뉴 가격이 전체 메뉴 상품의 가격 합보다 같거나 작아야 한다");
        }
    }

    public UUID getId() {
        return id;
    }

    public void changePrice(MenuPrice price, ProductInfos productInfos) {
        validatePrice(price, menuProducts.calculateTotalPrice(productInfos));
        this.price = price;
    }

    public List<UUID> findProductIds() {
        return menuProducts.getProductIds();
    }

    public void display(ProductInfos productInfos) {
        validatePrice(price, menuProducts.calculateTotalPrice(productInfos));
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public MenuPrice getPrice() {
        return price;
    }
}
