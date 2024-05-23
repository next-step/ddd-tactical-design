package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() {

    }

    public ProductName(String value) {
        if(Objects.isNull(value)){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }


}
