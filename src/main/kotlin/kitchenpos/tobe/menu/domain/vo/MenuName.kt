package kitchenpos.tobe.menu.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import kitchenpos.tobe.menu.domain.MenuPurgomalumClient

@Embeddable
class MenuName private constructor(
    @Column(name = "name", nullable = false)
    val name: String,
) {
    companion object {
        fun from(
            name: String,
            menuPurgomalumClient: MenuPurgomalumClient,
        ): MenuName {
            require(menuPurgomalumClient.containsProfanity(name).not()) {
                throw IllegalArgumentException("메뉴의 이름에 욕설이 포함되어 있습니다.")
            }
            return MenuName(name)
        }
    }
}
