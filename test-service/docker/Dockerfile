FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ARG VERSION
ENV version=${VERSION}
RUN mkdir -p /app
COPY ${JAR_FILE} /app/translation-service.jar
COPY docker-entrypoint.sh /app/docker-entrypoint.sh
RUN chown -R nobody:nogroup /app
RUN chmod +x /app/docker-entrypoint.sh
USER nobody
ENTRYPOINT ["/app/docker-entrypoint.sh"]
EXPOSE 2000/tcp