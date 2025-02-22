package kitchenpos;

import kitchenpos.menu.adapter.out.client.ProductClient;
import kitchenpos.shared.domain.Profanities;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockBeanConfiguration {
    @Bean
    @Primary
    Profanities profanities() {
        return Mockito.mock(Profanities.class);
    }

    @Bean
    @Primary
    ProductClient productClient() {
        return Mockito.mock(ProductClient.class);
    }
}
