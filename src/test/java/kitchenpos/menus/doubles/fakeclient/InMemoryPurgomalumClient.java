package kitchenpos.menus.doubles.fakeclient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import kitchenpos.menus.tobe.domain.PurgomalumClient;

public class InMemoryPurgomalumClient implements PurgomalumClient {

    private final Set<String> names = new HashSet<>(Arrays.asList("비속어", "욕설"));

    @Override
    public boolean containsProfanity(String name) {
        return names.contains(name);
    }
}
