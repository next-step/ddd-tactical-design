package kitchenpos.common.application;

import org.springframework.stereotype.Component;

@Component
public interface PurgomalumClient {
    boolean containsProfanity(String text);
}
