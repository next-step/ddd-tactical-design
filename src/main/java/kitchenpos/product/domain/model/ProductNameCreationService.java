package kitchenpos.product.domain.model;

import kitchenpos.common.application.PurgomalumClient;
import org.springframework.stereotype.Service;

@Service
public class ProductNameCreationService {
    private static final String PRODUCT_NAME_VALIDATION_EXCEPTION = "상품 이름에 비속어가 존재합니다. 비속어를 제외해주세요!";
    private final PurgomalumClient purgomalumClient;

    public ProductNameCreationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public ProductName createName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(PRODUCT_NAME_VALIDATION_EXCEPTION);
        }
        return new ProductName(name);
    }
}
