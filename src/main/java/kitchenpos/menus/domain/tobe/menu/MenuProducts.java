package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import kitchenpos.menus.domain.MenuProduct;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class MenuProducts {
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "menu")
  private List<MenuProduct> values = new ArrayList<>();


}
