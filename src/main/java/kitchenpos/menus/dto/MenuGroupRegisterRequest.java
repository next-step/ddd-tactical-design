package kitchenpos.menus.dto;

import kitchenpos.common.policy.NamingRule;

public class MenuGroupRegisterRequest {
    private String name;
    private NamingRule namingRule;

    public MenuGroupRegisterRequest(String name, NamingRule namingRule) {
        this.name = name;
        this.namingRule = namingRule;
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
}
