package com.tngtech.archunit.lang.syntax.elements;

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMember;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;

public interface GivenMembers<MEMBER extends JavaMember, SELF extends GivenMembers<MEMBER, SELF>> extends GivenObjects<MEMBER> {
    @Override
    @PublicAPI(usage = ACCESS)
    GivenMembersConjunction<MEMBER, ?> that(DescribedPredicate<? super MEMBER> predicate);
}
