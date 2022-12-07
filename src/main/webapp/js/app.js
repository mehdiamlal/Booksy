import {loginForm} from "./components/login-form.js";
import {bookingForm} from "./components/booking-form.js";
import {courseCard} from "./components/course-card.js";
import {navbar} from "./components/navbar.js";
import {appointmentCard} from "./components/appointment-card.js";
import {newTutorForm} from "./components/new-tutor-form.js";
import {adminCourseCard} from "./components/admin-course-card.js";
import {adminBookingCard} from "./components/admin-booking-card.js";
import {bookingView} from "./views/booking-view.js";
import {coursesView} from "./views/courses-view.js";
import {landingView} from "./views/landing-view.js";
import {loginView} from "./views/login-view.js";
import {addTutorView} from "./views/add-tutor-view.js";
import {bookingListView} from "./views/booking-list-view.js";
import {studentHomeView} from "./views/student-home-view.js";
import {adminHomeView} from "./views/admin-home-view.js";
import {adminCoursesView} from "./views/admin-courses-view.js";
import {adminBookingListView} from "./views/admin-booking-list-view.js";

const app = Vue.createApp();

app.component("login-form", loginForm);
app.component("booking-form", bookingForm);
app.component("course-card", courseCard);
app.component("navbar", navbar);
app.component("appointment-card", appointmentCard);
app.component("new-tutor-form", newTutorForm);
app.component("admin-course-card", adminCourseCard);
app.component("admin-booking-card", adminBookingCard);
app.component("booking-view", bookingView);
app.component("courses-view", coursesView);
app.component("landing-view", landingView);
app.component("login-view", loginView);
app.component("add-tutor-view", addTutorView);
app.component("booking-list-view", bookingListView);
app.component("student-home-view", studentHomeView);
app.component("admin-home-view", adminHomeView);
app.component("admin-courses-view", adminCoursesView);
app.component("admin-booking-list-view", adminBookingListView);


const routes = [
    {path: "/", component: landingView},
    {path: "/login", component: loginView},
    {path: "/home", component: studentHomeView},
    {path: "/courses", component: coursesView},
    {path: "/bookings", component: bookingListView},
    {path: "/book", component: bookingView},
    {path: "/admin", component: adminHomeView},
    {path: "/admin_courses", component: adminCoursesView},
    {path: "/admin_bookings", component: adminBookingListView},
    {path: "/add_tutor", component: addTutorView}
]

const router = VueRouter.createRouter({
    // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
    history: VueRouter.createWebHashHistory(),
    routes, // short for `routes: routes`
})

app.use(router);

app.mount("#app");


