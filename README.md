A Java and Spring backend for a task management app. Developed for learning purposes.

## Uses
(not exhaustive, main components)
 - Java 17
 - Spring Framework 5
 - Spring Boot
 - Spring Data JPA
 - Spring MVC
 - Spring Security
 - Hibernate
 - PostgreSQL & H2
 - Flyway
 - JUnit 5
 - SLF4J & LogBack
 - SpringFox
## Features
Usage-wise:
 - Fundamental app structer looks like so:  
   - Workspaces can represent orginization or big departments of one orginization etc.  
     They contain
     - Boards  
   - Boards can represent departments of an orginization, sub-division of a big department, big projects etc.  
     They contain
     - Cardlists 
   - Cardlists can represent projects, sets of tasks etc.  
     They contain
     - Cards  
   - Cards can represent individual tasks or sets of tasks.  
     They can contain:
     - text
     - todo lists
     - files
 - Cards can be assigned to Users
   - If a Reminder is set then all assignees will receive a reminder at specified time&date
 - Labels can be added to Cards
   - Labels are stored in Boards (Labels are not unique to a Card; if you delete a Card with Labels, they will remain; if you delete a label it will be deleted from all the cards)
 - Workspaces and Boards also contain users
   - A Board inside of a Workspace is not required to only contain Members of an enclosing Workspace. E.g. a company may hire freelancers for a project, so they will have a   
   -   
Programming-wise:  
 - a functional JSON API (ability to add users, workspaces etc)
 - Swagger docs
 - JWT auth 
 - Domain-imposed constraints. E.g. you can't add
