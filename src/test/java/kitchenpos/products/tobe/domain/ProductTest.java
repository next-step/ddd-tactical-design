package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Objects;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("상품 테스트")
@SpringBootTest
class ProductTest {

    @Autowired
    private PurgomalumClient purgomalumClient;


    @Test
    void objectEmptyTest() {

        assertThat(purgomalumClient.containsProfanity("")).isFalse();
        assertThat(purgomalumClient.containsProfanity(null)).isFalse();

//        assertAll(
//                () -> assertThat(Objects.isNull("")).isFalse(),
//                () -> assertThat(Objects.isNull(null)).isTrue()
//
//        );

    }

}
