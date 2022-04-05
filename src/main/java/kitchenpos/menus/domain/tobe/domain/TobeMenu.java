package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.common.exception.NamingRuleViolationException;
import kitchenpos.common.exception.PricingRuleViolationException;
import kitchenpos.common.policy.NamingRule;
import kitchenpos.common.policy.PricingRule;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.tobe.domain.vo.*;
import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;

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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<TobeMenuProduct> menuProducts;

    @Transient
    private MenuGroupId menuGroupId;

    protected TobeMenu() {
    }

    private TobeMenu(final MenuId id, final MenuName name, final MenuPrice price, final TobeMenuGroup menuGroup, final MenuDisplayed displayed, final List<TobeMenuProduct> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public TobeMenu changePrice(final BigDecimal price, final PricingRule rule) {
        if(Objects.isNull(rule) || Objects.isNull(price) || !rule.checkRule(price)) {
            throw new PricingRuleViolationException();
        }
        this.price = new MenuPrice(price);
        return this;
    }

    public TobeMenu display(final PricingRule pricingRule) {
        if(Objects.isNull(pricingRule) || !pricingRule.checkRule(this.price.getValue())) {
            throw new PricingRuleViolationException();
        }
        this.displayed = new MenuDisplayed(true);
        return this;
    }

    public TobeMenu hide() {
        this.displayed = new MenuDisplayed(false);
        return this;
    }

    public static class Builder {
        private MenuId menuId;
        private String name;
        private NamingRule namingRule;
        private BigDecimal price;
        private PricingRule pricingRule;
        private TobeMenuGroup menuGroup;
        private boolean displayed;
        private List<TobeMenuProduct> menuProducts;

        public Builder() {
            this.menuId = new MenuId(UUID.randomUUID());
        }

        public Builder name(final String name) {
            this.name=name;
            return this;
        }

        public Builder namingRule(final NamingRule namingRule) {
            this.namingRule=namingRule;
            return this;
        }

        public Builder price(final BigDecimal price) {
            this.price=price;
            return this;
        }

        public Builder pricingRule(final PricingRule pricingRule) {
            this.pricingRule = pricingRule;
            return this;
        }

        public Builder menuGroup(final TobeMenuGroup menuGroup) {
            this.menuGroup = menuGroup;
            return this;
        }

        public Builder displayed(final boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public Builder menuProducts(final List<TobeMenuProduct> menuProducts) {
            this.menuProducts = menuProducts;
            return this;
        }

        public TobeMenu build() {
            if (Objects.isNull(name) || Objects.isNull(namingRule) || !namingRule.checkRule(name)) {
                throw new NamingRuleViolationException();
            }
            if (Objects.isNull(price) || Objects.isNull(pricingRule) || !pricingRule.checkRule(price)) {
                throw new PricingRuleViolationException();
            }
            return new TobeMenu(menuId, new MenuName(name), new MenuPrice(price), menuGroup, new MenuDisplayed(displayed), menuProducts);
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

    public List<TobeMenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public MenuGroupId getMenuGroupId() {
        return menuGroupId;
    }
}
