package kitchenpos.takeoutorders.domain;

public enum TakeoutOrderStatus {
    WAITING {
        @Override
        public TakeoutOrderStatus next() {
            return ACCEPTED;
        }
    },
    ACCEPTED {
        @Override
        public TakeoutOrderStatus next() {
            return SERVED;
        }
    },
    SERVED {
        @Override
        public TakeoutOrderStatus next() {
            return COMPLETED;
        }
    },
    COMPLETED {
        @Override
        public TakeoutOrderStatus next() {
            throw new IllegalStateException();
        }
    };

    public abstract TakeoutOrderStatus next();
}
