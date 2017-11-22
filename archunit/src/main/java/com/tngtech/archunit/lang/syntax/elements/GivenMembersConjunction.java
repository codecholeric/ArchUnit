package com.tngtech.archunit.lang.syntax.elements;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;

public interface GivenMembersConjunction<MEMBER extends JavaMember, SELF extends GivenMembersConjunction<MEMBER, SELF>>
        extends GivenConjunction<MEMBER> {
    @Override
    SELF and(DescribedPredicate<? super MEMBER> predicate);

    @Override
    SELF or(DescribedPredicate<? super MEMBER> predicate);
}
