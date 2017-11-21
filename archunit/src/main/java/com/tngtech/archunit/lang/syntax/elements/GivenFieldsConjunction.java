/*
 * Copyright 2017 TNG Technology Consulting GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tngtech.archunit.lang.syntax.elements;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.lang.ArchCondition;

public interface GivenFieldsConjunction extends GivenConjunction<JavaField> {
    @Override
    @PublicAPI(usage = ACCESS)
    FieldsShouldConjunction should(ArchCondition<JavaField> condition);

    @PublicAPI(usage = ACCESS)
    FieldsShould should();

    @Override
    @PublicAPI(usage = ACCESS)
    GivenFieldsConjunction and(DescribedPredicate<? super JavaField> predicate);

    /**
     * @see #and(DescribedPredicate)
     */
    @PublicAPI(usage = ACCESS)
    GivenFieldsThat and();

    @Override
    @PublicAPI(usage = ACCESS)
    GivenFieldsConjunction or(DescribedPredicate<? super JavaField> predicate);

    /**
     * @see #or(DescribedPredicate)
     */
    @PublicAPI(usage = ACCESS)
    GivenFieldsThat or();
}
