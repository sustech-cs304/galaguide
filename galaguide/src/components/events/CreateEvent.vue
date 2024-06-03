<template>
  <div style="display: flex;">
    <div style="display: flex; flex-direction: row; width: 10%;">

    </div>
    <div class="create-event">
      <form>
        <div class="form-group">
          <label for="event-title">Event Title</label>
          <input type="text" id="event-title" v-model="formData.title" />
        </div>
        <div class="form-group">
          <label for="event-poster">Event Poster</label>
          <div class="uploaderHolder">
            <ImageUploader :posterId="formData.posterId" />
          </div>
          <div class="my-images">
            <h2>My Images</h2>

            <div class="myImageHolder">
              <h4>Click on images to copy to clipboard</h4>
              <div id="imgs-holder">
                <div v-for="img in myImgs" :key="img" class="single-img">
                  <img :src="'/api/asset/' + img.uuid" alt="File" @click="copyToClickBoard(img.uuid)" />
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
          </div>


        </div>

        <div class="form-group">
          <label for="event-introduction">Event Introduction</label>
          <textarea id="event-introduction" v-model="formData.description"></textarea>
        </div>
        <div class="form-group-horizontal">

          <div class="form-group">
            <label for="event-fee">Fee</label>
            <input type="number" id="event-fee" v-model="formData.cost" />
          </div>
          <div class="form-group">
            <label for="event-category">Category</label>
            <!-- a drop down selection -->
            <select id="event-category" v-model="formData.category">
              <option v-for="option in category_options" :key="option.value" :value="option.value">
                {{ option.label }}
              </option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <!-- periods is a list containing {start:, end: } -->
          <div v-for="(period, index) in formData.periods" :key="index" style="display: flex; align-self: center;">
            <div class="form-group-horizontal">
              <div class="form-group">
                <label :for="'event-start-time-' + index">Event Start Time</label>
                <input type="datetime-local" :id="'event-start-time-' + index" v-model="period.start" />
              </div>
              <div class="form-group">
                <label :for="'event-end-time-' + index">Event End Time</label>
                <input type="datetime-local" :id="'event-end-time-' + index" v-model="period.end" />
              </div>
              <button @click.prevent="removePeriod(index)">Remove Period</button>
            </div>


          </div>

          <button @click.prevent="addPeriod" :disabled="formData.periods.length >= 5">Add Period</button>
        </div>

        <button @click.prevent="createEvent">Create Event</button>
        <CustomAlert :visible="showAlert" title="Congrats!" message="Link copied to clipboard!"
          @close="showAlert = false" />
      </form>
    </div>
  </div>

</template>

<script setup>
import { ref, onMounted, } from 'vue';
import { useRouter } from "vue-router";
import axios from 'axios';
import ImageUploader from '../image/ImageUploader.vue';
import CustomAlert from '../CustomAlert.vue';
const router = useRouter();
const category_options = [
  { label: 'Sport', value: 'Sport' },
  { label: 'Music', value: 'Music' },
  { label: 'Education', value: 'Education' },
  { label: 'Art', value: 'Art' },
  { label: 'Other', value: 'Other' }
];
const formData = ref({
  title: '',
  posterId: '',
  description: '',
  category: '',
  cost: 0,
  periods: [],
});

const createEvent = async () => {
  try {
    console.log(formData.value);
    const response = await axios.post('/api/event/create', formData.value);
    console.log(response.data);
  } catch (error) {
    console.error(error);
  }
};
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

const addPeriod = () => {
  if (formData.value.periods.length < 5) {
    formData.value.periods.push({ start: '', end: '' });
  } else {
    alert('You can add a maximum of 5 periods.');
  }
};

const removePeriod = (index) => {
  formData.value.periods.splice(index, 1);
};
</script>

<style scoped>
.create-event {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 80%;
  margin: auto;
  padding: 20px;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

select {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}

.form-group {
  display: flex;
  flex-direction: column;
  align-self: center;
  width: 80%;
}

.form-group-horizontal {
  display: flex;
  align-self: center;
  width: 80%;
  justify-content: space-between;
  gap: 15px;
}

.form-group label {
  margin-bottom: 5px;
  font-size: 22px;
  font-weight: bold;
  color: #333;
}

.form-group input,
.form-group textarea {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  font-size: 16px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group textarea {
  resize: vertical;
  height: 100px;
}

button {
  align-self: center;
  padding: 10px 20px;
  background-color: #cbe091;
  color: #000;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #a5adba;
}

.uploaderHolder {
  display: flex;
  align-self: center;
  justify-content: center;
  align-items: center;
  width: 50%;
  height: auto;
  border: 1px dashed #353232;
  border-radius: 10px;
  cursor: pointer;
}

.myImageHolder {
  display: flex;
  align-self: center;
  justify-content: center;
  align-items: center;
  width: 90%;
  height: auto;
  border: 1px dashed #353232;
  border-radius: 10px;
  cursor: pointer;
}

.my-images {
  display: flex;
  flex-direction: column;
  align-self: center;
  width: 80%;
  gap: 10px;
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