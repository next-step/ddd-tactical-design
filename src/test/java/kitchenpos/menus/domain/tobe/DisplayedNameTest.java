package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DisplayedNameTest {
    @Test
    void constructor() {
        final Profanities profanities = new FakeProfanities();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new DisplayedName("욕설", profanities));
        assertThatNoException()
                .isThrownBy(() -> new DisplayedName("치킨", profanities));
    }

    @Test
    void equals() {
        final Profanities profanities = new FakeProfanities();
        final DisplayedName name1 = new DisplayedName("치킨", profanities);
        final DisplayedName name2 = new DisplayedName("치킨", profanities);
        assertThat(name1).isEqualTo(name2);
    }
}