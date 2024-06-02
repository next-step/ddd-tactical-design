package kitchenpos.menus.domain.tobe.menugroup;

import jakarta.persistence.Embeddable;
import kitchenpos.products.domain.tobe.ProfanityValidator;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Embeddable
public class MenuGroupName {
  private String name;

  protected MenuGroupName() {
  }

  private MenuGroupName(String name) {
    validate(name);
    this.name = name;
  }

  public static MenuGroupName of(String name){
    return new MenuGroupName(name);
  }

  private void validate(String name){
    if(Objects.isNull(name)){
      throw new IllegalArgumentException("- 메뉴 그룹의 이름은 비워 둘 수 없다.");
    }
    if(ObjectUtils.isEmpty(name)){
      throw new IllegalArgumentException("메뉴 그룹의 이름은 비워 둘 수 없다.");

    }
  }

  public String getName() {
    return name;
  }
}
