package kitchenpos.menus.domain;

import kitchenpos.menus.application.FakeMenuPurgomalumClient;
import kitchenpos.menus.tobe.domain.CleanName;
import kitchenpos.menus.tobe.domain.MenuNameValidationService;
import kitchenpos.products.infra.PurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("메뉴 도메인 이름 테스트")
public class CleanNameTest {
    private MenuNameValidationService menuNameValidationService;

    @BeforeEach
    void setUp() {
        PurgomalumClient purgomalumClient = new FakeMenuPurgomalumClient();
        menuNameValidationService = new MenuNameValidationService(purgomalumClient);
    }


    @Test
    @DisplayName("이름을 생성한다.")
    void create() {
        String 이름 = "후라이드";
        CleanName 메뉴_그룹_이름 = new CleanName(이름);

        Assertions.assertThat(메뉴_그룹_이름.getName()).isEqualTo(이름);
    }

    @NullAndEmptySource
    @ParameterizedTest
    @DisplayName("이름은 null이거나 공백일 수 없다.")
    void create_exception_empty_name(String 이름) {
        Assertions.assertThatThrownBy(
                () -> new CleanName(이름)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름에는 비속어나 욕설이 포함되면 안된다.")
    void create_exception_validation() {
        Assertions.assertThatThrownBy(
                () -> new CleanName("욕설", menuNameValidationService)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
