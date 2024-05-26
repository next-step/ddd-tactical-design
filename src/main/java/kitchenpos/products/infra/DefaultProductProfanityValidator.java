package kitchenpos.products.infra;

import kitchenpos.products.tobe.domain.ProductProfanityValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultProductProfanityValidator implements ProductProfanityValidator {

  private final PurgomalumClient purgomalumClient;

  public DefaultProductProfanityValidator(PurgomalumClient purgomalumClient) {
    this.purgomalumClient = purgomalumClient;
  }

  @Override
  public void validate(String name) {
    if (purgomalumClient.containsProfanity(name)) {
      throw new IllegalArgumentException();
    }
  }
}
