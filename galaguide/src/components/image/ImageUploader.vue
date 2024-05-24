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
    const token = localStorage.getItem("token");
    if (!token) {
        console.log("No token found");
        return;
    }

    const formData = new FormData();
    const fileInput = document.getElementById('upload');
    const file = fileInput.files[0];
    
    if (!file) {
        console.log("No file selected");
        return;
    }
    console.log(file);
    formData.append('file', file);
    console.log(formData);

    axios.post('/api/asset/upload', formData, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'multipart/form-data'
        }
    }).then((res) => {
        console.log(res.data);
    }).catch((err) => {
        console.log(err);
    });

    console.log("Image uploaded");
    fileInput.value = "";
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
                        <!--<p class="upload-button">Upload</p>-->
                        <svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor" class="bi bi-cloud-upload" viewBox="-4 -4 20 20">
                            <path fill-rule="evenodd" d="M4.406 1.342A5.53 5.53 0 0 1 8 0c2.69 0 4.923 2 5.166 4.579C14.758 4.804 16 6.137 16 7.773 16 9.569 14.502 11 12.687 11H10a.5.5 0 0 1 0-1h2.688C13.979 10 15 8.988 15 7.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 2.825 10.328 1 8 1a4.53 4.53 0 0 0-2.941 1.1c-.757.652-1.153 1.438-1.153 2.055v.448l-.445.049C2.064 4.805 1 5.952 1 7.318 1 8.785 2.23 10 3.781 10H6a.5.5 0 0 1 0 1H3.781C1.708 11 0 9.366 0 7.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383"/>
                            <path fill-rule="evenodd" d="M7.646 4.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V14.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708z"/>
                        </svg>
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