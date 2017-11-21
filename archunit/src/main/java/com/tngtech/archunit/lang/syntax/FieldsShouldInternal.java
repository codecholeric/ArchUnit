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
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ClassesTransformer;
import com.tngtech.archunit.lang.Priority;
import com.tngtech.archunit.lang.syntax.elements.FieldsShould;
import com.tngtech.archunit.lang.syntax.elements.FieldsShouldConjunction;

class FieldsShouldInternal extends ObjectsShouldInternal<JavaField>
        implements FieldsShould, FieldsShouldConjunction {

    FieldsShouldInternal(ClassesTransformer<JavaField> classesTransformer,
                         Priority priority,
                         Function<ArchCondition<JavaField>, ArchCondition<JavaField>> prepareCondition) {
        super(classesTransformer, priority, prepareCondition);
    }

    FieldsShouldInternal(ClassesTransformer<JavaField> classesTransformer,
                         Priority priority,
                         ArchCondition<JavaField> condition,
                         Function<ArchCondition<JavaField>, ArchCondition<JavaField>> prepareCondition) {
        super(classesTransformer, priority, condition, prepareCondition);
    }

    private FieldsShouldInternal(ClassesTransformer<JavaField> classesTransformer,
                                 Priority priority,
                                 ConditionAggregator<JavaField> conditionAggregator,
                                 Function<ArchCondition<JavaField>, ArchCondition<JavaField>> prepareCondition) {
        super(classesTransformer, priority, conditionAggregator, prepareCondition);
    }

    FieldsShouldInternal copyWithNewCondition(ArchCondition<JavaField> newCondition) {
        return new FieldsShouldInternal(classesTransformer, priority, newCondition, prepareCondition);
    }

    FieldsShouldInternal addCondition(ArchCondition<JavaField> condition) {
        return copyWithNewCondition(conditionAggregator.add(condition));
    }

    @Override
    public FieldsShouldConjunction andShould(ArchCondition<? super JavaField> condition) {
        return copyWithNewCondition(conditionAggregator
                .thatANDsWith(ObjectsShouldInternal.<JavaField>prependDescription("should"))
                .add(condition));
    }

    @Override
    public FieldsShould andShould() {
        return new FieldsShouldInternal(
                classesTransformer,
                priority,
                conditionAggregator.thatANDsWith(ObjectsShouldInternal.<JavaField>prependDescription("should")),
                prepareCondition);
    }

    @Override
    public FieldsShouldConjunction orShould(ArchCondition<? super JavaField> condition) {
        return copyWithNewCondition(conditionAggregator
                .thatORsWith(ObjectsShouldInternal.<JavaField>prependDescription("should"))
                .add(condition));
    }

    @Override
    public FieldsShould orShould() {
        return new FieldsShouldInternal(
                classesTransformer,
                priority,
                conditionAggregator.thatORsWith(ObjectsShouldInternal.<JavaField>prependDescription("should")),
                prepareCondition);
    }
}
