plugins {
	java
	application
	jacoco
	id("com.diffplug.spotless") version "7.2.1"
}

group = "com.healthcare.platform"
version = "0.1.0"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

dependencies {

	implementation("info.picocli:picocli:4.7.7")
	implementation("io.pebbletemplates:pebble:3.2.4")

	implementation("org.slf4j:slf4j-api:2.0.17")
	runtimeOnly("ch.qos.logback:logback-classic:1.5.18")

	testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("org.assertj:assertj-core:3.27.6")
	testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.4.1")

}

application {
	mainClass.set("com.healthcare.platform.cli.HcpCliApplication")
}

tasks.test {
	useJUnitPlatform()
}

jacoco {
	toolVersion = "0.8.13"
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
}

spotless {

	java {
		target("src/**/*.java")
		removeUnusedImports()
		importOrder()
		formatAnnotations()
		cleanthat()
		googleJavaFormat("1.28.0")
		trimTrailingWhitespace()
		endWithNewline()

	}

}