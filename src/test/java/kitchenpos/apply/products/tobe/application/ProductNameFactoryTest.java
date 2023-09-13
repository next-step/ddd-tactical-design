package kitchenpos.apply.products.tobe.application;

import kitchenpos.support.domain.PurgomalumClient;
import kitchenpos.apply.products.tobe.infra.FakePurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductNameFactoryTest {
    private ProductNameFactory factory;

    @BeforeEach
    void setUp() {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        factory = new ProductNameFactory(purgomalumClient);
    }

    @Test
    public void createTest() {
        Assertions.assertThatThrownBy(() -> factory.createProductName(null))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없거나 비속어를 포함하면 안됩니다.");
    }
}