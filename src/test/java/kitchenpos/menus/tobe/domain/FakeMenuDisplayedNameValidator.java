package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import kitchenpos.menus.tobe.infra.FakeProfanityClient;

public class FakeMenuDisplayedNameValidator implements DisplayNameValidator {

  private final ProfanityClient fakeProfanityClient = new FakeProfanityClient();

  @Override
  public void validate(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException();
    }

    if (fakeProfanityClient.containsProfanity(name)) {
      throw new IllegalArgumentException();
    }
  }
}
