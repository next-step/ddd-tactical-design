package kitchenpos.products.tobe.domain;

import java.util.List;

public class FakeProductProfanityValidator implements ProductProfanityValidator {
  private static final List<String> profanities = List.of("비속어", "욕설");
  @Override
  public void validate(String name) {
    boolean contains = profanities.stream()
        .anyMatch(name::contains);
    if(contains) {
      throw new IllegalArgumentException();
    }
  }
}
