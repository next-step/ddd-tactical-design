package kitchenpos.menu.domain.service;

public interface MenuCreatePolicy {

    String validateMenuName(String name, MenuPurgomalumClient purgomalumClient);
}
