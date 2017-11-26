package com.tngtech.archunit.lang.syntax.elements;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.tngtech.archunit.core.domain.TestUtils.importClassesWithContext;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.testutil.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;

public class GivenFieldsThatTest {

    @Test
    public void haveNameMatching() throws NoSuchFieldException {
        List<JavaField> fields = filterResultOf(classes().that().haveFullyQualifiedName(String.class.getName())
                .should().haveFields().haveNameMatching("value"))
                .on(String.class, List.class);

        assertThat(getOnlyElement(fields)).isEquivalentTo(String.class.getDeclaredField("value"));
    }

    @Test
    public void haveNameNotMatching() throws NoSuchFieldException {
        List<JavaField> fields = filterResultOf(classes().that().haveFullyQualifiedName(String.class.getName())
                .should().haveFields().haveNameNotMatching(".+"))
                .on(String.class, List.class);

        assertThat(fields).isEmpty();
    }

    private Evaluator filterResultOf(GivenFieldsConjunction givenClasses) {
        return new Evaluator(givenClasses);
    }

    private class Evaluator {
        private final GivenFieldsConjunction givenFields;

        Evaluator(GivenFieldsConjunction givenFields) {
            this.givenFields = givenFields;
        }

        public List<JavaField> on(Class<?>... toCheck) {
            JavaClasses classes = importClassesWithContext(toCheck);
            ArchCondition<JavaField> condition = spy(new ArchCondition<JavaField>("ignored") {
                @Override
                public void check(JavaField item, ConditionEvents events) {
                }
            });
            givenFields.should(condition).check(classes);

            ArgumentCaptor<JavaField> fieldsCaptor = ArgumentCaptor.forClass(JavaField.class);
            verify(condition, atLeast(0)).check(fieldsCaptor.capture(), any(ConditionEvents.class));
            return fieldsCaptor.getAllValues();
        }
    }
}