#   Web Quiz Engine
A multi-user web service for creating and solving quizzes using REST API, an embedded database, security, and other technologies. Based on Spring Boot, H2 Database
#
## About
In the Internet, you can often find sites where you need to answer some questions. It can be educational sites, sites with psychological tests, job search services, or just entertaining sites like web quests. The common thing for them is the ability to answer questions (or quizzes) and then see some results. In this project, I  developed a multi-users web service for creating and solving quizzes.
##Learning outcomes
You will clearly understand what is the backend development and how to use many modern technologies together to get a great result. If you would like to continue the project, you could develop a web or mobile client for this web service. You will learn about REST API, an embedded database, security, and other technologies.
## REST API Functionality
* Users can register using email and password. /api/register
* All resources are secured via HTTP Basic Authentication
* Creating creating Quizzes. /api/quizzes
* Listing Quizzes. Built in paging and sorting of quizzes returned. GET /api/quizzes
* List by Quiz given the id. /ap/quizzes/{id}
* Solve a quiz question./ap/quizzes/{id}/solve
* Delete quiz. Only the creator of quiz can delete.
* Records all correct quiz attempts.

### Doing these to keep my Java Programming Skill Alive.
