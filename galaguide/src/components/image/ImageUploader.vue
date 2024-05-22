<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';


onMounted(() => {
    console.log('ImageUploader component is mounted');
});

const src = ref("");


const afterRead = (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    if (!file) {
        src.value = "";
        return;
    }
    reader.readAsDataURL(file);
    reader.onload = () => {
        src.value = reader.result;
    };
}

const uploadImage = () => {
    // get token from local storage 
    // use jwt scheme to post
    const token = localStorage.getItem("token");
    if (!token) {
        console.log("No token found");
        return;
    }
    axios.post('/api/upload-image', {
        image: src.value
    }, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    }).then((res) => {
        console.log(res.data);
    }).catch((err) => {
        console.log(err);
    });
    console.log("Image uploaded");
    src.value = "";
    document.getElementById("upload").value = "";
    window.location.reload();
}
</script>

<template>
    <div id="image-uploader">
        <div id="image-choosing">
            <h2>Choose an Image</h2>
            <input 
                type="file" 
                @change="afterRead"
                ref="updata"
                accept="image/*" 
                style="display: none;"
                id="upload"
            />
            <label for="upload">
                <div class="laber-up">
                    <div v-show="src">
                        <img :src="src" alt="" srcset="" />
                    </div>
                    <div v-show="!src">
                        <p class="upload-button">Upload</p>
                    </div>
                </div>
            </label>
            <p class="upload-button" v-show="src" @click="uploadImage">Confirm Upload</p>
        </div>
    </div>
</template>

<style>
.laber-up {
    width: 100%;
    height: 100%;
}

.laber-up>div {
    width: 100%;
    height: 100%;
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: relative;
}

.laber-up>div>img{
    height: 150px;
}

.upload-button {
    margin-left: 10px;
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    border-radius: 10px;
}

.upload-button:hover {
    background-color: rgb(49, 207, 13);
}


</style>