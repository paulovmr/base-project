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

  * Create a new database: `createdb <YOUR_NEW_PROJECT_NAME>_devel`

  * Create a new database: `createdb <YOUR_NEW_PROJECT_NAME>_test`

  * Allow your new user to access and modify the new database created: `psql`
   - `postgres=# alter user <YOUR_NEW_PROJECT_NAME> with encrypted password '<YOUR_NEW_PROJECT_NAME>';`
   - `postgres=# grant all privileges on database <YOUR_NEW_PROJECT_NAME>)_devel to <YOUR_NEW_PROJECT_NAME>;`
   - `postgres=# grant all privileges on database <YOUR_NEW_PROJECT_NAME>_test to <YOUR_NEW_PROJECT_NAME>;`

  * To access the development database: `psql -h localhost <YOUR_NEW_PROJECT_NAME>_devel <YOUR_NEW_PROJECT_NAME>`
   - `Password for user <YOUR_NEW_PROJECT_NAME>: <YOUR_NEW_PROJECT_NAME>`

  * To access the test database: `psql -h localhost <YOUR_NEW_PROJECT_NAME>_test <YOUR_NEW_PROJECT_NAME>`
   - `Password for user <YOUR_NEW_PROJECT_NAME>: <YOUR_NEW_PROJECT_NAME>`

  * Use the following commands to control the PostgreSQL server:
   - Start the service: `sudo /etc/init.d/postgresql start`
   - Stop the service: `sudo /etc/init.d/postgresql stop`
   - Find out the status: `sudo /etc/init.d/postgresql status`
   - Restart the service: `sudo /etc/init.d/postgresql restart`

### Creating your project

  * Create a new Github repository from this one

  * Clone your new repository: `git clone git@github.com:<YOUR_GITHUB_USER>/<YOUR_NEW_PROJECT_NAME>.git`

  * Run the script `<ROOT_DIRECTORY>/setup_new_project.sh <YOUR_NEW_PROJECT_NAME>`

  * Run a `mvn clean install && mvn eclipse:clean eclipse:eclipse` on the `implementation` directory

  * Import your project into Eclipse

