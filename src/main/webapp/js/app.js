import {loginForm} from "./components/login-form.js";
import {bookingForm} from "./components/booking-form.js";
import {bookingView} from "./views/booking-view.js";
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

app.mount("#app");


