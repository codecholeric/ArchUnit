package com.tngtech.archunit.lang.syntax;

import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.lang.syntax.elements.GivenCodeUnits;

class GivenCodeUnitsInternal<MEMBER extends JavaCodeUnit, SELF extends GivenCodeUnits<MEMBER, SELF>>
        extends GivenMembersInternal<MEMBER, SELF>
        implements GivenCodeUnits<MEMBER, SELF> {
}
