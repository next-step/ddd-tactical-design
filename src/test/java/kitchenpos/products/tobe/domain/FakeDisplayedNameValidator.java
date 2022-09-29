package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;

public class FakeDisplayedNameValidator implements DisplayNameValidator {

  private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

  @Override
  public void validate(DisplayedName displayedName) {
    if (purgomalumClient.containsProfanity(displayedName.getName())) {
      throw new IllegalArgumentException();
    }
  }
}
