export var landingView = {
    template: `
        <navbar></navbar>
        <div class="container text-center">
            <div class="row">
                <div class="col-md"></div>
                <div class="col-md-8" id="landingMain">
                    <img src="./img/favicon.png" alt="logo Booksy" width="75" height="75" style="margin-bottom: 2em">
                    <h1 id="landingHeading"><strong>Boost your academic career &#128640;</strong></h1>
                    <h4 class="text-secondary" style="margin-bottom: 2em">Get started now and book your first slot with one of our tutors.</h4>
                    <div class="btn-group-lg btn-group-horizontal">
                        <router-link to="/login">
                            <button class="btn btn-outline-primary btn-block shadow" style="margin: .5em; width: 10em; height: 3em">Login</button>
                        </router-link>
                        <a href="./register.html">
                            <button class="btn btn-primary btn-block shadow" style="margin: .5em; width: 10em; height: 3em">Register</button>
                        </a>
                    </div>
                </div>
                <div class="col-md"></div>
            </div>
            
        </div>
    `,
    mounted() {
        var self = this;
        document.title = "Booksy | Boost Your Academic Career";
        if(localStorage.getItem("role") !== null) {
            if(localStorage.getItem("role") === "amministratore") {
                self.$router.push("/admin");
            } else if(localStorage.getItem("role") === "studente") {
                self.$router.push("/home");
            }
        }
    }
};