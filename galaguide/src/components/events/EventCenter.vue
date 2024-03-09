<template>
  <div class="container">
    <div class="sidebar-space">
      <h1>This is sidebar!</h1>
      <!-- sidebar content here -->
    </div>
    <div class="middle-container">
      <EventColumn></EventColumn>
    </div>
    <div class="right-container">
      <!-- Structure similar to 'Upcoming' for these sections -->
      <!-- Recommend Events Section -->
      <!-- Search Section -->
      <!-- Favorite Section -->
    </div>
  </div>
</template>

<script>
import EventColumn from './EventColumn.vue'
export default {
  components: { EventColumn, },
  
};
</script>

<style scoped>
  .container {
    display: flex;
    height: 100%;
  }


  
  .middle-container {
    width: 50%;
    display: flex;
    flex-wrap: wrap; /* Allows items to wrap as needed */
    justify-content: space-between;
  }

  .sidebar-space {
    width: 10%; /* Adjusted to take the full width on its own line if necessary */
    /* Additional styling as needed */
  }

  .right-container {
    width: 20%; /* Adjusted width for the right-side container */
    display: flex;
    flex-direction: column;
    justify-content: space-between; /* Spacing between sections */
    padding: 10px; /* Padding around the sections */
  }
</style>
