## Frog-chat a web-chat on tomcat.

I made a whole web chat using Tomcat 9 and Bootstrap. The project was first done in Italian, so you might still spot some Italian words here and there. It uses a cache with a reader-writer policy to manage requests between users and the database. I followed RESTful principles for the structure. The site is secure from SQL Injection and most XSS attacks, although there are some missing features like the logout policy. To make things easier during development, I used some less efficient algorithms, but feel free to tweak them as needed. This project is under the MIT license.


## Chat
![Chat](https://github.com/StefanPacella/Frog-chat/blob/main/chat.png)
## Login
![Login](https://github.com/StefanPacella/Frog-chat/blob/main/login.png)
