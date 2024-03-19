FROM openjdk:17-slim
COPY . work/
WORKDIR work
RUN /work/gradlew build
CMD ["java", "-jar", "build/libs/challenge-backend-SNAPSHOT.jar"]