package kitchenpos.common.domain;

import java.util.Objects;
import kitchenpos.common.application.PurgomalumClient;
import org.springframework.stereotype.Service;

@Service
public class NameCreationService {
    private static final String NAME_VALIDATION_EXCEPTION = "이름에 비속어가 존재합니다. 비속어를 제외해주세요!";
    private final PurgomalumClient purgomalumClient;

    public NameCreationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public Name createName(String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(NAME_VALIDATION_EXCEPTION);
        }
        return new Name(name);
    }
}
