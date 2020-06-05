package kitchenpos.menus.model;

import java.math.BigDecimal;
import java.util.List;

public class MenuView {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long menuGroupId;
    private List<MenuProductView> menuProducts;

    private MenuView() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductView> getMenuProducts() {
        return menuProducts;
    }

    public static final class Builder {

        private Long id;
        private String name;
        private BigDecimal price;
        private Long menuGroupId;
        private List<MenuProductView> menuProducts;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withMenuGroupId(Long menuGroupId) {
            this.menuGroupId = menuGroupId;
            return this;
        }

        public Builder withMenuProducts(List<MenuProductView> menuProducts) {
            this.menuProducts = menuProducts;
            return this;
        }

        public MenuView build() {
            MenuView menuView = new MenuView();
            menuView.price = this.price;
            menuView.id = this.id;
            menuView.name = this.name;
            menuView.menuGroupId = this.menuGroupId;
            menuView.menuProducts = this.menuProducts;
            return menuView;
        }
    }

    public static class MenuProductView {

        private Long productId;
        private long quantity;

        public Long getProductId() {
            return productId;
        }

        public long getQuantity() {
            return quantity;
        }

        public static final class Builder {

            private Long productId;
            private long quantity;

            private Builder() {
            }

            public static Builder builder() {
                return new Builder();
            }

            public Builder withProductId(Long productId) {
                this.productId = productId;
                return this;
            }

            public Builder withQuantity(long quantity) {
                this.quantity = quantity;
                return this;
            }

            public MenuProductView build() {
                MenuProductView menuProductView = new MenuProductView();
                menuProductView.productId = this.productId;
                menuProductView.quantity = this.quantity;
                return menuProductView;
            }
        }
    }


}
