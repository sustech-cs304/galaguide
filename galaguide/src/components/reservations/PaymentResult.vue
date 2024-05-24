<!-- this page shows the payment result: succeed or fail -->
<template>
  <div>
    <div class="success" v-if="paySucceed === true">
      <h1>Payment Succeed</h1>
      <p>Thank you for your payment</p>
    </div>
    <div class="fail" v-else>
      <h1>Payment Failed</h1>
      <p>Sorry, your payment failed</p>
      <!--  Check a list of possible failed reasons-->
      <div class="fail-reason">
        <h2>Reasons:</h2>
        <ul v-for="reason in failReasons" :key="reason">
          <li>{{ reason }}</li>
        </ul>
      </div>
    </div>
  </div>


  <!-- a buttom back to events -->
  <router-link to="/events">Back to Events</router-link>


</template>

<script setup>
import { ref } from 'vue';
const paySucceed = ref(true);
const failReasons = ref([
  'Insufficient funds',
  'Invalid card number',
  'Card expired',
  'Payment declined',
  'Payment timeout'
]);

<style scoped>
</style>