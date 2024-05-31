package kitchenpos.tobe.product.infra

import kitchenpos.tobe.product.domain.entity.ProductV2
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaProductRepositoryV2 : JpaRepository<ProductV2, UUID>, ProductRepositoryV2 {
    override fun findProductById(id: UUID): ProductV2?
}
