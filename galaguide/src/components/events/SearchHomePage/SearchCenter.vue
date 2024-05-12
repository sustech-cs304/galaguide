<template>
    <div class="search-center">
        <main class="main-container">
            <div class="main-content">
                <div class="header">
                    <div class="logo-wrapper">
                        <img class="logo-icon" src="@/assets/logo.png" alt="Gala Logo" />
                    </div>
                    <div class="search-bar">
                        <input class="input-field" placeholder="Search for any Gala you like" type="text" />
                        <button class="search-button">Search</button>
                    </div>
                </div>
                <div class="content">
                    <FilterBox />
                </div>
            </div>
        </main>
    </div>
</template>

<style scoped>
.search-center {
    display: flex;
    justify-content: center;
    background-color: #93a9b1;
    /* Matched from the image */
    height: 100vh;
    width: 100%;
}

.main-container {
    width: 80%;
    height: 90%;
    align-items: center;
    margin: auto;
    
}

.main-content {
    background-color: #93a9b1;
    /* Slightly darker to give depth */
    border-radius: 8px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    height: 50%;
    align-self: center;
}

.header {
    display: flex;
    flex-direction: column;
    align-items: center;
    
    justify-content: space-between;
    width: 100%;
    height: auto;
    
}

.logo-wrapper {
    width: 300px;
    /* Adjust based on actual logo size */
}

.logo-icon {
    width: 100%;
}

.search-bar {
    display: flex;
    align-items: center;
    align-self: center;
    width: 80%;
    
}

.input-field {
    flex-grow: 1;
    margin-right: 10px;
    padding: 10px;
    border: none;
    border-radius: 5px;
    font-size: 16px;
}

.search-button {
    background-color: #4CAF50;
    /* Green button */
    border: none;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.search-button:hover {
    background-color: #367C2B;
    /* Darker green on hover */
}

.content {
    background-color: #93a9b1;
    margin-top: 30px;
    height: 30%;
    width: 100%;
    align-self: center;
}
</style>

<script>
import { defineComponent } from "vue";
import FilterBox from "./FilterBox.vue";

export default defineComponent({
    name: "SearchCenter",
    components: { FilterBox },
});
</script>
