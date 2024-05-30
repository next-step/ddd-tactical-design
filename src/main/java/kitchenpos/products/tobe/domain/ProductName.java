package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

@Embeddable
public class ProductName {

    private String name;

    protected ProductName() {
    }

    private ProductName(String name, PurgomalumClient purgomalumClient) {
        validateName(name, purgomalumClient);
        this.name = name;
    }

    public static ProductName of(String name, PurgomalumClient purgomalumClient) {
        return new ProductName(name, purgomalumClient);
    }

    public void validateName(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수값입니다.");
        }
        if (purgomalumClient.containsProfanity(name))
            throw new IllegalArgumentException("비속어가 포함된 상품명은 등록할 수 없습니다.");
    }


    public String name() {
        return name;
    }
}
