package kitchenpos.menus.dto;

public class CreateRequest {

  private final String name;

  public CreateRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
