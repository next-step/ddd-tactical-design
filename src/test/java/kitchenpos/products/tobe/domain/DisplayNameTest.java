package kitchenpos.products.tobe.domain;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.application.FakePurgomalumClient;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.infra.Profanities;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;


class DisplayNameTest {

    private Profanities profanities;
    private DisplayNamePolicy displayNamePolicy;

    @BeforeEach
    void setUp() {
        profanities = new FakePurgomalumClient();
        displayNamePolicy = new DisplayNamePolicy(profanities);
    }

    @DisplayName("비속어가 포함된 이름은 예외가 발생한다.")
    @Test
    void createDisplayName(){
        // given
        final String name = "비속어";

        // when then
        assertThatIllegalArgumentException().isThrownBy(() -> new kitchenpos.products.tobe.domain.DisplayName(name, displayNamePolicy));
    }

    @DisplayName("비속어가 포함되지 않은 이름은 생성된다.")
    @Test
    void createDisplayName2(){
        // given
        final String name = "후라이드";

        // when
        final kitchenpos.products.tobe.domain.DisplayName displayName = new kitchenpos.products.tobe.domain.DisplayName(name, displayNamePolicy);

        // then
        assertThat(displayName.getName()).isEqualTo(name);
    }
}
