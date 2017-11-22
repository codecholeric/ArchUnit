package com.tngtech.archunit.lang.syntax.elements;

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaField;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;

public interface GivenFields extends GivenMembers<JavaField, GivenFields> {
    @Override
    @PublicAPI(usage = ACCESS)
    GivenFieldsConjunction that(DescribedPredicate<? super JavaField> predicate);
}
