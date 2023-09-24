package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String productName;

    public ProductName(String productName, PurgomalumClient purgomalumClient) {
        validateProductName(productName, purgomalumClient);
        this.productName = productName;
    }

    protected ProductName() {

    }

    public String getProductName() {
        return productName;
    }

    private void validateProductName(String productName, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(productName)) {
            throw new IllegalArgumentException("상품 이름 값이 존재하지 않습니다.");
        }
        if (purgomalumClient.containsProfanity(productName)) {
            throw new IllegalArgumentException("상품 이름에 비속어가 존재합니다. product Name : " + productName);
        }
    }

}
