package kitchenpos.eatinorders.application;

public class EatInOrderTableCreateRequestDto {
  private String name;

  public EatInOrderTableCreateRequestDto() {}

  public EatInOrderTableCreateRequestDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
