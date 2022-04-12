package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    protected ProductName(PurgomalumClient purgomalumClient, String name) {
        validation(purgomalumClient, name);

        this.name = name;
    }

    private void validation(PurgomalumClient purgomalumClient, String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품명은 비속어를 사용할수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
