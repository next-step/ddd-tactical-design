package kitchenpos.products.tobe.port

interface ProductNameSlangChecker {
    fun containSlang(
        text: String,
    ): Boolean
}
