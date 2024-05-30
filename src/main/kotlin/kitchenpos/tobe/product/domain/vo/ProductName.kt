package kitchenpos.tobe.product.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import kitchenpos.tobe.product.domain.ProductPurgomalumClient
import kitchenpos.tobe.product.exception.ProductNameException

@Embeddable
class ProductName private constructor(
    @Column(name = "name", nullable = false)
    val name: String,
) {
    companion object {
        fun of(
            name: String,
            productPurgomalumClient: ProductPurgomalumClient,
        ): ProductName {
            validate(name, productPurgomalumClient)
            return ProductName(name)
        }

        private fun validate(
            name: String,
            productPurgomalumClient: ProductPurgomalumClient,
        ) {
            require(name.isNotBlank()) {
                throw ProductNameException("상품명은 공백이 될 수 없습니다.")
            }
            require(
                productPurgomalumClient.containsProfanity(name).not(),
            ) {
                throw ProductNameException("상품명에 욕설이 포함되어 있습니다.")
            }
        }
    }
}
