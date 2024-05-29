package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuDisplayed {
    @Column(name = "displayed", nullable = false)
    private boolean displayed;
}
