package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.common.application.PurgomalumClient;
import kitchenpos.common.infra.external.FakePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameCreationServiceTest {

    private NameCreationService nameCreationService;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
        nameCreationService = new NameCreationService(purgomalumClient);
    }

    @Test
    @DisplayName("이름에 비속어가 있으면 예외를 던집니다.")
    void validate_name_exception() {
        // given
        String name = "비속어";
        FakePurgomalumClient fakePurgomalumClient = (FakePurgomalumClient) purgomalumClient;
        fakePurgomalumClient.setProfanity(true);

        // when // then
        assertThatThrownBy(() -> nameCreationService.createName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름에 비속어가 존재합니다. 비속어를 제외해주세요!");
    }
}
