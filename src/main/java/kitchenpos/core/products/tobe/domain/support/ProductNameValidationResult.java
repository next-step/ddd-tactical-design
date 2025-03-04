package kitchenpos.core.products.tobe.domain.support;

import java.util.List;

public record ProductNameValidationResult(boolean valid, List<String> errorMessages) {}
