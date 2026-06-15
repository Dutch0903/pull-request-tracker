package com.pullrequesttracker;

import com.pullrequesttracker.application.exception.ApplicationException;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.pullrequesttracker")
class ExceptionOwnershipTest {

    @ArchTest
    static final ArchRule domainExceptionsOnlyAccessedByDomainLayer = noClasses()
            .that().resideOutsideOfPackage("com.pullrequesttracker.domain..")
            .should().accessClassesThat().resideInAPackage("com.pullrequesttracker.domain.exception..")
            .because("domain exceptions may only be thrown by the domain layer (ADR 0001 Rule 1)");

    @ArchTest
    static final ArchRule portExceptionsNotAccessedByPresentation = noClasses()
            .that().resideInAPackage("com.pullrequesttracker.presentation..")
            .should().accessClassesThat().resideInAPackage("com.pullrequesttracker.application.provider..")
            .because("presentation must not depend on port failure contracts (ADR 0001 Rule 2)");

    @ArchTest
    static final ArchRule useCaseExceptionsExtendApplicationException = classes()
            .that().resideInAPackage("com.pullrequesttracker.application.exception..")
            .should().beAssignableTo(ApplicationException.class)
            .because("use case exceptions must extend ApplicationException (ADR 0001 Rule 3)");

}
