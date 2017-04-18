# Supermarket Support System
### RMIT Chaos Team
____
- **Architecture**
	- Server side
		- PHP
		- [Restler](https://github.com/Luracast/Restler) Framework
		- MySQL
	- Client Side
		- Java
		- [Unirest](http://unirest.io) framework
		- Maven
- **Folders and files**
	- api
		- PHP REST API
		- Using [Restler](https://github.com/Luracast/Restler) framework
		- Communicates with MySQL database
		- api_calls is a [Paw](https://paw.cloud) project to test the api calls
	- db
		- Contains database DDL to create the database scheme
		- Contains database data dump file
	- pom.xml
		- Maven configuration file
	- src, bin and target
		- Java source files (Client)
