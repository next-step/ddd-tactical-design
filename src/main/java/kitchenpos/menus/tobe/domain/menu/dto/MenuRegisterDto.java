package kitchenpos.menus.tobe.domain.menu.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MenuRegisterDto {
    private Long id;
    private BigDecimal price;
    private String name;
    private Long menuGroupId;
    private List<MenuProductDto> menuProducts = new ArrayList<>();

    public MenuRegisterDto (){
    }

    public MenuRegisterDto (Builder builder){
        this.id = builder.id;
        this.price = builder.price;
        this.name = builder.name;
        this.menuGroupId = builder.menuGroupId;
        this.menuProducts = builder.menuProducts;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }

    public static class Builder {

        private Long id;
        private BigDecimal price;
        private String name;
        private Long menuGroupId;
        private List<MenuProductDto> menuProducts;

        public Builder id (Long id){
            this.id = id;
            return this;
        }

        public Builder price(BigDecimal price){
            this.price = price;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder menuGroupId (Long menuGroupId){
            this.menuGroupId = menuGroupId;
            return this;
        }

        public Builder menuProducts (List<MenuProductDto> menuProducts){
            this.menuProducts = new ArrayList<>(menuProducts);
            return this;
        }

        public MenuRegisterDto build (){
            return new MenuRegisterDto(this);
        }

    }
}
