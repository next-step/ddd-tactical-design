package kitchenpos.menus.tobe.domain;

public class MenuName {

    private PurgomalumClient purgomalumClient;
    private String name;

    public MenuName(PurgomalumClient purgomalumClient, String name) {
        this.purgomalumClient = purgomalumClient;
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }
}
