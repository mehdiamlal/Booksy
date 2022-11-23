import {loginForm} from "./login-form.js";
const app = Vue.createApp({
        data: function () {
            return {
                message: "hello world!"
            }
        }
});

app.component("login-form", loginForm);

app.mount("#app");


