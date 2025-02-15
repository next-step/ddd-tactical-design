package kitchenpos.products.ui.response

import kitchenpos.products.domain.Product

data class ProductResponse(
    val id: String,
    val name: String,
    val price: String,
) {
    companion object {
        @JvmStatic
        fun of(product: Product): ProductResponse {
           return ProductResponse(
               id = product.id.toString(),
               name = product.getName(),
               price = product.getPrice().toString()
           )
        }
    }
}
