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

import java.lang.annotation.Annotation;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaModifier;

public interface GivenFieldsThat extends FieldsThat<GivenFieldsConjunction> {
    GivenFieldsConjunction areAnnotatedWith(Class<? extends Annotation> annotationType);

    GivenFieldsConjunction areNotAnnotatedWith(Class<? extends Annotation> annotationType);

    GivenFieldsConjunction areAnnotatedWith(String annotationTypeName);

    GivenFieldsConjunction areNotAnnotatedWith(String annotationTypeName);

    GivenFieldsConjunction areAnnotatedWith(DescribedPredicate<? super JavaAnnotation> predicate);

    GivenFieldsConjunction areNotAnnotatedWith(DescribedPredicate<? super JavaAnnotation> predicate);

    GivenFieldsConjunction haveNameMatching(String regex);

    GivenFieldsConjunction haveNameNotMatching(String regex);

    GivenFieldsConjunction arePublic();

    GivenFieldsConjunction areNotPublic();

    GivenFieldsConjunction areProtected();

    GivenFieldsConjunction areNotProtected();

    GivenFieldsConjunction arePackagePrivate();

    GivenFieldsConjunction areNotPackagePrivate();

    GivenFieldsConjunction arePrivate();

    GivenFieldsConjunction areNotPrivate();

    GivenFieldsConjunction haveModifier(JavaModifier modifier);

    GivenFieldsConjunction dontHaveModifier(JavaModifier modifier);
}
