package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;


    public ProductName(String name, PurgomalumClient purgomalumClient) {
        checkNameContainsProfanity(name, purgomalumClient);
        this.name = name;
    }

    protected ProductName() {
    }

    public String getName() {
        return name;
    }

    private void checkNameContainsProfanity(String name, PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품명에 비속어를 사용할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductName)) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}