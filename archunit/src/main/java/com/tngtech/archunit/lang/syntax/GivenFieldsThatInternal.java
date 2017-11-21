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

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.have;

import java.lang.annotation.Annotation;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.syntax.elements.GivenFieldsConjunction;
import com.tngtech.archunit.lang.syntax.elements.GivenFieldsThat;

class GivenFieldsThatInternal implements GivenFieldsThat {
    private final GivenFieldsInternal givenClasses;
    private final PredicateAggregator<JavaField> currentPredicate;

    GivenFieldsThatInternal(GivenFieldsInternal givenClasses, PredicateAggregator<JavaField> predicate) {
        this.givenClasses = givenClasses;
        this.currentPredicate = predicate;
    }

    @Override
    public GivenFieldsConjunction areAnnotatedWith(Class<? extends Annotation> annotationType) {
        return givenWith(are(annotatedWith(annotationType)));
    }

    @Override
    public GivenFieldsConjunction areNotAnnotatedWith(Class<? extends Annotation> annotationType) {
        return givenWith(are(not(annotatedWith(annotationType))));
    }

    @Override
    public GivenFieldsConjunction areAnnotatedWith(String annotationTypeName) {
        return givenWith(are(annotatedWith(annotationTypeName)));
    }

    @Override
    public GivenFieldsConjunction areNotAnnotatedWith(String annotationTypeName) {
        return givenWith(are(not(annotatedWith(annotationTypeName))));
    }

    @Override
    public GivenFieldsConjunction areAnnotatedWith(DescribedPredicate<? super JavaAnnotation> predicate) {
        return givenWith(are(annotatedWith(predicate)));
    }

    @Override
    public GivenFieldsConjunction areNotAnnotatedWith(DescribedPredicate<? super JavaAnnotation> predicate) {
        return givenWith(are(not(annotatedWith(predicate))));
    }

    @Override
    public GivenFieldsConjunction haveNameMatching(String regex) {
        return givenWith(have(nameMatching(regex)));
    }

    @Override
    public GivenFieldsConjunction haveNameNotMatching(String regex) {
        return givenWith(ClassesThatPredicates.haveNameNotMatching(regex));
    }

    @Override
    public GivenFieldsConjunction arePublic() {
        return givenWith(ClassesThatPredicates.arePublic());
    }

    @Override
    public GivenFieldsConjunction areNotPublic() {
        return givenWith(ClassesThatPredicates.areNotPublic());
    }

    @Override
    public GivenFieldsConjunction areProtected() {
        return givenWith(ClassesThatPredicates.areProtected());
    }

    @Override
    public GivenFieldsConjunction areNotProtected() {
        return givenWith(ClassesThatPredicates.areNotProtected());
    }

    @Override
    public GivenFieldsConjunction arePackagePrivate() {
        return givenWith(ClassesThatPredicates.arePackagePrivate());
    }

    @Override
    public GivenFieldsConjunction areNotPackagePrivate() {
        return givenWith(ClassesThatPredicates.areNotPackagePrivate());
    }

    @Override
    public GivenFieldsConjunction arePrivate() {
        return givenWith(ClassesThatPredicates.arePrivate());
    }

    @Override
    public GivenFieldsConjunction areNotPrivate() {
        return givenWith(ClassesThatPredicates.areNotPrivate());
    }


    @Override
    public GivenFieldsConjunction haveModifier(JavaModifier modifier) {
        return givenWith(ClassesThatPredicates.haveModifier(modifier));
    }

    @Override
    public GivenFieldsConjunction dontHaveModifier(JavaModifier modifier) {
        return givenWith(ClassesThatPredicates.dontHaveModifier(modifier));
    }

    private GivenFieldsInternal givenWith(DescribedPredicate<? super JavaField> predicate) {
        return givenClasses.with(currentPredicate.add(predicate));
    }
}
