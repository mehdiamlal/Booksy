import {loginForm} from "./components/login-form.js";
import {bookingForm} from "./components/booking-form.js";
import {courseCard} from "./components/course-card.js";
import {navbar} from "./components/navbar.js";
import {appointmentCard} from "./components/appointment-card.js";
import {newTutorForm} from "./components/new-tutor-form.js";
import {adminCourseCard} from "./components/admin-course-card.js";
import {adminBookingCard} from "./components/admin-booking-card.js";
import {slotCard} from "./components/slot-card.js";
import {adminSlotCard} from "./components/admin-slot-card.js";
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
import {activeBookingListView} from "./views/active-booking-list-view.js";
import {availableSlotsView} from "./views/available-slots-view.js";
import {adminAvailableSlotsView} from "./views/admin-available-slots-view.js";

const app = Vue.createApp();

app.component("login-form", loginForm);
app.component("booking-form", bookingForm);
app.component("course-card", courseCard);
app.component("navbar", navbar);
app.component("appointment-card", appointmentCard);
app.component("new-tutor-form", newTutorForm);
app.component("admin-course-card", adminCourseCard);
app.component("admin-booking-card", adminBookingCard);
app.component("slot-card", slotCard);
app.component("admin-slot-card", adminSlotCard);
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
app.component("active-booking-list-view", activeBookingListView);
app.component("available-slots-view", availableSlotsView);
app.component("admin-available-slots-view", adminAvailableSlotsView);


const routes = [
    {path: "/", name: "Landing", component: landingView},
    {path: "/login", name: "Login", component: loginView},
    {path: "/home", name: "Home", component: studentHomeView},
    {path: "/courses", name: "Courses", component: coursesView},
    {path: "/bookings", name: "Bookings", component: bookingListView},
    {path: "/book", name: "Book", component: bookingView},
    {path: "/admin", name: "Admin", component: adminHomeView},
    {path: "/admin_courses", name: "Admin-Courses", component: adminCoursesView},
    {path: "/admin_bookings", name: "Admin-Bookings", component: adminBookingListView},
    {path: "/add_tutor", name: "Add-Tutor", component: addTutorView},
    {path: "/active_bookings", name: "Active-Bookings", component: activeBookingListView},
    {path: "/available_slots", name: "Available-Slots", component: availableSlotsView},
    {path: "/admin_available_slots", name: "Admin-Available-Slots", component: adminAvailableSlotsView}
]

const router = VueRouter.createRouter({
    // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
    history: VueRouter.createWebHashHistory(),
    routes, // short for `routes: routes`
})

app.use(router);

app.mount("#app");


