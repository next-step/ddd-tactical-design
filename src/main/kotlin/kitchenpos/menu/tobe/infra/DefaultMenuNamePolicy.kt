package kitchenpos.menu.tobe.infra

import kitchenpos.common.annotation.DomainService
import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.domain.MenuNamePolicy

@DomainService
class DefaultMenuNamePolicy(private val profanities: Profanities) : MenuNamePolicy {
    override fun validate(name: String) {
        require(name.isNotBlank()) { "메뉴 이름은 필수값입니다." }
        require(!profanities.contains(name)) { "메뉴 이름에 금지어가 포함되어 있습니다." }
    }
}
