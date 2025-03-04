package kitchenpos.core.products.tobe.domain;

import kitchenpos.config.UnitTest;
import kitchenpos.core.products.application.FakeProfanityChecker;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.core.products.tobe.domain.support.DefaultProductNamePolicy;
import kitchenpos.core.shared.domain.ProfanityChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@UnitTest
@DisplayName("[Product] ProductName 테스트")
class ProductNameTest {

    private final ProfanityChecker profanityChecker = new FakeProfanityChecker();
    private final ProductNamePolicy productNamePolicy = new DefaultProductNamePolicy(profanityChecker);

    @Test
    @DisplayName("성공: 유효한 이름으로 ProductName 생성")
    void createProductNameSuccess() {
        // given
        String rawName = "  Test Product  ";

        // when
        ProductName productName = ProductName.create(productNamePolicy, rawName);

        // then
        assertThat(productName.getName()).isEqualTo("Test Product");
    }

    @ParameterizedTest
    @DisplayName("실패: null이나 빈 문자열로 ProductName 생성시 InvalidProductNameException 발생")
    @NullAndEmptySource
    void createProductNameFail(String name) {

        // when & then
        assertThrows(InvalidProductNameException.class, () -> ProductName.create(productNamePolicy, name));
    }

    @ParameterizedTest
    @DisplayName("실패: 비속어가 포함된 ProductName 생성시 InvalidProductNameException 발생")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    void createProductNameFailWhenContainsProfanity(String name) {
        // when & then
        assertThrows(InvalidProductNameException.class, () -> ProductName.create(productNamePolicy, name));
    }
}