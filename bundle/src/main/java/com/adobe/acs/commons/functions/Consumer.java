/*
 * Copyright 2016 Adobe.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adobe.acs.commons.functions;

import aQute.bnd.annotation.ConsumerType;

/**
 * Created work-alike for functionality not introduced until Java 8
 * Represents an operation that accepts a single input argument and returns no
 * result. Unlike most other functional interfaces, {@code Consumer} is expected
 * to operate via side-effects.
 *
 * @param <T> the type of the input to the operation
 * @deprecated Use CheckedConsumer instead
 */
@ConsumerType
@Deprecated
public abstract class Consumer<T> implements CheckedConsumer<T> {
    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    public Consumer<T> andThen(final Consumer<? super T> after) {
        return adapt(andThen((CheckedConsumer) after));
    }

    public static <X> Consumer<X> adapt(CheckedConsumer<X> delegate) {
        return new Adapter<>(delegate);
    }

    private static class Adapter<T> extends Consumer<T> {

        private final CheckedConsumer<T> delegate;

        public Adapter(CheckedConsumer<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public void accept(T t) throws Exception {
            delegate.accept(t);
        }
    }
}