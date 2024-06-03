<template>
	<div style="display: flex; flex-direction: row;">
		<div style="display: flex; width: 8%;"></div>
		<div class="search-center">
			<main class="main-container">
				<div class="main-content">
					<div class="header">
						<div class="logo-wrapper">
							<img class="logo-icon" src="@/assets/logo.png" alt="Gala Logo" />
						</div>
						<div class="search-bar">
							<input class="input-field" v-model="searchQuery" placeholder="Search for any Gala you like" type="text" />
							<button class="search-button" @click="searchGala">Search</button>
						</div>
					</div>

					<div class="content">
						<div :class="filterBox">
							<button class="filterBoxButton" @click="toggleFilters">
								Filter Box
							</button>

							<div :class="filters" v-show="showFilters">
								<div class="dropdown">
									<label class="label">Category</label>
									<select v-model="category" style="display: block;">
										<option v-for="option in categoryOptions" :key="option.value" :value="option.value">
											{{ option.label }}
										</option>
									</select>
								</div>

								<div class="dropdown">
									<label class="label">Start Date</label>
									<input type="date" v-model="startDate" style="display: block;">

								</div>

								<div class="dropdown">
									<label class="label">End Date</label>
									<input type="date" v-model="endDate" style="display: block;">
								</div>


								<div class="dropdown">
									<label class="label">Min Price</label>
									<input type="number" v-model="minPrice" style="display: block;">
								</div>

								<div class="dropdown">
									<label class="label">Max Price</label>
									<input type="number" v-model="maxPrice" style="display: block;">
								</div>
							</div>
							<div>
								<span v-if="(minPrice > maxPrice) && showFilters" class="error">The max price must be greater than the
									min
									price.</span>
								<br>
								<span v-if="(startDate > endDate) && showFilters" class="error">The end date must be later than the
									start
									date.</span>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
</template>



<script lang="js" setup>
import router from "@/router";
import { ref, } from "vue";
const category = ref("")
//initialize start date and end date with the current date
const startDate = ref(new Date().toISOString().split('T')[0])
const endDate = ref(new Date().toISOString().split('T')[0])
const minPrice = ref(0)
const maxPrice = ref(0)
const searchQuery = ref("")

const categoryOptions = [
	{
		value: "Sports",
		label: "Sports",
	},
	{
		value: "Arts & Music",
		label: "Arts & Music",
	},
	{
		value: "Food & Drink",
		label: "Food & Drink",
	},
	{
		value: "Lecture",
		label: "Lecture",
	},
	{
		value: "Others",
		label: "Others",
	},
];



const showFilters = ref(false);

const toggleFilters = () => {
	showFilters.value = !showFilters.value;
};

// Search for Gala
const searchGala = () => {
	// the user will be pushed to search result page with the search query
	// the search query will be the values of the filters
	console.log('query', searchQuery.value, category.value, startDate.value, endDate.value, minPrice.value, maxPrice.value)
	router.push({
		name: "SearchResult",
		query: {
			searchQuery: searchQuery.value,
			category: category.value,
			startDate: startDate.value,
			endDate: endDate.value,
			minPrice: minPrice.value,
			maxPrice: maxPrice.value,
		},
	});
	
};

</script>


<style scoped>
/*
* AI-generated-content
* tool: Copilot
* version: latest
* usage: CSS Modify
* weight: part of
*/
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
	animation: fadeIn 1s ease-in-out;
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
	animation: popIn 1s ease-in-out;
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
	animation: slideDown 1s ease-in-out;
	transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.filterBoxButton:hover {
	transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
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
	animation: popIn 1s ease-in-out;
}

.label {
	font-size: 16px;
	color: #333;
}

.chevronDownIcon {
	width: 20px;
	/* Size of the chevron icon */
	float: right;
	align-self: center;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}

@keyframes slideDown {
    from {
        transform: translateY(-20px);
        opacity: 0;
    }
    to {
        transform: translateY(0);
        opacity: 1;
    }
}

@keyframes popIn {
    from {
        transform: scale(0.9);
        opacity: 0;
    }
    to {
        transform: scale(1);
        opacity: 1;
    }
}
</style>