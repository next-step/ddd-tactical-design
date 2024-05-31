package kitchenpos.tobe.menu.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import kitchenpos.tobe.menu.domain.MenuPurgomalumClient
import kitchenpos.tobe.product.exception.menu.MenuNameException

@Embeddable
class MenuName private constructor(
    @Column(name = "name", nullable = false)
    val name: String,
) {
    companion object {
        fun of(
            name: String,
            menuPurgomalumClient: MenuPurgomalumClient,
        ): MenuName {
            require(menuPurgomalumClient.containsProfanity(name).not()) {
                throw MenuNameException("메뉴의 이름에 욕설이 포함되어 있습니다.")
            }
            return MenuName(name)
        }
    }
}
