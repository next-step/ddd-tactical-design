package kitchenpos.menus.tobe.menu.application;

import java.math.BigDecimal;
import java.util.List;

public class MenuCreationRequestDto {
    private String name;
    private BigDecimal price;
    private Long menuGroupId;
    private List<ProductQuantityDto> productQuantityDtos;

    public MenuCreationRequestDto(final String name, final BigDecimal price, final Long menuGroupId, final List<ProductQuantityDto> productQuantityDtos) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.productQuantityDtos = productQuantityDtos;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final Long menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public List<ProductQuantityDto> getProductQuantityDtos() {
        return productQuantityDtos;
    }

    public void setProductQuantityDtos(final List<ProductQuantityDto> productQuantityDtos) {
        this.productQuantityDtos = productQuantityDtos;
    }
}
