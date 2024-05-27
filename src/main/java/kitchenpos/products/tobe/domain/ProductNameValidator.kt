package kitchenpos.products.tobe.domain

fun interface ProductNameValidator {
    fun validate(name: String): Boolean
}
