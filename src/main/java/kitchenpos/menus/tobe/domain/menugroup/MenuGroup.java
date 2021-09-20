package kitchenpos.menus.tobe.domain.menugroup;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu_group")
@Entity(name = "TobeMenuGroup")
public class MenuGroup {
	@Id
	@Column(name = "id", columnDefinition = "varbinary(16)")
	private UUID id;
	@Embedded
	private Name name;

	protected MenuGroup() {

	}

	public MenuGroup(Name name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MenuGroup menuGroup = (MenuGroup)o;
		return id.equals(menuGroup.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public UUID getId() {
		return id;
	}

	public Name getName() {
		return name;
	}
}
