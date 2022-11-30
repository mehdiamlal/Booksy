export var courseCard = {
    props: {
        title: String,
    },
    data: function() {
        var self = this;
        return {
            titleData: self.title
        }
    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <h3 class="text-center">{{titleData}}</h3>
                <p></p>
<!--                <p></p>-->
                <div class="text-center"><button class="btn btn-primary btn-sm">Lista Docenti</button></div>
            </div>
        </div>
    `
}