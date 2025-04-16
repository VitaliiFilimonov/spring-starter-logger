plugins {
    id("java")
    id("org.springframework.boot") version("3.2.0")
    id("io.spring.dependency-management") version("1.1.0")
}

group = "ru.homework"
version = "1.0-SNAPSHOT"

val aspectj = "1.9.20.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.aspectj:aspectjweaver:$aspectj")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}