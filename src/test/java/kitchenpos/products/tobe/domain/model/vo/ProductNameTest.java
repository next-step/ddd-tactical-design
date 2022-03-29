package kitchenpos.products.tobe.domain.model.vo;

import kitchenpos.fake.global.infrastructure.external.FakeBannedWordCheckClient;
import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.products.tobe.domain.model.vo.ProductName;
import kitchenpos.products.tobe.exception.IllegalProductNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductNameTest {

    public final BannedWordCheckClient bannedWordCheckClient = new FakeBannedWordCheckClient();

    @Test
    @DisplayName("상품(Product)은 반드시 이름을 가져야 한다.")
    void create01() {
        assertThatThrownBy(() -> new ProductName(null, bannedWordCheckClient))
                .isInstanceOf(IllegalProductNameException.class);
    }


    @Test
    @DisplayName("상품(Product)의 이름은 금지어(banned word)를 포함할 수 없다.")
    void create02() {
        assertThatThrownBy(() -> new ProductName("존X 맛있는 미트파이", bannedWordCheckClient))
                .isInstanceOf(IllegalProductNameException.class);
    }
}
