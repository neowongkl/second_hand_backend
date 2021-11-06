# Spring Boot Backend Core

## Project Setup
- Reload build.gradle.kts
- Add "-Dspring.profiles.active=LOCAL" to vm option
- application.properties
  - set "server.servlet.context-path"

## Datasource Setup
Supported datasource: mariaDb, mySql
1. In application.properties, enable required datasource, eg, datasource.mariadb.enable=true
2. Add target datasource annotation to repository class
3. inherit AuditableEntity to create dao for autofill createdBy, createdOn, lastModifiedBy, lastModifiedDate

## Security Setting
Edit webSecurity-{env}.json 
### Cors
set mapping in json
### Jwt & User
- set enable jwt and user
- customize user model for every project
