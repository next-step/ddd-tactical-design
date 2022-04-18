package kitchenpos.fakeobject;

import java.util.Arrays;
import java.util.List;
import kitchenpos.products.domain.PurgomalumClient;

public class FakePurgomalumClient implements PurgomalumClient {

    private final List<String> names = Arrays.asList("욕설", "비속어");

    @Override
    public boolean containsProfanity(String name) {
        return names.contains(name);
    }
}
