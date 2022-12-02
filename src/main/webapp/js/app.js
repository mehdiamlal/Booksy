import {loginForm} from "./components/login-form.js";
import {bookingForm} from "./components/booking-form.js";
import {courseCard} from "./components/course-card.js";
import {navbar} from "./components/navbar.js";
import {appointmentCard} from "./components/appointment-card.js";
import {newTutorForm} from "./components/new-tutor-form.js";
import {bookingView} from "./views/booking-view.js";
import {coursesView} from "./views/courses-view.js";
import {landingView} from "./views/landing-view.js";
import {loginView} from "./views/login-view.js";
import {addTutorView} from "./views/add-tutor-view.js";

const app = Vue.createApp({
        data: function () {
            return {
                bookings: [{
                    "tutor": "Mehdi Amlal",
                    "email": "mehdi@gmail.com",
                    "course": "Informatica",
                    "day": "23/01/2023",
                    "timeSlot": "16:00 - 17:00"
                }, {
                    "tutor": "Mehdi Amlal",
                    "email": "mehdi@gmail.com",
                    "course": "Informatica",
                    "day": "23/01/2023",
                    "timeSlot": "16:00 - 17:00"
                }, {
                    "tutor": "Mehdi Amlal",
                    "email": "mehdi@gmail.com",
                    "course": "Informatica",
                    "day": "23/01/2023",
                    "timeSlot": "16:00 - 17:00"
                },{
                    "tutor": "Mehdi Amlal",
                    "email": "mehdi@gmail.com",
                    "course": "Informatica",
                    "day": "23/01/2023",
                    "timeSlot": "16:00 - 17:00"
                }]
            }
        }
});

app.component("login-form", loginForm);
app.component("booking-form", bookingForm);
app.component("course-card", courseCard);
app.component("navbar", navbar);
app.component("appointment-card", appointmentCard);
app.component("new-tutor-form", newTutorForm);
app.component("booking-view", bookingView);
app.component("courses-view", coursesView);
app.component("landing-view", landingView);
app.component("login-view", loginView);
app.component("add-tutor-view", addTutorView);

app.mount("#app");


