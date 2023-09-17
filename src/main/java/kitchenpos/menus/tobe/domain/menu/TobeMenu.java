package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProducts;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class TobeMenu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName menuName;

    @Embedded
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private TobeMenuGroup tobeMenuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(
//        name = "menu_id",
//        nullable = false,
//        columnDefinition = "binary(16)",
//        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
//    )
//    private List<TobeMenuProduct> tobeMenuProducts;

    @Embedded
    private TobeMenuProducts tobeMenuProducts = new TobeMenuProducts();


    @Transient
    private UUID menuGroupId;

    protected TobeMenu() {
    }

    public TobeMenu(UUID id, MenuName menuName, MenuPrice price, TobeMenuGroup tobeMenuGroup, boolean displayed,
                    List<TobeMenuProduct> tobeMenuProducts) {
        if (Objects.isNull(tobeMenuProducts) || tobeMenuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴의 상품은 비어있을 수 없습니다.");
        }

        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.tobeMenuGroup = tobeMenuGroup;
        this.displayed = displayed;
        this.tobeMenuProducts = new TobeMenuProducts(tobeMenuProducts);
    }

    public TobeMenu(UUID id, MenuName menuName, MenuPrice price, TobeMenuGroup tobeMenuGroup, boolean displayed,
                    TobeMenuProducts tobeMenuProducts) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.tobeMenuGroup = tobeMenuGroup;
        this.displayed = displayed;
        this.tobeMenuProducts = tobeMenuProducts;
    }

    public TobeMenu(MenuPrice price) {
        this.price = price;
    }

    public void display() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void updatePrice(MenuPrice price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public MenuName getMenuName() {
        return menuName;
    }
    public MenuPrice getPrice() {
        return price;
    }

    public TobeMenuGroup getTobeMenuGroup() {
        return tobeMenuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public TobeMenuProducts getTobeMenuProducts() {
        return tobeMenuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

}
