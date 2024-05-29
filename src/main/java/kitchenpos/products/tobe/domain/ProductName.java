package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private final String value;

    protected ProductName() {
        this.value = new String();
    }
    public ProductName(String value){
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
    public ProductName(String value, PurgomalumClient purgomalumClient){
        if (Objects.isNull(value)| purgomalumClient.containsProfanity(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


}
