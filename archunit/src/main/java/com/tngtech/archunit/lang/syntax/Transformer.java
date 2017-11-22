package com.tngtech.archunit.lang.syntax;

import com.google.common.collect.ImmutableSet;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.AbstractClassesTransformer;
import com.tngtech.archunit.lang.ClassesTransformer;

class Transformer {
    static ClassesTransformer<JavaMember> toMembers() {
        return new AbstractClassesTransformer<JavaMember>("members") {
            @Override
            public Iterable<JavaMember> doTransform(JavaClasses collection) {
                ImmutableSet.Builder<JavaMember> result = ImmutableSet.builder();
                for (JavaClass javaClass : collection) {
                    result.addAll(javaClass.getMembers());
                }
                return result.build();
            }
        };
    }

    static ClassesTransformer<JavaField> toFields() {
        return new AbstractClassesTransformer<JavaField>("fields") {
            @Override
            public Iterable<JavaField> doTransform(JavaClasses collection) {
                ImmutableSet.Builder<JavaField> result = ImmutableSet.builder();
                for (JavaClass javaClass : collection) {
                    result.addAll(javaClass.getFields());
                }
                return result.build();
            }
        };
    }

    static ClassesTransformer<JavaCodeUnit> toCodeUnits() {
        return new AbstractClassesTransformer<JavaCodeUnit>("code units") {
            @Override
            public Iterable<JavaCodeUnit> doTransform(JavaClasses collection) {
                ImmutableSet.Builder<JavaCodeUnit> result = ImmutableSet.builder();
                for (JavaClass javaClass : collection) {
                    result.addAll(javaClass.getCodeUnits());
                }
                return result.build();
            }
        };
    }

    static ClassesTransformer<JavaConstructor> toConstructors() {
        return new AbstractClassesTransformer<JavaConstructor>("constructors") {
            @Override
            public Iterable<JavaConstructor> doTransform(JavaClasses collection) {
                ImmutableSet.Builder<JavaConstructor> result = ImmutableSet.builder();
                for (JavaClass javaClass : collection) {
                    result.addAll(javaClass.getConstructors());
                }
                return result.build();
            }
        };
    }

    static ClassesTransformer<JavaMethod> toMethods() {
        return new AbstractClassesTransformer<JavaMethod>("methods") {
            @Override
            public Iterable<JavaMethod> doTransform(JavaClasses collection) {
                ImmutableSet.Builder<JavaMethod> result = ImmutableSet.builder();
                for (JavaClass javaClass : collection) {
                    result.addAll(javaClass.getMethods());
                }
                return result.build();
            }
        };
    }
}
