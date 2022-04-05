package kitchenpos.menus.dto;

import kitchenpos.common.policy.NamingRule;
import kitchenpos.common.policy.PricingRule;
import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;

import java.math.BigDecimal;
import java.util.List;

public class MenuRegisterResponse {
    private MenuId menuId;
    private String name;
    private NamingRule namingRule;
    private BigDecimal price;
    private PricingRule pricingRule;
    private MenuGroupId menuGroupId;
    private List<TobeMenuProduct> menuProducts;
    private boolean displayed;

    public MenuRegisterResponse() {
    }

    public MenuRegisterResponse(MenuId menuId, String name, NamingRule namingRule, BigDecimal price, PricingRule pricingRule, MenuGroupId menuGroupId, List<TobeMenuProduct> menuProducts, boolean displayed) {
        this.menuId = menuId;
        this.name = name;
        this.namingRule = namingRule;
        this.price = price;
        this.pricingRule = pricingRule;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NamingRule getNamingRule() {
        return namingRule;
    }

    public void setNamingRule(NamingRule namingRule) {
        this.namingRule = namingRule;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PricingRule getPricingRule() {
        return pricingRule;
    }

    public void setPricingRule(PricingRule pricingRule) {
        this.pricingRule = pricingRule;
    }

    public MenuGroupId getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(MenuGroupId menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public List<TobeMenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<TobeMenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuRegisterResponse that = (MenuRegisterResponse) o;

        if (displayed != that.displayed) return false;
        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (namingRule != null ? !namingRule.equals(that.namingRule) : that.namingRule != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (pricingRule != null ? !pricingRule.equals(that.pricingRule) : that.pricingRule != null) return false;
        if (menuGroupId != null ? !menuGroupId.equals(that.menuGroupId) : that.menuGroupId != null) return false;
        return menuProducts != null ? menuProducts.equals(that.menuProducts) : that.menuProducts == null;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (namingRule != null ? namingRule.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (pricingRule != null ? pricingRule.hashCode() : 0);
        result = 31 * result + (menuGroupId != null ? menuGroupId.hashCode() : 0);
        result = 31 * result + (menuProducts != null ? menuProducts.hashCode() : 0);
        result = 31 * result + (displayed ? 1 : 0);
        return result;
    }
}
