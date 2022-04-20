package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatCode;

class MenuTest {

    @DisplayName("이름, 가격, 메뉴 그룹 이름, 상품 목록으로 메뉴를 생성한다")
    @Test
    void create() {
        assertThatCode(() ->
                new Menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트", new ArrayList<>()))
                .doesNotThrowAnyException();
    }
}
