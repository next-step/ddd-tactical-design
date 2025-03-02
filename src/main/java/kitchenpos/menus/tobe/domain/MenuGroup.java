package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupNameException;

import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroup() {
    }

    public MenuGroup(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidMenuGroupNameException("메뉴 그룹명이 존재해야 합니다.");
        }
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
