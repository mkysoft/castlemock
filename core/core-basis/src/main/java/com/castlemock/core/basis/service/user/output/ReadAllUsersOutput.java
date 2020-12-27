/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.core.basis.service.user.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.basis.service.user.input.ReadAllUsersInput;

import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see ReadAllUsersInput
 */
public final class ReadAllUsersOutput implements Output {

    @NotNull
    private final List<User> users;

    public ReadAllUsersOutput(final Builder builder) {
        this.users = Objects.requireNonNull(builder.users);
    }

    public List<User> getUsers() {
        return users;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<User> users;

        private Builder() {
        }

        public Builder users(List<User> users) {
            this.users = users;
            return this;
        }

        public ReadAllUsersOutput build() {
            return new ReadAllUsersOutput(this);
        }
    }
}
