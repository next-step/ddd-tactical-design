package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.vo.Profanities;
import org.springframework.stereotype.Component;

@Component
public class DefaultProfanities implements Profanities {

    @Override
    public boolean containsProfanity(final String name) {
        return false;
    }
}
