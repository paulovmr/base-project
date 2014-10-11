base-project
============

Base maven project that uses JPA (Hibernate implementation), Resteasy and others common dependencies (see &lt;ROOT_DIRECTORY>/pom.xml for the list of all dependencies).

Tutorial
========

Before follow this tutorial, define your new project name, and replace it in all the `<YOUR_NEW_PROJECT_NAME>` sentences.

### Pre-requisites

  * Java 8
  * Maven 3
  * Eclipse (used here: Eclipse Luna)
  * PostgreSQL (used here: 9.3)
    
### Installing and configuring your PostgreSQL database

  * Install PostgreSQL 9.3: `sudo apt-get install postgresql-9.3`
  
  * Change user: `sudo su postgres`

  * Create a new user: `createuser <YOUR_NEW_PROJECT_NAME>`

  * Create a new database: `createdb <YOUR_NEW_PROJECT_NAME>db`

  * Allow your new user to access and modify the new database created: `psql`
   - `postgres=# alter user <YOUR_NEW_PROJECT_NAME> with encrypted password '<YOUR_NEW_PROJECT_NAME>';`
   - `postgres=# grant all privileges on database <YOUR_NEW_PROJECT_NAME>db to <YOUR_NEW_PROJECT_NAME>;`

  * To access the database: `psql -h localhost <YOUR_NEW_PROJECT_NAME>db <YOUR_NEW_PROJECT_NAME>`
   - `Password for user <YOUR_NEW_PROJECT_NAME>: <YOUR_NEW_PROJECT_NAME>`

  * Use the following commands to control the PostgreSQL server:
   - Start the service: `sudo /etc/init.d/postgresql start`
   - Stop the service: `sudo /etc/init.d/postgresql stop`
   - Know the status: `sudo /etc/init.d/postgresql status`
   - Restart the service: `sudo /etc/init.d/postgresql restart`

