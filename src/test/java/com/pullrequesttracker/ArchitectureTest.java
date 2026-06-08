package com.pullrequesttracker;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.pullrequesttracker")
class ArchitectureTest {

    @ArchTest
    static final ArchRule layerDependenciesAreRespected = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers().layer("Presentation")
            .definedBy("com.pullrequesttracker.presentation..").layer("Application")
            .definedBy("com.pullrequesttracker.application..").layer("Infrastructure")
            .definedBy("com.pullrequesttracker.infrastructure..").layer("Domain")
            .definedBy("com.pullrequesttracker.domain..").whereLayer("Presentation")
            .mayOnlyAccessLayers("Application", "Domain").whereLayer("Application").mayOnlyAccessLayers("Domain")
            .whereLayer("Infrastructure").mayOnlyAccessLayers("Application", "Domain").whereLayer("Domain")
            .mayNotAccessAnyLayer();

    @ArchTest
    static final ArchRule domainHasNoSpringDependencies = noClasses().that()
            .resideInAPackage("com.pullrequesttracker.domain..").should().dependOnClassesThat()
            .resideInAPackage("org.springframework..");

}
