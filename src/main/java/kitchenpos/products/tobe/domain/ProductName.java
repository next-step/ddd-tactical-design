package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.infra.PurgomalumClient;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName from(String name, PurgomalumClient purgomalumClient) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("상품명은 필수로 입력해야 합니다.");
        }
        
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        }
        return new ProductName(name);
    }

    public String getName() {
        return name;
    }
}
