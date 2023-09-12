package kitchenpos.menus.tobe.domain;

public interface MenuPricePolicy {

    boolean isNotPermit(MenuPrice price, Menu menu);

}
