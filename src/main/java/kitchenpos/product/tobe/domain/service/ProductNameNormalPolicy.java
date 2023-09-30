package kitchenpos.product.tobe.domain.service;

import kitchenpos.common.profanity.ProfanityClient;
import kitchenpos.product.tobe.domain.ProductName;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class ProductNameNormalPolicy implements ProductNamePolicy {
    private final ProfanityClient profanityClient;

    public ProductNameNormalPolicy(ProfanityClient profanityClient) {
        this.profanityClient = profanityClient;
    }

    @Override
    public void validate(ProductName productName) {
        String value = productName.getValue();
        if (Strings.isBlank(value) || profanityClient.containsProfanity(value)) {
            throw new IllegalArgumentException("상품 이름은 null 또는 공백을 허용하지 않습니다.");
        }
    }
}
