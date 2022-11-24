import {loginForm} from "./components/login-form.js";
import {bookingForm} from "./components/booking-form.js";
const app = Vue.createApp({
        data: function () {
            return {
                message: "hello world!"
            }
        }
});

app.component("login-form", loginForm);
app.component("booking-form", bookingForm);

app.mount("#app");


