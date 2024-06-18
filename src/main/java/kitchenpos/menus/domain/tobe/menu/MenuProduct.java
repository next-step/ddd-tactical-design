package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.*;
import kitchenpos.common.domain.Price;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

  private static final int ZERO = 0;
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  private Long seq;
  @Column(name = "productId", nullable = false)
  private UUID productId;
  @Column(name = "price", nullable = false)
  @Embedded
  private Price price;
  @Column(name = "quantity", nullable = false)
  private Long quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", nullable = false, columnDefinition = "binary(16)")
  private Menu menu;

  protected MenuProduct() {
  }
  private MenuProduct(UUID productId, Price price, Long quantity) {
    validate(quantity);

    this.productId = productId;
    this.price = price;
    this.quantity = quantity;
  }
  private void validate(Long quantity){
    if (quantity <= ZERO) {
      throw new IllegalArgumentException("`메뉴`에 등록된 `상품 개수`는 1개 이상이어야 한다.");
    }
  }
  public static MenuProduct of(UUID productId, Long price, Long quantity){
    return new MenuProduct(productId, Price.from(price), quantity);
  }

  public static MenuProduct of(UUID productId, BigDecimal price, Long quantity){
    return new MenuProduct(productId, Price.from(price), quantity);
  }
  public BigDecimal amount(){
    return price.multiply(BigDecimal.valueOf(quantity));
  }

  protected void mapping(final Menu menu){
    this.menu = menu;
  }
  public UUID getId(){
    return productId;
  }

  public void changePrice(Long price){
    this.price = Price.from(price);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MenuProduct that = (MenuProduct) o;
    return Objects.equals(seq, that.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }
}
