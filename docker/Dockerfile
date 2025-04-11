# Use a Linix image with Tomcat 10
FROM tomcat:10.1.0-M5-jdk16-openjdk-slim-bullseye

# Remove default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT*

# Copy your WAR file (named ROOT.war) into the webapps directory
COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080


