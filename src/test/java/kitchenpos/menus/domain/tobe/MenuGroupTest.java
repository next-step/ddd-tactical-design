package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class MenuGroupTest {
    @Test
    void constructor() {
        Assertions.assertDoesNotThrow(() ->  new MenuGroup(UUID.randomUUID(), "name"));
    }
}
