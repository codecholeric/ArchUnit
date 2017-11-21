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
package com.tngtech.archunit.lang.syntax;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.tngtech.archunit.base.DescribedIterable;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.base.Guava;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.lang.AbstractClassesTransformer;
import com.tngtech.archunit.lang.ClassesTransformer;

/**
 * A {@link ClassesTransformer} that simply returns the supplied collection of {@link JavaField}
 * (i.e. the identity transformation)
 */
public final class ClassesToFieldsTransformer extends AbstractClassesTransformer<JavaField> {
    private DescribedPredicate<JavaField> selected;

    public ClassesToFieldsTransformer() {
        this("fields", DescribedPredicate.<JavaField>alwaysTrue());
    }

    private ClassesToFieldsTransformer(String description, DescribedPredicate<JavaField> selected) {
        super(description);
        this.selected = selected;
    }

    @Override
    public Iterable<JavaField> doTransform(JavaClasses collection) {
        ImmutableSet.Builder<JavaField> result = ImmutableSet.builder();
        for (JavaClass javaClass : collection) {
            result.addAll(Guava.Iterables.filter(javaClass.getFields(), selected));
        }
        return DescribedIterable.From.iterable(result.build(), getDescription());
    }

    @Override
    public ClassesTransformer<JavaField> that(DescribedPredicate<? super JavaField> predicate) {
        String newDescription = Joiner.on(" that ").join(getDescription(), predicate.getDescription());
        return new ClassesToFieldsTransformer(newDescription, predicate.<JavaField>forSubType());
    }

    @Override
    public ClassesTransformer<JavaField> as(String description) {
        return new ClassesToFieldsTransformer(description, selected);
    }
}
