<template>
    <div class="admin container">
        <div class="row">
            <div class="jumbotron">
                <h1>Photo</h1>

                <div class="row">
                  <div class="col-sm-10"></div>
                  <div><button  v-on:click="deconnexion()" class="btn-primary col-sm-2">Deconnexion</button></div>
                </div>
            </div>

            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form @submit="addPhoto" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="lat" class="decale">Lattitude</label>
                        <input id="lat" v-model="latitude" class="form-control" type="number" step="0.000001" required>
                    </div>

                    <div class="form-group">
                        <label for="long" class="decale">Longitude</label>
                        <input id="long" v-model="longitude" class="form-control" type="number" step="0.000001"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="img" class="decale">Image</label>
                        <input id="img" class="form-control form-control-file" type="file" accept=".jpg, .jpeg, .png" required>
                    </div>

                    <button id="subm" type="submit" class="btn decale ">Ajouter photo</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
  import confApi from '../configApi'
  import router from '../router'

  export default {

    name: 'photos',
    data () {
      return {
        idmap: "",
        latitude: "",
        longitude: "",
        url: "",
      }
    },
    methods: {
      addPhoto () {
        var fullPath = document.getElementById('img').value;
        if (fullPath) {
          var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
          var filename = fullPath.substring(startIndex);
          if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
            filename = filename.substring(1);
          }
        }
        var photo = {
          "latitude": this.latitude.toString(),
          "longitude": this.longitude.toString(),
          "url": filename
        }

        var formData = new FormData();
        var imagefile = document.querySelector('#img');
        formData.append("img", imagefile.files[0]);
        confApi.post('file/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': localStorage.getItem("token")
          }
        }).then((response) => {

          confApi.post('/photos/?idMap=' + this.$route.params.idMap, photo, {headers: {Authorization: localStorage.getItem("token")}}).then((response) => {

            this.latitude = '';
            this.longitude = '';
            this.url = '';
            alert("Photo ajoutée")
            location.reload()
          }).catch((error) => {
            localStorage.removeItem("token")
            alert("Veuillez vous reconnecter")
            router.push('/')
          })

        }).catch((error) => {
          alert("Une erreur est survenue")
        })
      },
      deconnexion(){
        localStorage.removeItem("token")
        router.push('/')
      }
    }
  }
</script>
