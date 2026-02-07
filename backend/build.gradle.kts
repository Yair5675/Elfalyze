plugins {
	java
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm")
}

group = "com.yairz"
version = "0.0.1-SNAPSHOT"
description = "backend"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation(kotlin("stdlib-jdk8"))
	testImplementation("io.mockk:mockk:1.14.0")
	testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
	implementation(kotlin("stdlib-jdk8"))

}

tasks.withType<Test> {
	useJUnitPlatform()
}
