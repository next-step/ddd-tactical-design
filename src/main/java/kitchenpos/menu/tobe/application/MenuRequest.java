package kitchenpos.menu.tobe.application;

import kitchenpos.menu.tobe.domain.menu.MenuProduct;

import java.util.List;
import java.util.UUID;

/**
 * 메뉴 생성 및 수정을 위한 요청 객체
 * 서비스 레이어에서 클라이언트의 요청을 도메인 객체로 변환하는 데 사용됨
 */
public class MenuRequest {
    private String name;
    private Long price;
    private UUID menuGroupId;
    private List<MenuProduct> menuProducts;
    private boolean displayed;

    // 기본 생성자
    public MenuRequest() {
    }

    // 필수 필드만 포함한 생성자
    public MenuRequest(String name, Long price, UUID menuGroupId, List<MenuProduct> menuProducts, boolean displayed) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    // 모든 필드에 대한 getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}