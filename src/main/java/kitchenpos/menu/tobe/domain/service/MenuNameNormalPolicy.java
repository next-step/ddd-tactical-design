package kitchenpos.menu.tobe.domain.service;

import java.util.Objects;
import kitchenpos.common.profanity.ProfanityClient;
import kitchenpos.menu.tobe.domain.MenuName;
import org.springframework.stereotype.Service;

@Service
public class MenuNameNormalPolicy implements MenuNamePolicy {

    private final ProfanityClient profanityClient;

    public MenuNameNormalPolicy(ProfanityClient profanityClient) {
        this.profanityClient = profanityClient;
    }

    @Override
    public void validate(MenuName menuName) {
        String value = menuName.getValue();
        if (Objects.isNull(value) || profanityClient.containsProfanity(value)) {
            throw new IllegalArgumentException();
        }
    }
}
