package com.tngtech.archunit.library.dependencies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tngtech.archunit.core.Dependency;
import com.tngtech.archunit.core.JavaClass;
import com.tngtech.archunit.core.JavaClasses;
import com.tngtech.archunit.core.Optional;
import com.tngtech.archunit.lang.DescribedIterable;
import com.tngtech.archunit.lang.InputTransformer;
import com.tngtech.archunit.lang.conditions.PackageMatcher;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.tngtech.archunit.core.Dependency.toTargetClasses;
import static com.tngtech.archunit.lang.conditions.PackageMatcher.TO_GROUPS;

public class Slices implements DescribedIterable<Slice> {
    private final Iterable<Slice> slices;
    private String description = "Slices";

    private Slices(Iterable<Slice> slices) {
        this.slices = slices;
    }

    public static ClosedCreator of(JavaClasses classes) {
        return new ClosedCreator(classes);
    }

    @Override
    public Iterator<Slice> iterator() {
        return slices.iterator();
    }

    public Slices as(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Allows the naming of single slices, where back references to the matching pattern can be denoted by '$' followed
     * by capturing group number. <br/>
     * E.g. {@code namingSlices("Slice $1")} would name a slice matching {@code '*..service.(*)..*'}
     * against {@code 'com.some.company.service.hello.something'} as 'Slice hello'.
     *
     * @param pattern The naming pattern, e.g. 'Slice $1'
     * @return The same slices with adjusted naming for each single slice
     */
    public Slices namingSlices(String pattern) {
        for (Slice slice : slices) {
            slice.as(pattern);
        }
        return this;
    }

    /**
     * @see ClosedCreator#matching(String)
     */
    public static Transformer matching(String packageIdentifier) {
        return new Transformer(packageIdentifier, String.format("slices matching '%s'", packageIdentifier));
    }

    public static class Transformer extends InputTransformer<Slice> {
        private final String packageIdentifier;
        private Optional<String> namingPattern = Optional.absent();

        private Transformer(String packageIdentifier, String description) {
            super(description);
            this.packageIdentifier = packageIdentifier;
        }

        /**
         * @see ClosedCreator#namingSlices(String)
         */
        public Transformer namingSlices(String pattern) {
            return namingSlices(Optional.of(pattern));
        }

        Transformer namingSlices(Optional<String> pattern) {
            this.namingPattern = checkNotNull(pattern);
            return this;
        }

        @Override
        public Transformer as(String description) {
            return new Transformer(packageIdentifier, description).namingSlices(namingPattern);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Slices doTransform(JavaClasses classes) {
            Slices slices = new ClosedCreator(classes).matching(packageIdentifier);
            if (namingPattern.isPresent()) {
                slices.namingSlices(namingPattern.get());
            }
            return slices.as(getDescription());
        }

        public Slices of(JavaClasses classes) {
            return new Slices(transform(classes));
        }

        public Slices transform(Iterable<Dependency> dependencies) {
            return new Slices(transform(toTargetClasses(dependencies)));
        }
    }

    public static class ClosedCreator {
        private final JavaClasses classes;

        private ClosedCreator(JavaClasses classes) {
            this.classes = classes;
        }

        /**
         * Supports partitioning a set of {@link JavaClasses} into different slices by matching the supplied
         * package identifier. For identifier syntax, see {@link com.tngtech.archunit.lang.conditions.PackageMatcher}.<br/>
         * The slicing is done according to capturing groups (thus if none are contained in the identifier, no more than
         * a single slice will be the result). For example
         * <p>
         * Suppose there are three classes:<br/><br/>
         * {@code com.example.slice.one.SomeClass}<br/>
         * {@code com.example.slice.one.AnotherClass}<br/>
         * {@code com.example.slice.two.YetAnotherClass}<br/><br/>
         * If slices are created by specifying<br/><br/>
         * {@code Slices.of(classes).byMatching("..slice.(*)..")}<br/><br/>
         * then the result will be two slices, the slice where the capturing group is 'one' and the slice where the
         * capturing group is 'two'.
         * </p>
         *
         * @param packageIdentifier The identifier to match against
         * @return Slices partitioned according the supplied package identifier
         */
        public Slices matching(String packageIdentifier) {
            SliceBuilders sliceBuilders = new SliceBuilders();
            PackageMatcher matcher = PackageMatcher.of(packageIdentifier);
            for (JavaClass clazz : classes) {
                Optional<List<String>> groups = matcher.match(clazz.getPackage()).transform(TO_GROUPS);
                sliceBuilders.add(groups, clazz);
            }
            return new Slices(sliceBuilders.build()).as(String.format("slices matching '%s'", packageIdentifier));
        }
    }

    private static class SliceBuilders {
        Map<List<String>, Slice.Builder> sliceBuilders = new HashMap<>();

        void add(Optional<List<String>> matchingGroups, JavaClass clazz) {
            if (matchingGroups.isPresent()) {
                put(matchingGroups.get(), clazz);
            }
        }

        private void put(List<String> matchingGroups, JavaClass clazz) {
            if (!sliceBuilders.containsKey(matchingGroups)) {
                sliceBuilders.put(matchingGroups, Slice.Builder.from(matchingGroups));
            }
            sliceBuilders.get(matchingGroups).addClass(clazz);
        }

        Set<Slice> build() {
            Set<Slice> result = new HashSet<>();
            for (Slice.Builder builder : sliceBuilders.values()) {
                result.add(builder.build());
            }
            return result;
        }
    }
}
