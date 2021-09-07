package kitchenpos.products.tobe.domain.model;

import javax.persistence.Embeddable;
import kitchenpos.products.tobe.domain.service.ProductDomainService;

@Embeddable
public class DisplayedName {

    private String name;

    private DisplayedName() {
    }

    public DisplayedName(final String name, ProductDomainService productDomainService) {
        productDomainService.validateDisplayedName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
