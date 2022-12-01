import {loginForm} from "./components/login-form.js";
import {bookingForm} from "./components/booking-form.js";
import {courseCard} from "./components/course-card.js";
import {navbar} from "./components/navbar.js";
import {appointmentCard} from "./components/appointment-card.js";
import {bookingView} from "./views/booking-view.js";
import {coursesView} from "./views/courses-view.js";
import { landingView } from "./views/landing-view.js";

const app = Vue.createApp({
        data: function () {
            return {
                message: "hello world!"
            }
        }
});

app.component("login-form", loginForm);
app.component("booking-form", bookingForm);
app.component("course-card", courseCard);
app.component("navbar", navbar);
app.component("appointment-card", appointmentCard);
app.component("booking-view", bookingView);
app.component("courses-view", coursesView);
app.component("landing-view", landingView);

app.mount("#app");


