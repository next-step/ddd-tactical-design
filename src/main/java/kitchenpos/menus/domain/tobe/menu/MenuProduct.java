package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;
  @Column(name = "productId", nullable = false)
  private UUID productId;
  @Column(name = "price", nullable = false)
  private long price;
  @Column(name = "quantity", nullable = false)
  private long quantity;

  protected MenuProduct() {
  }

  public long amount(){
    return price * quantity;
  }

  public UUID getProductId(){
    return productId;
  }

  public void changePrice(int price){
    this.price = price;
  }
}
