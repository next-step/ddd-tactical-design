package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class TobeMenu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private TobeMenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected TobeMenu() {
    }

    public TobeMenu(MenuName name, MenuPrice price, TobeMenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this(
            UUID.randomUUID(),
            name,
            price,
            menuGroup,
            displayed,
            menuProducts
        );
    }

    public TobeMenu(UUID id, MenuName name, MenuPrice price, TobeMenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public TobeMenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void changeDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }
}
