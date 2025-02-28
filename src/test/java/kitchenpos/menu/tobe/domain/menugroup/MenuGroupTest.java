package kitchenpos.menu.tobe.domain.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupTest {

    @Test
    @DisplayName("정적 팩토리 메서드를 통해 MenuGroup 객체를 생성한다")
    void createMenuGroupUsingFactoryMethod() {
        // given
        String groupName = "한식";

        // when
        MenuGroup menuGroup = MenuGroup.of(groupName);

        // then
        assertAll(
                () -> assertThat(menuGroup.getId()).isNotNull(),
                () -> assertThat(menuGroup.getNameValue()).isEqualTo(groupName)
        );
    }

    @Test
    @DisplayName("getName 메서드는 MenuGroupName 객체를 반환한다")
    void getNameReturnsMenuGroupNameObject() {
        // given
        String groupName = "중식";
        MenuGroup menuGroup = MenuGroup.of(groupName);

        // when
        MenuGroupName name = menuGroup.getName();

        // then
        assertThat(name).isInstanceOf(MenuGroupName.class);
        assertThat(name.getValue()).isEqualTo(groupName);
    }

    @Test
    @DisplayName("getNameValue 메서드는 이름 문자열 값을 반환한다")
    void getNameValueReturnsStringValue() {
        // given
        String groupName = "일식";
        MenuGroup menuGroup = MenuGroup.of(groupName);

        // when
        String nameValue = menuGroup.getNameValue();

        // then
        assertThat(nameValue).isEqualTo(groupName);
    }

    @Test
    @DisplayName("동일한 ID를 가진 MenuGroup 객체는 equals 비교에서 true를 반환한다")
    void equalMenuGroups() throws Exception {
        // given
        // private 생성자에 접근하기 위해 리플렉션 사용
        Constructor<MenuGroup> constructor = MenuGroup.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        MenuGroup group1 = constructor.newInstance();
        MenuGroup group2 = constructor.newInstance();

        // id 필드에 접근하기 위해 리플렉션 사용
        Field idField = MenuGroup.class.getDeclaredField("id");
        idField.setAccessible(true);

        UUID sharedId = UUID.randomUUID();
        idField.set(group1, sharedId);
        idField.set(group2, sharedId);

        // then
        assertThat(group1).isEqualTo(group2);
        assertThat(group1.hashCode()).isEqualTo(group2.hashCode());
    }

    @Test
    @DisplayName("다른 ID를 가진 MenuGroup 객체는 equals 비교에서 false를 반환한다")
    void notEqualMenuGroups() {
        // given
        MenuGroup group1 = MenuGroup.of("동일한 이름");
        MenuGroup group2 = MenuGroup.of("동일한 이름");

        // then
        assertThat(group1).isNotEqualTo(group2);
    }
}