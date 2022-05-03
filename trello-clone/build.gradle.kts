plugins {
    java
    idea
    jacoco
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

jacoco {
    version = "0.8.6"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework:spring-jdbc")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.github.javafaker:javafaker:1.0.2")

    implementation("org.apache.commons:commons-collections4:4.4")

    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("org.flywaydb:flyway-core:8.2.2")
    implementation("com.zaxxer:HikariCP:5.0.0")
    runtimeOnly("com.h2database:h2:1.4.200")
    //runtimeOnly("org.postgresql:postgresql:42.3.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    finalizedBy(tasks.withType(JacocoReport::class))
}