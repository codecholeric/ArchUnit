package com.tngtech.archunit.lang.syntax.elements;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaCodeUnit;

public interface GivenCodeUnits<CODE_UNIT extends JavaCodeUnit, SELF extends GivenCodeUnits<CODE_UNIT, SELF>>
        extends GivenMembers<CODE_UNIT, SELF> {
    @Override
    GivenCodeUnitsConjunction<CODE_UNIT, ?> that(DescribedPredicate<? super CODE_UNIT> predicate);
}
