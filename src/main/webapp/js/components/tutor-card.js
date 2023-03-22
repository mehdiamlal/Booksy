export var tutorCard = {
    props: {
        name: String,
        surname: String,
        mail: String,
        active: Boolean,
    },
    methods: {
        change_status: function() {
            this.active = !this.active;

            /* Chiamata HTTP che modificherà il dato (stato del docente) anche sul DB */
        }
    },
    template: `
     <div class="card" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">{{name}} {{surname}}</h5>
            <h6 class="card-subtitle">{{mail}}</h6>
            <div class="row justify-content-end">
                <p class="badge badge-success" v-if="active">Active</p>
                <p class="badge badge-danger" v-else>Inactive</p>
            </div>
        </div>
     </div>
    `
}