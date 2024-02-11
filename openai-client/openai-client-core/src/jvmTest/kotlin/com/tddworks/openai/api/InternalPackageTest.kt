package com.tddworks.openai.api

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import java.io.IOException

class InternalPackageTest {

    companion object {
        private val BASE_PACKAGE = InternalPackageTest::class.java.`package`.name
    }

    private val analyzedClasses = ClassFileImporter().importPackages(BASE_PACKAGE)

    @Test
    @Throws(IOException::class)
    fun internalPackagesAreNotAccessedFromOutside() {

        // so that the test will break when the base package is re-named
        assertPackageExists(BASE_PACKAGE)
        val internalPackages = internalPackages(BASE_PACKAGE)
        for (internalPackage in internalPackages) {
            assertPackageExists(internalPackage)
            assertPackageIsNotAccessedFromOutside(internalPackage)
        }
    }

    /**
     * Finds all packages named "internal".
     */
    private fun internalPackages(basePackage: String): List<String> {
        val scanner = Scanners.SubTypes
        val reflections = Reflections(basePackage, scanner)
        return reflections.getSubTypesOf(Object::class.java).map {
            it.`package`.name
        }.filter {
            it.endsWith(".internal")
        }
    }

    private fun assertPackageIsNotAccessedFromOutside(internalPackage: String) {
        noClasses()
            .that()
            .resideOutsideOfPackage(packageMatcher(internalPackage))
            .should()
            .dependOnClassesThat()
            .resideInAPackage(packageMatcher(internalPackage))
            .check(analyzedClasses)
    }

    private fun assertPackageExists(packageName: String?) {
        assertThat(analyzedClasses.containPackage(packageName))
            .`as`("package %s exists", packageName)
            .isTrue()
    }

    private fun packageMatcher(fullyQualifiedPackage: String): String? {
        return "$fullyQualifiedPackage.."
    }

}
