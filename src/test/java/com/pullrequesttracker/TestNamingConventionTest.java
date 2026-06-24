package com.pullrequesttracker;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "com.pullrequesttracker")
class TestNamingConventionTest {

    @ArchTest
    static final ArchRule testMethodsDoNotUseWhenCalled = methods().that().areAnnotatedWith(Test.class).should()
            .haveNameNotMatching(".*_whenCalled(_.*|$)")
            .because("use a specific condition instead of the generic 'whenCalled' — see ADR 0003");

    @ArchTest
    static final ArchRule testMethodNamesMustContainShould = methods().that().areAnnotatedWith(Test.class).should()
            .haveNameMatching(".*should.*")
            .because("test method names must express the expected outcome using 'should' — see ADR 0003");
}
