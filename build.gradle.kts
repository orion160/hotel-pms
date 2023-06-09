plugins {
    application
    jacoco
    id("com.diffplug.spotless") version "6.19.0"
}

group = "edu"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)

    implementation(libs.log4j2.api)
    implementation(libs.log4j2.core)
    implementation(libs.log4j2.slf4j2.impl)

    implementation(libs.log4j2.jcl)

    runtimeOnly(libs.bouncycastle.bcpkix)
    implementation(libs.spring.security.crypto)

    runtimeOnly(libs.postgresql.driver)
    implementation(libs.hikaricp.pool)

    implementation(libs.jakarta.inject.api)

    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(false)
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

application {
    mainClass.set("edu.hotel.pms.Main")
    mainModule.set("edu.hotel.pms")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:unchecked")
    options.compilerArgs.add("-Xlint:deprecation")
}

spotless {
    encoding("UTF-8")
    java {
        googleJavaFormat().reflowLongStrings()
        formatAnnotations()
    }
    kotlinGradle {

    }
}
