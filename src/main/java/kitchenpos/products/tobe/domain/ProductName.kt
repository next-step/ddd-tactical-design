package kitchenpos.products.tobe.domain

private const val MIN_LENGTH = 1
private const val MAX_LENGTH = 255

@JvmInline
value class ProductName(val value: String) {
    init {
        require(value.length in MIN_LENGTH..MAX_LENGTH) {
            "DisplayedName 은 ${MIN_LENGTH}자 이상 ${MAX_LENGTH}자 이하여야 합니다."
        }
    }
}
