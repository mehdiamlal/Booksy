export var loginView = {
    template: `
        <navbar></navbar>
        <login-form></login-form>
        <div class="row" style="margin: 3em">
            <div class="col"></div>
            <div class="col text-center">
                <a href="https://play.google.com/">
                    <img src="./img/android.png" alt="android-store" width="160" height="52">
                </a>
            </div>
            <div class="col"></div>
        </div>
    `,
    mounted() {
        document.title = "Accedi | Booksy";
        if(localStorage.getItem("role") === "studente") {
            this.$router.push("/home");
        } else if(localStorage.getItem("role") === "amministratore") {
            this.$router.push("/admin");
        }
    }
};