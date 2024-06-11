package kitchenpos.products.tobe.domain;

import org.springframework.stereotype.Component;

@Component
public interface ProfanitiesChecker {
    boolean containsProfanity(String text);
}
