package kitchenpos.menus.tobe.domain.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    private Menu menu;

    private Long product_id;

    private long quantity;

    protected MenuProduct() {
    }

    public MenuProduct(long quantity) {
        this(null, quantity);
    }

    public MenuProduct(Long product_id, long quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public void addMenu(Menu menu) {
        this.menu = menu;
    }
}
