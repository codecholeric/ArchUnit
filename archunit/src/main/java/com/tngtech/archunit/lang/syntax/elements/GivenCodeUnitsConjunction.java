package com.tngtech.archunit.lang.syntax.elements;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaCodeUnit;

public interface GivenCodeUnitsConjunction<CODE_UNIT extends JavaCodeUnit, SELF extends GivenCodeUnitsConjunction<CODE_UNIT, SELF>>
        extends GivenMembersConjunction<CODE_UNIT, SELF> {
    @Override
    SELF and(DescribedPredicate<? super CODE_UNIT> predicate);

    @Override
    SELF or(DescribedPredicate<? super CODE_UNIT> predicate);
}
