package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.shared.domain.Price;
import kitchenpos.shared.domain.Profanities;

import java.util.List;
import java.util.UUID;

@Table(name = "tobe_menu")
@Entity
public class TobeMenu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private TobeMenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected TobeMenu() {
    }

    public TobeMenu(String name, int price, boolean displayed, UUID menuGroupId, List<TobeMenuProduct> tobeMenuProducts, Profanities profanities) {
        this.id = UUID.randomUUID();
        this.name = DisplayedName.of(name, profanities);
        this.price = Price.of(price);
        this.displayed = displayed;
        this.menuProducts = TobeMenuProducts.of(tobeMenuProducts);
        this.menuGroupId = menuGroupId;
        validatePrice();
    }

    public void changePrice(int price) {
        this.price = Price.of(price);
        validatePrice();
    }

    public void display() {
        validatePrice();
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    private void validatePrice() {
        int menuProductsTotalPrice = menuProducts.getTotalPrice();
        if (price.isGreaterThan(menuProductsTotalPrice)) {
            throw new IllegalStateException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public int getPrice() {
        return price.getPrice();
    }
}
