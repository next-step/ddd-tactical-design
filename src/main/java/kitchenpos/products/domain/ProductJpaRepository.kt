package kitchenpos.products.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductJpaRepository : JpaRepository<ProductRecord, UUID>
