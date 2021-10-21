package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.domain.Menu;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuForm {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean displayed;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public static MenuForm of(Menu menu) {
        MenuForm menuForm = new MenuForm();
        menuForm.setId(menu.getId());
        menuForm.setName(menu.getName());
        menuForm.setPrice(menu.getMenuPrice());
        menuForm.setDisplayed(menu.isDisplayed());
        return menuForm;
    }
}
