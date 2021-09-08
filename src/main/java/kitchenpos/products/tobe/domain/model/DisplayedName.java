package kitchenpos.products.tobe.domain.model;

import javax.persistence.Embeddable;
import kitchenpos.products.tobe.domain.service.ProductDomainService;

@Embeddable
public class DisplayedName {

    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(final String name, ProductDomainService productDomainService) {
        productDomainService.validateDisplayedName(name);
        this.name = name;
    }

    protected String getName() {
        return name;
    }
}
