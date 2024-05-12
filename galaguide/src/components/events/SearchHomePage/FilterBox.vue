<template>
    <div :class="filterBox">
        <button class="filterBoxButton" @click="toggleFilters">
            Filter Box
        </button>
        <div :class="filters" v-show="showFilters">
            <div class="dropdown">
                <label class="label">Placeholder</label>
                <img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
            </div>
            <div class="dropdown">
                <label class="label">Placeholder</label>
                <img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
            </div>
            <div class="dropdown">
                <label class="label">Placeholder</label>
                <img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
            </div>
            <div class="dropdown">
                <label class="label">Placeholder</label>
                <img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
            </div>
            <!-- Repeat dropdown structure for each filter as needed -->
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';

export default {
    name: "FilterBox",
    setup() {
        const showFilters = ref(false);
        const toggleFilters = () => {
            showFilters.value = !showFilters.value;
        };
        return { showFilters, toggleFilters };
    }
};
</script>

<style scoped>
.filterBox {
    position: relative;
    width: 100%;
    height: 100%;
}

.filterBoxButton {
    cursor: pointer;
    background-color: #54bb87;
    /* Adjust to match your theme color */
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    height: 200%;
    border-color: #f0f0f0;
}

.filters {
    
    background-color: #f0f0f0;
    /* Light background for dropdown items */
    padding: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.dropdown {
    margin-bottom: 10px;
    background-color: beige;
    padding: 10px;
    margin: 5px;
    border-radius: 15px;
    display: inline-block;
    vertical-align: middle;
}

.label {
    font-size: 16px;
    color: #333;
}

.chevronDownIcon {
    width: 20px;
    /* Size of the chevron icon */
    float: right;
}
</style>