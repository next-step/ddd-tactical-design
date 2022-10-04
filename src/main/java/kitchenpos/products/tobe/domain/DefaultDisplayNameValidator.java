package kitchenpos.products.tobe.domain;

import java.util.Objects;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultDisplayNameValidator implements DisplayNameValidator {

  private final PurgomalumClient purgomalumClient;

  public DefaultDisplayNameValidator(PurgomalumClient purgomalumClient) {
    this.purgomalumClient = purgomalumClient;
  }

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
