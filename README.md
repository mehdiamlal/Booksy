![logo](https://user-images.githubusercontent.com/76702446/226922720-6700ab58-8e8a-4d13-8094-88c7e9dbffa0.png)
<br /><br />
## How it works
Booksy is a private tutoring service that allows students to book tutoring sessions on a wide range of subjects.

There are two types of users:
- Students
- Admins

### Students
A student can:
- Book a tutoring session (1-hour slot).
- Browse the course list, checking the tutor list for each course.
- Check tutors' availability for a specified date.
- Mark a booking as completed.
- Cancel a booking.
- Browse the booking history (containing active, canceled and completed bookings).
- See upcoming bookings on the home page.

### Admin
An admin can:
- Browse the course list.
- Add a new course.
- Disable/enable a course.
- Add a new tutor.
- Assign a course to a tutor.
- Check tutors' availability for a specified date.
- Remove a tutor from the list of tutors who teach a course.
- Browse the booking history (active, canceled and completed bookings) for all the students.
- Cancel a booking.


## Technical Details
### Backend
The backend is implemented using plain **Java Servlets**, without any additional frameworks.
<br />
The Architecture is organized as a **REST API**.
<br />
**Session tracking and management** is implemented using Java's **HttpSession API**.
### Fronted
The fronted is organized as a SPA (Single Page Application) implemented using **VueJS** and **Vue Router**. 
### DB
Data is stored in a **MySQL Database** and the operations are implemented as methods of the DAO (Data Access Object) class, using the **JDBC** API to interact with database.
## Screenshots
![Screenshot 2023-03-22 at 09 40 21](https://user-images.githubusercontent.com/76702446/226934987-8871c2db-ec55-410e-ac59-a0530bb018d1.png)
![Screenshot 2023-03-22 at 09 40 28](https://user-images.githubusercontent.com/76702446/226935014-6d5c514c-9f4c-4c8c-b271-08c02800d69f.png)
<br /><br />
### Admin
![Screenshot 2023-03-22 at 10 04 04](https://user-images.githubusercontent.com/76702446/226933779-dd4cf2c1-4e41-4667-a286-5ee076bd8ef7.png)
![Screenshot 2023-03-22 at 10 04 18](https://user-images.githubusercontent.com/76702446/226933783-933073f3-7406-429f-9506-b6cfc5f9d48d.png)
![Screenshot 2023-03-22 at 10 04 32](https://user-images.githubusercontent.com/76702446/226933785-fc244b83-81f1-413d-9b52-1abd832c77a2.png)
![Screenshot 2023-03-22 at 10 04 44](https://user-images.githubusercontent.com/76702446/226933788-9b958a46-77a5-4684-b083-5a2775875f42.png)
![Screenshot 2023-03-22 at 10 04 57](https://user-images.githubusercontent.com/76702446/226933791-90119bbb-d243-4758-987e-0dfa1f4d5289.png)
<br /><br /><br /><br />
### Student
![Screenshot ![Screenshot 2023-03-22 at 10 03 08](https://user-images.githubusercontent.com/76702446/226935252-5e860b92-277c-4ba1-808f-98583e978d3c.png)
2023-03-22 at 10 02 56](https://user-images.githubusercontent.com/76702446/226935210-6be68eb4-fcbf-429b-a3f4-978b90220429.png)
![Screenshot 2023-03-22 at 10 03 16](https://user-images.githubusercontent.com/76702446/226935285-b2baf924-2272-493b-a315-62448a3a32de.png)
![Screenshot 2023-03-22 at 10 03 38](https://user-images.githubusercontent.com/76702446/226935410-fb90d2f1-6dea-400a-9866-775ef24b8e96.png)
![Screenshot 2023-03-22 at 10 03 52](https://user-images.githubusercontent.com/76702446/226935450-daf71cb2-2727-420f-aa50-03815931addd.png)
