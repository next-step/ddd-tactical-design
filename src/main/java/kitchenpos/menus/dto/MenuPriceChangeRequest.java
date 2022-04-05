package kitchenpos.menus.dto;

import kitchenpos.common.policy.PricingRule;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;

import java.math.BigDecimal;

public class MenuPriceChangeRequest {
    private MenuId menuId;
    private BigDecimal price;
    private PricingRule pricingRule;

    public MenuPriceChangeRequest() {
    }

    public MenuPriceChangeRequest(MenuId menuId, BigDecimal price, PricingRule pricingRule) {
        this.menuId = menuId;
        this.price = price;
        this.pricingRule = pricingRule;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuPriceChangeRequest that = (MenuPriceChangeRequest) o;

        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return pricingRule != null ? pricingRule.equals(that.pricingRule) : that.pricingRule == null;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (pricingRule != null ? pricingRule.hashCode() : 0);
        return result;
    }
}
