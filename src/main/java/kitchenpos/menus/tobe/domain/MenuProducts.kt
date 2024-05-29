package kitchenpos.menus.tobe.domain

class MenuProducts(val items: List<MenuProduct>) {
    init {
        require(items.isNotEmpty()) { "적어도 1개 이상의 상품을 등록해야합니다" }
    }
}
