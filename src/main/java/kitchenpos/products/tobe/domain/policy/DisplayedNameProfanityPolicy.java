package kitchenpos.products.tobe.domain.policy;

import kitchenpos.products.exception.DisplayedNameException;
import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.profanity.ProfanityClient;
import org.springframework.stereotype.Component;

@Component
public class DisplayedNameProfanityPolicy implements DisplayedNamePolicy {

    private final ProfanityClient profanityClient;

    public DisplayedNameProfanityPolicy(ProfanityClient profanityClient) {
        this.profanityClient = profanityClient;
    }

    public void validate(String text) {
        if (isNullAndEmpty(text)) {
            throw new DisplayedNameException(ProductErrorCode.NAME_IS_NULL_OR_EMPTY);
        }

        if (profanityClient.containsProfanity(text)) {
            throw new DisplayedNameException(ProductErrorCode.NAME_HAS_PROFANITY);
        }
    }

    private boolean isNullAndEmpty(String name) {
        return name == null || name.isBlank();
    }

}
