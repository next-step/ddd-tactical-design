package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.tobe.infra.ProfanitiesImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @Test
    @DisplayName("생성")
    void create() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        DefaultPurgomalumClient purgomalumClient = new DefaultPurgomalumClient(restTemplateBuilder);
        ProfanitiesImpl profanities = new ProfanitiesImpl(purgomalumClient);

        UUID id = UUID.randomUUID();
        Name name = new Name("이름", profanities);
        Price price = new Price(BigDecimal.valueOf(100));

        assertDoesNotThrow(() -> new Product(id, name, price));
    }

    @Test
    @DisplayName("가격 수정")
    void changePrice() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        DefaultPurgomalumClient purgomalumClient = new DefaultPurgomalumClient(restTemplateBuilder);
        ProfanitiesImpl profanities = new ProfanitiesImpl(purgomalumClient);

        UUID id = UUID.randomUUID();
        Name name = new Name("이름", profanities);
        Price price = new Price(BigDecimal.valueOf(100));

        Product product = new Product(id, name, price);

        assertDoesNotThrow(() -> product.changePrice(new Price(BigDecimal.valueOf(200))));

    }

    @Test
    @DisplayName("동등성")
    void equalsTest() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        DefaultPurgomalumClient purgomalumClient = new DefaultPurgomalumClient(restTemplateBuilder);
        ProfanitiesImpl profanities = new ProfanitiesImpl(purgomalumClient);

        UUID id = UUID.randomUUID();
        Name name = new Name("이름", profanities);
        Price price = new Price(BigDecimal.valueOf(100));

        Product product1 = new Product(id, name, price);
        Product product2 = new Product(id, name, price);
        assertThat(product1).isEqualTo(product2);
    }
}
