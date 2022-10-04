package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.core.domain.Name;

@Entity
@Table(name = "dt_menu_group")
public class MenuGroup {

	@Id
	@Column(name = "id", columnDefinition = "binary(16)", nullable = false)
	private UUID id;

	@Embedded
	private Name name;

	protected MenuGroup() {
	}

	public MenuGroup(Name name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name.getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MenuGroup menuGroup = (MenuGroup)o;
		return Objects.equals(getId(), menuGroup.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
