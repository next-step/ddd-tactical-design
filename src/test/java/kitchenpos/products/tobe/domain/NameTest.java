package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.tobe.infra.ProfanitiesImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NameTest {
    @Test
    @DisplayName("욕설")
    void profanities() {
        // 한국욕은 안된다.
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Name("fuck", new ProfanitiesImpl(new DefaultPurgomalumClient(new RestTemplateBuilder()))));
    }

    @Test
    @DisplayName("생성")
    void create() {
        assertDoesNotThrow(() -> new Name("이름", new ProfanitiesImpl(new DefaultPurgomalumClient(new RestTemplateBuilder()))));
    }

    @Test
    @DisplayName("동등성")
    void equalsTest() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        DefaultPurgomalumClient purgomalumClient = new DefaultPurgomalumClient(restTemplateBuilder);
        ProfanitiesImpl profanities = new ProfanitiesImpl(purgomalumClient);

        Name name1 = new Name("이름", profanities);
        Name name2 = new Name("이름", new ProfanitiesImpl(purgomalumClient));
        assertThat(name1).isEqualTo(name2);
    }
}
