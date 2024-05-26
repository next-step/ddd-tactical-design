package kitchenpos.products.tobe.port

interface SlangChecker {
    fun containSlang(
        text: String,
    ): Boolean
}
