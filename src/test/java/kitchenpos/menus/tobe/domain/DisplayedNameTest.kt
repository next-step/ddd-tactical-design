package kitchenpos.menus.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.shouldBe
import kitchenpos.menus.tobe.infra.FakePurgomalumClient

private val profanityWordValidator = ProfanityWordValidator(FakePurgomalumClient("후라이드", "양념"))

class DisplayedNameTest : ExpectSpec({
    expect("금지어가 포함된 이름을 생성하면 예외가 발생한다") {
        val target = "후라이드 양념치킨"

        shouldThrow<IllegalArgumentException> {
            MenuName(target, profanityWordValidator)
        }
    }

    expect("금지어가 포함되지 않은 이름을 생성하면 이름이 생성된다") {
        val target = "간장치킨"

        val actual = MenuName(target, profanityWordValidator)

        actual.value shouldBe target
    }
})
