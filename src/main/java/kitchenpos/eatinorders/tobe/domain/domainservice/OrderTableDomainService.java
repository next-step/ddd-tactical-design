package kitchenpos.eatinorders.tobe.domain.domainservice;

public interface OrderTableDomainService {

  boolean isEmptyOrderTable(Long orderTableId);

  boolean hasInCompletedOrders(Long orderTableId);

  void emptyTable(Long orderTableId);
}
