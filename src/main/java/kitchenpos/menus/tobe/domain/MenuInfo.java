package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.UUID;

@Embeddable
public class MenuInfo {
    @Embedded
    private MenuName menuName;

    @Embedded
    private MenuPrice menuPrice;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Column(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            nullable = false
    )
    private UUID menuGroupId;

    protected MenuInfo() {}

    public MenuInfo(final MenuName menuName, final MenuPrice menuPrice, final boolean displayed, final UUID menuGroupId) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.displayed = displayed;
        this.menuGroupId = menuGroupId;
    }

    public String getName() {
        return menuName.getName();
    }

    MenuPrice getMenuPrice() {
        return menuPrice;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void changePrice(final MenuPrice menuPrice) {
        this.menuPrice = menuPrice;
    }

    void display() {
        this.displayed = true;
    }

    void hide() {
        this.displayed = false;
    }
}
