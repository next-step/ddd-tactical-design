package kitchenpos.eatinorders.ordertable.tobe.domain;

public class CleanUpPolicyFixture {

    public static final CleanUpPolicy PASS = orderTableId -> true;
    public static final CleanUpPolicy FAIL = orderTableId -> false;
}
