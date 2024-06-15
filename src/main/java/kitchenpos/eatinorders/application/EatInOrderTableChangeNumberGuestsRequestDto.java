package kitchenpos.eatinorders.application;

public class EatInOrderTableChangeNumberGuestsRequestDto {
  private int numberOfGuests;

  public EatInOrderTableChangeNumberGuestsRequestDto() {}

  public EatInOrderTableChangeNumberGuestsRequestDto(int numberOfGuests) {
    this.numberOfGuests = numberOfGuests;
  }

  public int getNumberOfGuests() {
    return numberOfGuests;
  }
}
