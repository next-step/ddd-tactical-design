package kitchenpos.common.name;

import kitchenpos.common.profanity.domain.ProfanityDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <ul>
 *   <li>이름은 비어있지 않아야 한다.</li>
 *   <li>이름은 비속어를 포함하지 않아야 한다.</li>
 * </p>
 */
@Service
public class NameFactory {

    private final ProfanityDetectService profanityDetectService;

    @Autowired
    public NameFactory(ProfanityDetectService profanityDetectService) {
        this.profanityDetectService = profanityDetectService;
    }

    public Name create(final String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Name은 비어있을 수 없습니다");
        }
        if (this.profanityDetectService.profanityIn(name)) {
            throw new IllegalArgumentException("Name은 비속어를 포함하지 않아야 합니다.");
        }
        return new Name(name);
    }
}
