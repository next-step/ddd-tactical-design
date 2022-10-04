package kitchenpos.products.tobe.domain;

import java.util.Objects;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;

public class FakeDisplayedNameValidator implements DisplayNameValidator {

  private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

  @Override
  public void validate(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException();
    }

    if (purgomalumClient.containsProfanity(name)) {
      throw new IllegalArgumentException();
    }
  }
}
