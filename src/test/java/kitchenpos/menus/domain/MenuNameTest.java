package kitchenpos.menus.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuNameTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Test
    @DisplayName("메뉴명을 생성한다.")
    void createMenuName() {
        // given
        String name = "상품 이름";

        // when
        MenuName displayedName = new MenuName(name, purgomalumClient);

        // then
        assertThat(displayedName).isEqualTo(new MenuName(name, purgomalumClient));
    }

    @ParameterizedTest
    @DisplayName("상품명이 존재하지 않거나, 비속어가 존재할 경우 Exception을 발생 시킨다.")
    @NullSource
    @ValueSource(strings = {"비속어", "욕설"})
    void createMenuName(String name) {
        // when
        // then
        assertThatThrownBy(() -> new MenuName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
