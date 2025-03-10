package kitchenpos.menu.tobe.domain

interface MenuNamePolicy {
    fun validate(name: String)
}
