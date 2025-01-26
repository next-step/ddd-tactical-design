package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    public ProductName(final String name, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("이름이 존재하지 않습니다.");
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("이름에 비속어가 들어가 있습니다.");
        }

        this.name = name;
    }

    public ProductName() {

    }

    public ProductName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
