<template>
    <div v-if="visible" class="custom-alert">
      <div class="alert-content">
        <h2>{{ title }}</h2>
        <p>{{ message }}</p>
        <button @click="closeAlert">{{ buttonText }}</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      visible: Boolean,
      title: String,
      message: String,
      buttonText: {
        type: String,
        default: "OK"
      }
    },
    methods: {
      closeAlert() {
        this.$emit("close");
      }
    }
  };
  </script>
  
  <style scoped>
  .custom-alert {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    transition-duration: 0.3s;
  }
  
  .alert-content {
    text-align: center;
  }
  
  button {
    padding: 10px 20px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  </style>
  