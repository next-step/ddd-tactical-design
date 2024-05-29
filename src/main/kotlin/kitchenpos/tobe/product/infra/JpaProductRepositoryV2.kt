package kitchenpos.tobe.product.infra

import kitchenpos.tobe.product.domain.entity.ProductV2
import kitchenpos.tobe.product.domain.repository.ProductRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaProductRepositoryV2 : JpaRepository<ProductV2, UUID>, ProductRepository {
    override fun findById(id: UUID): Optional<ProductV2>
}
