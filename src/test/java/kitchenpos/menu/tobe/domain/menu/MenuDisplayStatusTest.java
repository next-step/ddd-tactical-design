package kitchenpos.menu.tobe.domain.menu;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class MenuDisplayStatusTest {

    @Test
    @DisplayName("기본 생성시 지정된 표시 상태값을 가진다")
    void createWithInitialStatus() {
        // given
        // true와 false 두 가지 초기 상태를 모두 테스트합니다
        MenuDisplayStatus displayedStatus = new MenuDisplayStatus(true);
        MenuDisplayStatus hiddenStatus = new MenuDisplayStatus(false);

        // when & then
        // 각각의 상태가 생성자에서 지정한 값과 일치하는지 확인합니다
        assertThat(displayedStatus.isDisplayed()).isTrue();
        assertThat(hiddenStatus.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("동일한 표시 상태를 가진 객체는 equals 비교에서 동등하다")
    void testEquals() {
        // given
        // 같은 상태값을 가진 두 객체를 생성합니다
        MenuDisplayStatus status1 = new MenuDisplayStatus(true);
        MenuDisplayStatus status2 = new MenuDisplayStatus(true);
        MenuDisplayStatus status3 = new MenuDisplayStatus(false);

        // when & then
        // 같은 상태를 가진 객체들은 동등하고, 다른 상태를 가진 객체들은 동등하지 않아야 합니다
        assertThat(status1)
                .isEqualTo(status2)
                .hasSameHashCodeAs(status2)
                .isNotEqualTo(status3);
    }

    @Test
    @DisplayName("MenuDisplayStatus 객체들이 Set에서 올바르게 동작한다")
    void testSetBehavior() {
        // given
        // 서로 다른 상태값을 가진 객체들을 생성합니다
        MenuDisplayStatus displayed1 = new MenuDisplayStatus(true);
        MenuDisplayStatus displayed2 = new MenuDisplayStatus(true);
        MenuDisplayStatus hidden = new MenuDisplayStatus(false);

        Set<MenuDisplayStatus> statuses = new HashSet<>();

        // when
        // Set에 객체들을 추가합니다. 같은 상태의 객체는 중복으로 처리되어야 합니다
        statuses.add(displayed1);
        statuses.add(displayed2);  // displayed1과 동일한 상태이므로 중복으로 처리됨
        statuses.add(hidden);

        // then
        // Set은 서로 다른 상태의 객체만 포함해야 합니다
        assertThat(statuses)
                .hasSize(2)  // true와 false 두 가지 상태만 존재해야 함
                .contains(displayed1, hidden);
    }
}