package kitchenpos.global.domain.vo;

import kitchenpos.fake.global.infrastructure.external.FakeBannedWordCheckClient;
import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DisplayNameTest {

    public final BannedWordCheckClient bannedWordCheckClient = new FakeBannedWordCheckClient();

    @Test
    @DisplayName("출력용 이름(display name)은 반드시 이름을 가져야 한다.")
    void create01() {
        assertThatThrownBy(() -> new DisplayedName(null, bannedWordCheckClient))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("출력용 이름(display name)은 금지어(banned word)를 포함할 수 없다.")
    void create02() {
        assertThatThrownBy(() -> new DisplayedName("존X 맛있는 미트파이", bannedWordCheckClient))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
