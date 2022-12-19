export var bookingView = {
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center">Prenota una ripetizione</h1>
            <booking-form></booking-form>
        </div>
    `,
    mounted() {
        var self = this;
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "studente") {
            self.$router.push("/");
        }
    }
};