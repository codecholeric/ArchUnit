package com.tngtech.archunit.lang.syntax.elements;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import org.junit.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.codeUnits;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.members;

public class GivenMembersTest {
    @Test
    public void test_members() {
        DescribedPredicate<JavaMember> predicate = new DescribedPredicate<JavaMember>("") {
            @Override
            public boolean apply(JavaMember input) {
                return false;
            }
        };
        ArchCondition<JavaMember> archCondition = new ArchCondition<JavaMember>("") {
            @Override
            public void check(JavaMember item, ConditionEvents events) {

            }
        };
        members().that(predicate).or(predicate).should(archCondition);
    }

    @Test
    public void test_fields() {
        DescribedPredicate<JavaField> predicate = new DescribedPredicate<JavaField>("") {
            @Override
            public boolean apply(JavaField input) {
                return false;
            }
        };
        ArchCondition<JavaField> archCondition = new ArchCondition<JavaField>("") {
            @Override
            public void check(JavaField item, ConditionEvents events) {

            }
        };
        fields().that(predicate).or(predicate).should(archCondition);
    }

    @Test
    public void test_codeunits() {
        DescribedPredicate<JavaCodeUnit> predicate = new DescribedPredicate<JavaCodeUnit>("") {
            @Override
            public boolean apply(JavaCodeUnit input) {
                return false;
            }
        };
        ArchCondition<JavaCodeUnit> archCondition = new ArchCondition<JavaCodeUnit>("") {
            @Override
            public void check(JavaCodeUnit item, ConditionEvents events) {

            }
        };
        codeUnits().that(predicate).or(predicate).should(archCondition);
    }
}
