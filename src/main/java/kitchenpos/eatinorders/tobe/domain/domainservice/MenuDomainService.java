package kitchenpos.eatinorders.tobe.domain.domainservice;

import java.util.Map;
import kitchenpos.eatinorders.tobe.domain.vo.MenuRes;

public interface MenuDomainService {

  Map<Long, MenuRes> findDisplayedMenus(Iterable<Long> ids);

}
