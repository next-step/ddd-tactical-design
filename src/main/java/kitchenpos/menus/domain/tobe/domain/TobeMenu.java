package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.tobe.domain.vo.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class TobeMenu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @EmbeddedId
    private MenuId id;

    @Column(name = "name", nullable = false)
    private MenuName name;

    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private TobeMenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private MenuDisplayed displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private MenuGroupId menuGroupId;

    protected TobeMenu() {
    }

    private TobeMenu(final MenuId id, final MenuName name, final MenuPrice price, final TobeMenuGroup menuGroup, final MenuDisplayed displayed, final MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroup.getId();
    }

    public TobeMenu changePrice(final MenuPrice price) {
        if (Objects.isNull(this.price)) {
            throw new IllegalArgumentException();
        }
        checkPriceIsGreaterThanSumOfMenuProductAmount();
        this.price = price;
        return this;
    }

    public TobeMenu display() {
        checkPriceIsGreaterThanSumOfMenuProductAmount();
        this.displayed = new MenuDisplayed(true);
        return this;
    }

    public TobeMenu hide() {
        this.displayed = new MenuDisplayed(false);
        return this;
    }

    private void checkPriceIsGreaterThanSumOfMenuProductAmount() {
        if (price.isGreaterThan(menuProducts.getSumOfMenuProductAmount())) {
            throw new IllegalArgumentException();
        }
    }

    public MenuId getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public TobeMenuGroup getMenuGroup() {
        return menuGroup;
    }

    public MenuDisplayed getDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public MenuGroupId getMenuGroupId() {
        return menuGroupId;
    }

    public static class Builder {
        private MenuId menuId;
        private MenuName name;
        private MenuPrice price;
        private TobeMenuGroup menuGroup;
        private MenuDisplayed displayed;
        private MenuProducts menuProducts;

        public Builder() {
            this.menuId = new MenuId(UUID.randomUUID());
        }

        public Builder id(final MenuId id) {
            this.menuId = id;
            return this;
        }

        public Builder name(final MenuName name) {
            this.name = name;
            return this;
        }

        public Builder price(final MenuPrice price) {
            this.price = price;
            return this;
        }

        public Builder menuGroup(final TobeMenuGroup menuGroup) {
            this.menuGroup = menuGroup;
            return this;
        }

        public Builder displayed(final MenuDisplayed displayed) {
            this.displayed = displayed;
            return this;
        }

        public Builder menuProducts(final MenuProducts menuProducts) {
            this.menuProducts = menuProducts;
            return this;
        }

        public TobeMenu build() {
            if (Objects.isNull(name) || Objects.isNull(price) || Objects.isNull(menuProducts) || Objects.isNull(menuProducts)) {
                throw new IllegalArgumentException();
            }
            if (displayed.isDisplayed()) {
                if (price.isGreaterThan(menuProducts.getSumOfMenuProductAmount())) {
                    throw new IllegalArgumentException();
                }
            }
            return new TobeMenu(menuId, name, price, menuGroup, displayed, menuProducts);
        }
    }
}
