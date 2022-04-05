package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.common.policy.NamingRule;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupName;
import kitchenpos.menus.exception.MenuGroupNamingRuleViolationException;
import kitchenpos.products.exception.ProductNamingRuleViolationException;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class TobeMenuGroup {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @EmbeddedId
    private MenuGroupId id;

    @Column(name = "name", nullable = false)
    private MenuGroupName name;

    protected TobeMenuGroup() { }

    private TobeMenuGroup(MenuGroupId id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupId getId() {
        return id;
    }

    public MenuGroupName getName() {
        return name;
    }

    public static class MenuGroupBuilder {
        private MenuGroupId menuGroupId;
        private String name;
        private NamingRule namingRule;

        public MenuGroupBuilder() {
            this.menuGroupId = new MenuGroupId(UUID.randomUUID());
        }

        public MenuGroupBuilder name(String name) {
            this.name=name;
            return this;
        }

        public MenuGroupBuilder menuGroupName(String name) {
            this.name = name;
            return this;
        }

        public MenuGroupBuilder namingRule(NamingRule namingRule) {
            this.namingRule = namingRule;
            return this;
        }

        public TobeMenuGroup build() {
            if (Objects.isNull(name) || Objects.isNull(namingRule)) {
                throw new MenuGroupNamingRuleViolationException();
            }
            namingRule.checkRule(name);
            return new TobeMenuGroup(this.menuGroupId, new  MenuGroupName(name));
        }
    }

}
