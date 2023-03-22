export var bookingView = {
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center">Book a Slot</h1>
            <booking-form></booking-form>
        </div>
    `,
    mounted() {
        var self = this;
        document.title = "Book A Slot | Booksy";
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "studente") {
            self.$router.push("/");
        }
    }
};