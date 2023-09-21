package kitchenpos.menus.domain.tobe;


import kitchenpos.common.infra.PurgomalumClient;

import java.util.List;

class FakeProfanities implements PurgomalumClient {
    private final List<String> values = List.of("욕설", "비속어");

    @Override
    public boolean containsProfanity(String text) {
        values.stream().anyMatch(text::contains);
        return false;
    }
}
