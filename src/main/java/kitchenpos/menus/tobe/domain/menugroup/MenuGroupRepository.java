package kitchenpos.menus.tobe.domain.menugroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.products.tobe.domain.Product;


public interface MenuGroupRepository {

  MenuGroup save(Product product);

  List<MenuGroup> findAll();

}
