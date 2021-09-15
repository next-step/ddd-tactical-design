package kitchenpos.commons.tobe.domain.service;

@FunctionalInterface
public interface Validator<T> {

    void validate(T t);
}
