package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderId;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, EatInOrderId> {

}
