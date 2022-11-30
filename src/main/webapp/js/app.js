import {loginForm} from "./components/login-form.js";
import {bookingForm} from "./components/booking-form.js";
import {bookingView} from "./views/booking-view.js";
import {courseCard} from "./components/course-card.js";
import {navigationBar} from "./components/navbar.js";
import {appointmentCard} from "./components/appointment-card.js";
const app = Vue.createApp({
        data: function () {
            return {
                message: "hello world!"
            }
        }
});

app.component("login-form", loginForm);
app.component("booking-form", bookingForm);
app.component("booking-view", bookingView);
app.component("course-card", courseCard);
app.component("navbar", navigationBar);
app.component("appointment-card", appointmentCard);

app.mount("#app");


