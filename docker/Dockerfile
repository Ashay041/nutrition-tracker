FROM tomcat:9.0-jdk21-openjdk

# Remove default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT*

# Copy your WAR file (named ROOT.war) into the webapps directory
COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080


