package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.infra.Profanities;
import org.springframework.stereotype.Component;

@Component
public class DisplayNamePolicy {
    private final Profanities profanities;

    public DisplayNamePolicy(Profanities profanities) {
        this.profanities = profanities;
    }

    public boolean checkPolicy(final String name) {
        return profanities.containsProfanity(name);
    }


}
