package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.UbiquitousLanguages;
import kitchenpos.core.domain.Name;

@Entity
@Table(name = "dt_order_table")
public class OrderTable {

	@Id
	@Column(name = "id", columnDefinition = "binary(16)")
	private UUID id;

	@Embedded
	private Name name;

	@Column(name = "number_of_guests", nullable = false)
	private int numberOfGuests;

	@Column(name = "occupied", nullable = false)
	private boolean occupied;

	protected OrderTable() {
	}

	public OrderTable(Name name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}

	public boolean isUnoccupied() {
		return numberOfGuests == 0 && !occupied;
	}

	public void clear() {
		occupied = false;
		numberOfGuests = 0;
	}

	public void sit(int numberOfGuests) {
		occupied = true;
		changeNumberOfGuests(numberOfGuests);
	}

	private void changeNumberOfGuests(int numberOfGuests) {
		validateNumberOfGuests(numberOfGuests);
		this.numberOfGuests = numberOfGuests;
	}

	private void validateNumberOfGuests(int numberOfGuests) {
		if (!isOccupied()) {
			throw new IllegalStateException(String.format(ExceptionMessages.NEGATIVE_QUANTITY_TEMPLATE, UbiquitousLanguages.NUMBER_OF_GUESTS));
		}

		if (numberOfGuests < 0) {
			occupied = false;
			throw new IllegalArgumentException(String.format(ExceptionMessages.NEGATIVE_QUANTITY_TEMPLATE, UbiquitousLanguages.NUMBER_OF_GUESTS));
		}
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name.getValue();
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public boolean isOccupied() {
		return occupied;
	}
}
