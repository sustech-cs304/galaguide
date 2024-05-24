<script setup>
import { onMounted, ref } from 'vue';
import ImageUploader from './ImageUploader.vue';
import CustomAlert from "../../components/CustomAlert.vue";
import { useRoute } from "vue-router";
import axios from 'axios';
const router = useRoute();

const spaceUsed = ref(0);
const spaceTotal = ref(1000);
const imagesUploaded = ref(0);
const myImgs = ref([
    {
        src: "https://picsum.photos/800/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/600/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/500/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/800/500",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/400/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/400/500",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/500/500",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/800/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/800/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/800/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/800/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
    {
        src: "https://picsum.photos/800/600",
        uploadTime: "2024-12-01 15:38",
        size: "1.88"
    },
]);
const showAlert = ref(false);

onMounted(() => {
    console.log('ImageHost component is mounted');
    const token = localStorage.getItem("token");
    if (!token) {
        router.push("/login");
    }
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    axios.get("/api/image/host").then((res) => {
        console.log(res.data);
        spaceUsed.value = res.data.data;
    }).catch((err) => {
        console.log(err);
    });
});

const copyToClickBoard = (src) => {
    navigator.clipboard.writeText(src);
    showAlert.value = true;
}
</script>

<template>
    <div id="image-host">
        <h1>Image Hosting Service</h1>
        <ImageUploader />
        <div id="usage-info">
            <h2>Usage</h2>
            <p>Space Used: {{ spaceUsed }}MB / {{ spaceTotal }}MB</p>
            <p>Images Uploaded: {{ imagesUploaded }}</p>
            <h2>Uploading Guidelines</h2>
            <p>1. All uploaded images must adhere to relevant laws.</p>
            <p>2. This service is primarily for use within GalaGuide.</p>
        </div>
        <div id="my-images">
            <h2>My Images</h2>
            <h4>Click on images to copy to clipboard</h4>
            <div id="imgs-holder">
                <div v-for="img in myImgs" :key="img" class="single-img" @click="copyToClickBoard(img.src)">
                    <img :src="img.src" alt="" srcset="" />
                    <p>{{ img.uploadTime }}</p>
                    <p>{{ img.size }}MB</p>
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
</style>