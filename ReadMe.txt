Ticketing System

Author: Gustavo Lessa (https://github.com/gustavolessadublin)
December 2017



ACCOUNTS:
Username	Password	Type
——————————————————————————
manager	manager	manager
admin	admin	admin
John	 John	tech
Johan	Johan	tech
James	James	tech


ACCOUNTS MAIN FUNCTIONS:
——————————————————————————
- Admin: add user and update user information

- Tech support: view all tickets, open new ticket, close ticket, delete ticket, update ticket.

- Manager: overview tickets stats (numbers and graph) and check amount of work of each Tech Support staff.


DATABASE:
——————————————————————————
- TicketingSystem.sql can be use to restore database for this application;

- MariaDB RDBMS was used along with jConnector.

- The database connection is set to LocalHost, username = root, no password, port 3306. If you need to change this parameters, go to DatabaseOperations.java and modify the methods defaultConnection and intConnection.

- All the passwords are encrypted using jBCrypt (Copyright 2006 Damien Miller <djm@mindrot.org>), based on BCrypt password hashing function.



CODE FEATURES AND DETAILS:
——————————————————————————
- ReadMe screen appears only once.

- If the system is a MacOS, the menu will be displayed on the top. Otherwise, the default menu will be shown.

- When adding user, the system checks for existing username in database.

- Both add user and update user (when updating password) operations require "confirm password" field, to avoid mistyping.

- Tech Support tickets table is scrollable, sortable, non-editable and an ItemListener detects double-clicks on rows.

- View Ticket Details screen (dialog) varies according to whether the ticket is open or closed.

- MVC design pattern was used and each part has its designated package.



ADMIN ACCOUNT INFO:
——————————————————————————

- Admin dashboard contains two main panels, one for Adding users and another to Update user information.

- On the update panel, when an user ID is selected, an Item Listener is triggered and the other fields are populated automatically. Any modification will be transferred to the database on pressing Update button.



TECH SUPPORT ACCOUNT INFO:
——————————————————————————

- When a ticket is double-clicked or selected and the Edit button clicked, a new screen is opened, displaying ticket details. 

- If the ticket is open, this screen will contain button allowing the user to Close, Delete or Update ticket.

- If the ticket is closed, the screen will contain only Delete function.

- At the main screen, if Close ticket is clicked on an open ticket, a dialog will appear asking to confirm. If the ticket is closed, nothing happens.

- The New ticket button shows a dialog containing a form to Add new ticket. The system was designed to allow only one Tech Support staff to be assigned to each ticket. So, a dynamically populated drop-down menu allows the user to choose to which tech staff the ticket will be assigned.


MANAGER ACCOUNT INFO:
——————————————————————————

- The manager dashboard contains three panels: Overview, Tickets per Tech Staff and Graph.

- All the information is dynamically displayed. When Refresh is clicked, up-to-date data is retrieved from the database.



