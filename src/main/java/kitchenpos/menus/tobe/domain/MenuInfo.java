package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class MenuInfo {
    @Embedded
    private MenuName menuName;

    @Embedded
    private MenuPrice menuPrice;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    protected MenuInfo() {}

    public MenuInfo(final MenuName menuName, final MenuPrice menuPrice, final boolean displayed) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.displayed = displayed;
    }

    public String getName() {
        return menuName.getName();
    }

    public MenuPrice getMenuPrice() {
        return menuPrice;
    }

    public boolean isDisplayed() {
        return displayed;
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
