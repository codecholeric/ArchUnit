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

import com.tngtech.archunit.base.Function;
import com.tngtech.archunit.base.Function.Functions;
import com.tngtech.archunit.base.Optional;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ClassesTransformer;
import com.tngtech.archunit.lang.Priority;
import com.tngtech.archunit.lang.syntax.elements.ClassesShould;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;
import com.tngtech.archunit.lang.syntax.elements.FieldsShould;
import com.tngtech.archunit.lang.syntax.elements.FieldsShouldConjunction;
import com.tngtech.archunit.lang.syntax.elements.GivenClasses;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesConjunction;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesThat;
import com.tngtech.archunit.lang.syntax.elements.GivenFields;
import com.tngtech.archunit.lang.syntax.elements.GivenFieldsConjunction;
import com.tngtech.archunit.lang.syntax.elements.GivenFieldsThat;

class GivenFieldsInternal extends AbstractGivenObjects<JavaField, GivenFieldsInternal>
        implements GivenFields, GivenFieldsConjunction {

    GivenFieldsInternal(Priority priority, ClassesTransformer<JavaField> classesTransformer) {
        this(priority, classesTransformer, Functions.<ArchCondition<JavaField>>identity());
    }

    GivenFieldsInternal(Priority priority, ClassesTransformer<JavaField> classesTransformer,
                        Function<ArchCondition<JavaField>, ArchCondition<JavaField>> prepareCondition) {
        this(priority, classesTransformer, prepareCondition, new PredicateAggregator<JavaField>(), Optional.<String>absent());
    }

    private GivenFieldsInternal(
            Priority priority,
            ClassesTransformer<JavaField> classesTransformer,
            Function<ArchCondition<JavaField>, ArchCondition<JavaField>> prepareCondition,
            PredicateAggregator<JavaField> relevantObjectsPredicates,
            Optional<String> overriddenDescription) {

        super(new GivenFieldsFactory(),
                priority, classesTransformer, prepareCondition, relevantObjectsPredicates, overriddenDescription);

    }

    @Override
    public FieldsShould should() {
        return new FieldsShouldInternal(finishedClassesTransformer(), priority, prepareCondition);
    }

    @Override
    public GivenFieldsThat and() {
        return new GivenFieldsThatInternal(this, currentPredicate().thatANDs());
    }

    @Override
    public GivenFieldsThat or() {
        return new GivenFieldsThatInternal(this, currentPredicate().thatORs());
    }

    @Override
    public GivenFieldsThat that() {
        return new GivenFieldsThatInternal(this, currentPredicate());
    }

    @Override
    public FieldsShouldConjunction should(ArchCondition<JavaField> condition) {
        return new FieldsShouldInternal(finishedClassesTransformer(), priority, condition, prepareCondition);
    }

    private static class GivenFieldsFactory implements Factory<JavaField, GivenFieldsInternal> {
        @Override
        public GivenFieldsInternal create(Priority priority,
                                          ClassesTransformer<JavaField> classesTransformer,
                                          Function<ArchCondition<JavaField>, ArchCondition<JavaField>> prepareCondition,
                                          PredicateAggregator<JavaField> relevantObjectsPredicates,
                                          Optional<String> overriddenDescription) {
            return new GivenFieldsInternal(
                    priority, classesTransformer, prepareCondition, relevantObjectsPredicates, overriddenDescription);
        }
    }
}
