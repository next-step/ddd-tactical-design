package kitchenpos.menu.domain.service;

public interface MenuGroupCreatePolicy {

    String validateGroupName(String name, MenuPurgomalumClient purgomalumClient);
}
