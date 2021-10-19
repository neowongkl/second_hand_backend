# Spring Boot Backend Core

## Project Setup
- Reload build.gradle.kts
- Add "-Dspring.profiles.active=LOCAL" to vm option
- application.properties
  - set "server.servlet.context-path"

## Datasource Setup
1. In application.properties, enable required datasource, default is mariaDb
2. Add target datasource annotation to repository class