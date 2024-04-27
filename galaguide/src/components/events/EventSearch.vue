<template>
    <div class="event-center">
        <div class="search-container">
            <SearchBox></SearchBox>
        </div>
        <div class="filter-container">
            <div class="filter-category" v-for="category in filterCategories" :key="category.name">
                <label>{{ category.name }}</label>
                <div class="filter-options">
                    <button v-for="option in category.options" :key="option"
                        :class="{ 'selected': isSelected(category.name, option) }"
                        @click="toggleFilter(category.name, option)">
                        {{ option }}
                    </button>
                </div>
            </div>
            <button @click="applyFilters" style="width: 10%; align-self: center;">Filter</button>
        </div>
        <div class="events-list">
            <!-- Event listing -->
        </div>
        <div class="rank-list">
            <!-- Rank list -->
        </div>
    </div>
</template>

<script>
import SearchBox from './SearchBox.vue';
export default {
    data() {
        return {
            searchQuery: '',
            selectedFilters: {
                Category: [],
                Time: [],
                Price: [],
                // Initialize other categories as needed...
            },
            filterCategories: [
                {
                    name: 'Category',
                    options: ['Sport', 'Music', 'Lecture'],
                },
                {
                    name: 'Time',
                    options: ['Morning', 'Afternoon', 'Evening'],
                },
                {
                    name: 'Price',
                    options: ['Free', 'Paid'],
                },
                // Define more filter categories and their options...
            ],
            // ... other data properties ...
        };
    },

    methods: {
        searchEvents() {
            // TODO() Implement event search
        },
        toggleFilter(category, option) {
            const index = this.selectedFilters[category].indexOf(option);
            if (index > -1) {
                this.selectedFilters[category].splice(index, 1); // Remove filter
            } else {
                this.selectedFilters[category].push(option); // Add filter
            }
        },
        isSelected(category, option) {
            return this.selectedFilters[category]?.includes(option);
        },
        applyFilters() {
            // Method to apply selected filters to the event search
        },
        // ... other methods ...
    },
    components:{
        SearchBox,
    },
    created() {
        // Fetch events data
        const searchQuery = this.$route.query.q;
        if(searchQuery) {
            this.searchQuery = searchQuery;
            this.searchEvents();
        }
    },
};
</script>

<style scoped>
.event-center {
    /* Applying the classic and interactive style from Revised Plan 2 */
    color: #5c5c5c;
    background-color: #fff;
    padding: 20px;
    display: flex;
    flex-direction: column;
    
}

.search-container,
.filter-container {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
    gap:10px;
}

.filter-container button {
    padding: 5px 10px;
    cursor: pointer;
    border: 1px solid #dcdcdc;
    border-radius: 4px;
}
.search-container input[type="text"] {
    font-size: 20;
}

.filter-container .filter {
    display: flex;
    align-items:center;
    margin-bottom: 10px;
}

.events-list .event-item {
    /* Styles for event items */
    border-bottom: 1px solid #dcdcdc;
    padding: 10px 0;
}

.rank-list {
    width: 20%;
}

.search-and-filter {
    display: flex;
    flex-direction: column;
}


.filter-container .filter-category {
    display: flex;
    align-items: center;
    padding-top: 10px;
    padding-bottom: 2px;
}

.filter-container .filter-options button {
    margin-right: 5px;
    /* Additional styles for buttons */
}
.filter-category {
    margin-left: 20px;
    display: flex;
    align-items: center;
    gap: 5px;
}

.filter-category label {
    margin-right: 40px; /* Pushes the label to the left and options to the right */
    font-weight: bold; /* Makes the label bold */
    align-self: center;
}
.filter-options {
  display: flex; 
  gap: 10px; 
}
.filter-options button {
  /* Add styles for your buttons here */
  padding: 5px 10px; /* Example padding, adjust as needed */
  cursor: pointer; /* Changes the mouse cursor on hover */
  border: 1px solid #dcdcdc; /* Example border, adjust as needed */
  border-radius: 4px; /* Example border-radius for rounded corners */
}
.filter-container .filter-options .selected {
    background-color: #dcdcdc;
}
</style>