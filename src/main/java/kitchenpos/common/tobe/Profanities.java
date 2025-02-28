package kitchenpos.common.tobe;

import org.springframework.stereotype.Component;

@Component
public interface Profanities {
    boolean contains(String text);
}
