package kitchenpos.menus.tobe.domain.vo;

import org.springframework.stereotype.Component;

@Component
public class DefaultProfanities implements Profanities {

    @Override
    public boolean containsProfanity(final String name) {
        return false;
    }
}
