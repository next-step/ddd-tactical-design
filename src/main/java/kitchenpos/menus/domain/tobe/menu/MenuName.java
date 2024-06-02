package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.Embeddable;
import kitchenpos.products.domain.tobe.ProfanityValidator;

import java.util.Objects;

@Embeddable
public class MenuName {
  private String name;

  protected MenuName() {
  }

  private MenuName(String name, ProfanityValidator profanityValidator) {
    validate(name, profanityValidator);
    this.name = name;
  }

  public static MenuName of(String name, ProfanityValidator profanityValidator){
    return new MenuName(name, profanityValidator);
  }

  private void validate(String name, ProfanityValidator profanityValidator){
    if(Objects.isNull(name)){
      throw new IllegalArgumentException("메뉴의 이름이 올바르지 않으면 등록할 수 없다.");
    }

    if (profanityValidator.containsProfanity(name)){
      throw new IllegalArgumentException("메뉴의 이름에는 비속어가 포함될 수 없다.");
    }
  }

  public String getName() {
    return name;
  }
}
