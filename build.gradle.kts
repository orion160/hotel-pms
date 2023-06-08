plugins {
    java
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
    val junitVersion = "5.9.1"
    val mockitoVersion = "5.3.1"

    val log4jVersion = "2.20.0"

    val bouncyCastleVersion = "1.73"
    val springSecurityVersion = "6.1.0"

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")

    implementation("org.apache.logging.log4j:log4j-jcl:$log4jVersion")

    implementation("org.bouncycastle:bcpkix-jdk18on:$bouncyCastleVersion")
    implementation("org.springframework.security:spring-security-crypto:$springSecurityVersion")

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
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
    kotlinGradle {}
}
