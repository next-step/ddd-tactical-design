package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class MenuDisplayNameValidator implements DisplayNameValidator {

  private final ProfanityClient profanityClient;

  public MenuDisplayNameValidator(ProfanityClient profanityClient) {
    this.profanityClient = profanityClient;
  }

  @Override
  public void validate(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException();
    }

    if (profanityClient.containsProfanity(name)) {
      throw new IllegalArgumentException();
    }
  }
}
