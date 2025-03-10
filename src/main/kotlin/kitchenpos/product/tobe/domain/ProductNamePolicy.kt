package kitchenpos.product.tobe.domain

interface ProductNamePolicy {
    fun validate(name: String)
}
