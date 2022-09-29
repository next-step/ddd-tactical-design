package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultDisplayNameValidator implements DisplayNameValidator {

  private final PurgomalumClient purgomalumClient;

  public DefaultDisplayNameValidator(PurgomalumClient purgomalumClient) {
    this.purgomalumClient = purgomalumClient;
  }

  @Override
  public void validate(DisplayedName displayedName) {
    if (purgomalumClient.containsProfanity(displayedName.getName())) {
      throw new IllegalArgumentException();
    }
  }
}
