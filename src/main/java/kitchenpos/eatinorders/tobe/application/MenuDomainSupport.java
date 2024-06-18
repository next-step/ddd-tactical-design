package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItem;

import java.util.UUID;

/**
 * 메뉴 도메인을 지원하는 인터페이스
 */
public interface MenuDomainSupport {
    EatInOrderLineItem requestOrderLineItem(UUID menuId, long quantity);
}
