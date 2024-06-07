package kitchenpos.menus.tobe.domain.service;

import kitchenpos.products.infra.PurgomalumClient;

public class MenuNameValidationService {
    private PurgomalumClient purgomalumClient;

    public MenuNameValidationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public boolean containsProfanity(String name) {
        return purgomalumClient.containsProfanity(name);
    }
}
