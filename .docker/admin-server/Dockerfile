FROM openjdk:21-jdk AS builder
RUN microdnf install findutils

WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew

RUN if [ ! -f modules/admin-server/build/libs/admin-server-*.jar ]; then \
    ./gradlew :modules:admin-server:bootJar -x test -x allTests -x jsBrowserTest; \
    fi

FROM openjdk:21-jdk
RUN microdnf install curl

WORKDIR /app

COPY --from=builder /app/modules/admin-server/build/libs/admin-server-*.jar ./admin-server.jar
HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8080/status || exit 1

# Create non-root user
RUN useradd -r -u 1002 -g root admin-server
USER admin-server

ENTRYPOINT ["java"]
CMD ["-XX:MaxRAMPercentage=75.0", "-XX:InitialRAMPercentage=50.0", "-XX:+UseG1GC", "-jar", "admin-server.jar"]
