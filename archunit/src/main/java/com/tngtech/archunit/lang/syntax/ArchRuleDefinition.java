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

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.base.Function;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ClassesTransformer;
import com.tngtech.archunit.lang.Priority;
import com.tngtech.archunit.lang.syntax.elements.GivenClasses;
import com.tngtech.archunit.lang.syntax.elements.GivenCodeUnits;
import com.tngtech.archunit.lang.syntax.elements.GivenConstructors;
import com.tngtech.archunit.lang.syntax.elements.GivenFields;
import com.tngtech.archunit.lang.syntax.elements.GivenMembers;
import com.tngtech.archunit.lang.syntax.elements.GivenMethods;
import com.tngtech.archunit.lang.syntax.elements.GivenObjects;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;
import static com.tngtech.archunit.lang.Priority.MEDIUM;
import static com.tngtech.archunit.lang.conditions.ArchConditions.never;

public final class ArchRuleDefinition<T> {
    private ArchRuleDefinition() {
    }

    /**
     * @see Creator#all(ClassesTransformer)
     */
    @PublicAPI(usage = ACCESS)
    public static <TYPE> GivenObjects<TYPE> all(ClassesTransformer<TYPE> classesTransformer) {
        return priority(MEDIUM).all(classesTransformer);
    }

    /**
     * @see Creator#no(ClassesTransformer)
     */
    @PublicAPI(usage = ACCESS)
    public static <TYPE> GivenObjects<TYPE> no(ClassesTransformer<TYPE> classesTransformer) {
        return priority(MEDIUM).no(classesTransformer);
    }

    @PublicAPI(usage = ACCESS)
    public static Creator priority(Priority priority) {
        return new Creator(priority);
    }

    @PublicAPI(usage = ACCESS)
    public static GivenClasses classes() {
        return priority(MEDIUM).classes();
    }

    @PublicAPI(usage = ACCESS)
    public static GivenClasses noClasses() {
        return priority(MEDIUM).noClasses();
    }

    @PublicAPI(usage = ACCESS)
    public static GivenMembers<JavaMember, ?> members() {
        return priority(MEDIUM).members();
    }

    @PublicAPI(usage = ACCESS)
    public static GivenFields fields() {
        return priority(MEDIUM).fields();
    }

    @PublicAPI(usage = ACCESS)
    public static GivenCodeUnits<?, ?> codeUnits() {
        return priority(MEDIUM).codeUnits();
    }

    @PublicAPI(usage = ACCESS)
    public static GivenConstructors constructors() {
        return priority(MEDIUM).constructors();
    }

    @PublicAPI(usage = ACCESS)
    public static GivenMethods methods() {
        return priority(MEDIUM).methods();
    }

    public static final class Creator {
        private final Priority priority;

        private Creator(Priority priority) {
            this.priority = priority;
        }

        @PublicAPI(usage = ACCESS)
        public GivenClasses classes() {
            return new GivenClassesInternal(priority, ClassesIdentityTransformer.classes());
        }

        @PublicAPI(usage = ACCESS)
        public GivenClasses noClasses() {
            return new GivenClassesInternal(
                    priority,
                    ClassesIdentityTransformer.classes().as("no classes"),
                    ArchRuleDefinition.<JavaClass>negateCondition());
        }

        @PublicAPI(usage = ACCESS)
        public GivenMembers<JavaMember, ?> members() {
            return new GivenMembersInternal<>();
        }

        @PublicAPI(usage = ACCESS)
        public GivenFields fields() {
            return new GivenFieldsInternal();
        }

        @PublicAPI(usage = ACCESS)
        public GivenCodeUnits<?, ?> codeUnits() {
            return new GivenCodeUnitsInternal<>();
        }

        @PublicAPI(usage = ACCESS)
        public GivenConstructors constructors() {
            return new GivenConstructorsInternal();
        }

        @PublicAPI(usage = ACCESS)
        public GivenMethods methods() {
            return new GivenMethodsInternal();
        }

        /**
         * Takes a {@link ClassesTransformer} to specify how the set of objects of interest is to be created
         * from {@link JavaClasses} (which are the general input obtained from a {@link ClassFileImporter}).
         *
         * @param <TYPE>             The target type to which the later used {@link ArchCondition ArchCondition&lt;TYPE&gt;}
         *                           will have to refer to
         * @param classesTransformer Transformer specifying how the imported {@link JavaClasses} are to be transformed
         * @return {@link GivenObjects} to guide the creation of an {@link ArchRule}
         */
        @PublicAPI(usage = ACCESS)
        public <TYPE> GivenObjects<TYPE> all(ClassesTransformer<TYPE> classesTransformer) {
            return new GivenObjectsInternal<>(priority, classesTransformer);
        }

        /**
         * Same as {@link #all(ClassesTransformer)}, but negates the following condition.
         */
        @PublicAPI(usage = ACCESS)
        public <TYPE> GivenObjects<TYPE> no(ClassesTransformer<TYPE> classesTransformer) {
            return new GivenObjectsInternal<>(
                    priority,
                    classesTransformer.as("no " + classesTransformer.getDescription()),
                    ArchRuleDefinition.<TYPE>negateCondition());
        }
    }

    private static <T> Function<ArchCondition<T>, ArchCondition<T>> negateCondition() {
        return new Function<ArchCondition<T>, ArchCondition<T>>() {
            @Override
            public ArchCondition<T> apply(ArchCondition<T> condition) {
                return never(condition).as(condition.getDescription());
            }
        };
    }
}
