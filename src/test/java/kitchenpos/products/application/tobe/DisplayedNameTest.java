package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.vo.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DisplayedNameTest {
    @DisplayName("상품의 이름에는 비속어가 포함될 수 없다")
    @Test
    void setWrongNameTest() throws Exception {
        assertThatThrownBy(()->new DisplayedName("wrong-name"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
