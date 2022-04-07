package kitchenpos.menus.tobe.domain.menu;


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import kitchenpos.menus.tobe.domain.product.Product;
import kitchenpos.menus.tobe.domain.product.ProductRepository;

@Entity
@Table(name = "menu_product")
public class MenuProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @Embedded
  private MenuProductQuantity quantity;

  @Column(nullable = false)
  private Long productId;

  @Transient
  private ProductRepository productRepository;

  protected MenuProduct() {
  }

  public MenuProduct(Long productId, MenuProductQuantity quantity, ProductRepository productRepository) {
    this.productId = productId;
    this.quantity = quantity;
    this.productRepository = productRepository;
  }

  public BigDecimal calculateAmount() {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException(""));
    return quantity.multiplyPrice(product.getPrice());
  }

  public void connectRelation(Menu menu) {
    this.menu = menu;
  }
}
