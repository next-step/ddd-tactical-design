package kitchenpos.products.tobe.fake;

import java.util.HashSet;
import java.util.Set;
import kitchenpos.products.domain.ProfanityValidator;
import org.springframework.util.ObjectUtils;

public class FakeProfanityValidator implements ProfanityValidator {
    private final Set<String> profanitys;

    public FakeProfanityValidator(Set<String> profanitys) {
        if (profanitys == null) {
            throw new IllegalArgumentException("비어 있을 수 없습니다.");
        }
        this.profanitys = profanitys;
    }

    private void add(String word) {
        if (ObjectUtils.isEmpty(word)) {
            throw new IllegalArgumentException("추가할 단어는 비어 있을수 없습니다.");
        }
        profanitys.add(word);
    }

    public static FakeProfanityValidator from(String profanity) {
        final FakeProfanityValidator profanitys = new FakeProfanityValidator(new HashSet<>());
        profanitys.add(profanity);
        return profanitys;
    }

    public boolean isHas(String word) {
        return profanitys.contains(word);
    }
}
