<script setup>
import { onMounted, ref } from 'vue';
import ImageUploader from './ImageUploader.vue';
import CustomAlert from "../../components/CustomAlert.vue";
import { useRoute } from "vue-router";
import axios from 'axios';
const router = useRoute();

const spaceUsed = ref(0);
const spaceTotal = ref(10000);
const imagesUploaded = ref(0);
const myImgs = ref([]);
const showAlert = ref(false);

onMounted(() => {
    console.log('ImageHost component is mounted');
    const token = localStorage.getItem("token");
    if (!token) {
        router.push("/login");
    }
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    axios.get("/api/asset").then((res) => {
        console.log(res.data);
        myImgs.value = res.data.data;
        imagesUploaded.value = res.data.data.length;
        spaceUsed.value = 0;
        res.data.data.forEach((img) => {
            spaceUsed.value += img.size;
        });
    }).catch((err) => {
        console.log(err);
    });
});

const copyToClickBoard = (src) => {
    navigator.clipboard.writeText(
        window.location.origin + '/api/asset/' +
        src);
    showAlert.value = true;
}

const deleteImage = (uuid) => {
    axios.delete(`/api/asset/${uuid}`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
    }).then((res) => {
        console.log(res.data);
        window.location.reload();
    }).catch((err) => {
        console.log(err);
    });
}
</script>

<template>
    <div id="image-host">
        <h1>Image Hosting Service</h1>
        <ImageUploader />
        <div id="usage-info">
            <h2>Usage</h2>
            <p>Space Used: {{ spaceUsed / 1024 }}KB / {{ spaceTotal }}KB</p>
            <p>Images Uploaded: {{ imagesUploaded }}</p>
            <h2>Uploading Guidelines</h2>
            <p>1. All uploaded images must adhere to relevant laws.</p>
            <p>2. This service is primarily for use within GalaGuide.</p>
        </div>
        <div id="my-images">
            <h2>My Images</h2>
            <h4>Click on images to copy to clipboard</h4>
            <div id="imgs-holder">
                <div v-for="img in myImgs" :key="img" class="single-img">
                    <img :src="'/api/asset/' + img.uuid" alt="File" @click="copyToClickBoard(img.uuid)"/>
                    <p><strong>{{ img.fileName }}</strong></p>
                    <p><strong>{{ img.size / 1024 }}&nbsp;</strong>KB</p>
                    <p class="delete-button" @click="deleteImage(img.uuid)">
                        Delete
                    </p>
                    <p class="copy-button" @click="copyToClickBoard(img.uuid)">
                        Copy
                    </p>
                </div>
            </div>
        </div>
        <CustomAlert 
            :visible="showAlert" 
            title="Congrats!" 
            message="Link copied to clipboard!" 
            @close="showAlert = false"
        />
    </div>
</template>

<style>
#image-host {
    position: absolute;
    top: 10%;
    left: 10%;
    width: 90%;
    height: 100%;
    overflow: scroll;
}

#usage-info {
    position: absolute;
    top: 10%;
    left: 55%;
    width: 40%;
    height: 30%;
    background-color: white;
    border-radius: 10px;
    padding: 20px;
}

#image-uploader {
    position: absolute;
    top: 10%;
    left: 5%;
    width: 45%;
    height: 30%;
    background-color: white;
    border-radius: 10px;
    padding: 20px;
}

#my-images {
    position: absolute;
    top: 50%;
    left: 5%;
    width: 90%;
    border-radius: 10px;
    background-color: white;
    padding: 20px;
}

#imgs-holder {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    flex-wrap: wrap;
}

.single-img {
    margin-top: 4px;
    background: rgb(234, 234, 234);
    border-radius: 5px;
    transition-duration: 0.4s;
}

.single-img:hover {
    box-shadow: 0 0 30px rgba(82, 81, 81, 0.7);
}

#imgs-holder>div>img {
    height: 150px;
    margin: 5px;
}

.delete-button {
    position: relative;
    width: 80%;
    left: 10%;
    background-color: red;
    color: white;
    text-align: center;
    border-radius: 5px;
    cursor: pointer;
    transition-duration: 0.4s;
}

.delete-button:hover {
    background-color: rgb(224, 2, 2);
}

.copy-button {
    position: relative;
    width: 80%;
    left: 10%;
    background-color: blue;
    color: white;
    text-align: center;
    border-radius: 5px;
    cursor: pointer;
    transition-duration: 0.4s;
}

.copy-button:hover {
    background-color: rgb(2, 2, 224);
}
</style>