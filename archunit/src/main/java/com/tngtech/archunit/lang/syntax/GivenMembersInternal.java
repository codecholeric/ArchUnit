package com.tngtech.archunit.lang.syntax;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.GivenConjunction;
import com.tngtech.archunit.lang.syntax.elements.GivenMembers;

class GivenMembersInternal<MEMBER extends JavaMember, SELF extends GivenMembers<MEMBER, SELF>> implements GivenMembers<MEMBER, SELF> {
    @Override
    public ArchRule should(ArchCondition<MEMBER> condition) {
        return null;
    }

    @Override
    public GivenConjunction<MEMBER> that(DescribedPredicate<? super MEMBER> predicate) {
        return null;
    }
}
