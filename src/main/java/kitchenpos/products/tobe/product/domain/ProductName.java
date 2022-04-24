package kitchenpos.products.tobe.product.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    private static final String PRODUCT_NAME_EMPTY_MESSAGE = "상품의 이름은 비어있을 수 없습니다.";
    private static final String PRODUCT_NAME_PROFANITY_MESSAGE = "상품의 이름은 비속어가 포함될 수 없습니다.";


    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() { /* empty */ }

    public ProductName(final String name, final PurgomalumClient purgomalumClient) {
        validate(name,purgomalumClient);
        this.name = name;
    }

    private void validate(final String name, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || Strings.isBlank(name)) {
            throw new IllegalArgumentException(PRODUCT_NAME_EMPTY_MESSAGE);
        }

        if(purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(PRODUCT_NAME_PROFANITY_MESSAGE);
        }
    }

    public String value() {
        return name;
    }
}
