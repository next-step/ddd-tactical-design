package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderStatusException;

public enum EatInOrderStatus {
    WAITING {
        @Override
        public EatInOrderStatus accept() {
            return ACCEPTED;
        }

        @Override
        public EatInOrderStatus serve() {
            throw new IllegalOrderStatusException();
        }

        @Override
        public EatInOrderStatus complete() {
            throw new IllegalOrderStatusException();
        }
    },
    ACCEPTED {
        @Override
        public EatInOrderStatus accept() {
            throw new IllegalOrderStatusException();
        }

        @Override
        public EatInOrderStatus serve() {
            return SERVED;
        }

        @Override
        public EatInOrderStatus complete() {
            throw new IllegalOrderStatusException();
        }
    },
    SERVED {
        @Override
        public EatInOrderStatus accept() {
            throw new IllegalOrderStatusException();
        }

        @Override
        public EatInOrderStatus serve() {
            throw new IllegalOrderStatusException();
        }

        @Override
        public EatInOrderStatus complete() {
            return COMPLETED;
        }
    },
    COMPLETED {
        @Override
        public EatInOrderStatus accept() {
            throw new IllegalOrderStatusException();
        }

        @Override
        public EatInOrderStatus serve() {
            throw new IllegalOrderStatusException();
        }

        @Override
        public EatInOrderStatus complete() {
            throw new IllegalOrderStatusException();
        }
    }
    ;

    public abstract EatInOrderStatus accept();
    public abstract EatInOrderStatus serve();
    public abstract EatInOrderStatus complete();
}
