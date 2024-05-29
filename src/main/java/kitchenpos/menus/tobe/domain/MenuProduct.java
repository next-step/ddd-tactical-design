package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.products.tobe.domain.Product;

import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", nullable = false)
    private UUID id;

    private int price;

    private int quantity;

    public MenuProduct(UUID id, int price, int quantity) {
        checkQuantity(quantity);
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    protected MenuProduct() {

    }

    public int getAmount(){
        return price * quantity;
    }

    private void checkQuantity(int quantity){
        if(quantity < 0){
            throw new IllegalArgumentException();
        }
    }
}
