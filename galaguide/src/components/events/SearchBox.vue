<!-- Search box for event center -->
<template>
  <div class="search-box-container">
    <input type="text" v-model="searchQuery" @keyup.enter="performSearch" placeholder="Search events..."
      class="search-input" />
    <button @click="performSearch" class="search-button">🔍</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
    };
  },
  methods: {
    performSearch() {
      // Logic to perform the search
      console.log('Searching for:', this.searchQuery);
    },
  },
};
</script>

<style scoped>
.search-box-container {
  width: 95%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px;
  /* Adjust as needed */
}

.search-input {
  font-size: large;
  flex-grow: 1;
  padding: 20px;
  border: 1px solid #ccc;
  /* Light grey border */
  border-radius: 4px;
  margin-right: 10px;
  /* Spacing between input and button */
}

.search-button {
  font-size: large;
  padding: 18px 18px;
  border: none;
  background-color: grey;
  /* Dark grey background */
  color: white;
  /* White text */
  border-radius: 4px;
  /* Rounded corners */
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-button:hover {
  background-color: #555;
  /* Slightly lighter grey on hover */
}
</style>
