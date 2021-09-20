package kitchenpos.menus.tobe.domain.model;

import javax.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.service.MenuDomainService;
import kitchenpos.products.tobe.domain.service.ProductDomainService;

@Embeddable
public class DisplayedName {

    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(final String name, MenuDomainService menuDomainService) {
        menuDomainService.validateDisplayedName(name);
        this.name = name;
    }

    protected String getName() {
        return name;
    }
}
