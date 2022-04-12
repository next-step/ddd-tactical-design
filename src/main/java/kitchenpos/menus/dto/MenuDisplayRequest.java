package kitchenpos.menus.dto;

import kitchenpos.support.policy.PricingRule;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;

public class MenuDisplayRequest {
    private MenuId menuId;
    private PricingRule pricingRule;

    public MenuDisplayRequest() {
    }

    public MenuDisplayRequest(MenuId menuId, PricingRule pricingRule) {
        this.menuId = menuId;
        this.pricingRule = pricingRule;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
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

        MenuDisplayRequest that = (MenuDisplayRequest) o;

        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        return pricingRule != null ? pricingRule.equals(that.pricingRule) : that.pricingRule == null;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (pricingRule != null ? pricingRule.hashCode() : 0);
        return result;
    }
}
