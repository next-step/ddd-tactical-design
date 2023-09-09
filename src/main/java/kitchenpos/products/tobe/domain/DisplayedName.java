package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_NAME_CONTAINS_PROFANITY;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String name;

    protected DisplayedName() {}

    private DisplayedName(String name) {
        this.name = name;
    }

    public static DisplayedName of(String name) {
        return new DisplayedName(name);
    }

    public static DisplayedName of(String name, PurgomalumClient purgomalumClient) {
        if (StringUtils.isEmpty(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(PRODUCT_NAME_CONTAINS_PROFANITY);
        }
        return new DisplayedName(name);
    }

    public String getName() {
        return name;
    }
}
