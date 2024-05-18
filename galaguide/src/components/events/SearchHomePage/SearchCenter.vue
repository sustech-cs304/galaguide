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

					<div :class="filterBox">
						<button class="filterBoxButton" @click="toggleFilters">
							Filter Box
						</button>

						<div :class="filters" v-show="showFilters">
							<div class="dropdown">
								<label class="label">C</label>
								<img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
								<select v-model="category" style="display: block;">
									<option v-for="option in category_options" :key="option.value" :value="option.value">
										{{ option.label }}
									</option>
								</select>
							</div>

							<div class="dropdown">
								<label class="label">Event Time</label>
								<img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
								<select v-model="event_time" style="display: block;">
									<option v-for="option in event_time_options" :key="option" :value="option">
										{{ option }}
									</option>
								</select>
							</div>
							<div class="dropdown">
								<label class="label">
									Registration Time
								</label>
								<img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
								<select v-model="event_status" style="display: block;">
									<option v-for="option in event_status_options" :key="option" :value="option">
										{{ option }}
									</option>
								</select>
							</div>
							<div class="dropdown">
								<label class="label">
									Price
								</label>
								<img class="chevronDownIcon" alt="Expand" src="@/assets/chevron-down.svg" />
								<select v-model="price" style="display: block;">
									<option v-for="option in price_options" :key="option" :value="option">
										{{ option }}
									</option>
								</select>
							</div>
							<!-- Repeat dropdown structure for each filter as needed -->
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>
</template>



<script lang="js" setup>
import { ref } from "vue";
//import FilterBox from "./FilterBox.vue";
const category = ref("")
const event_time = ref("")
const event_status = ref("")
const price = ref("")

const category_options = [
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

const event_time_options = ref([
	"Morning",
	"Afternoon",
	"Evening",
	"Night",
]);

const event_status_options = ref([
	"Upcoming",
	"Ongoing",
	"Past",
]);

const price_options = ref([
	"Free",
	"0 - 50",
	"50 - 100",
	"100 - 200",
	"200+",
]);


const showFilters = ref(false);

const toggleFilters = () => {
	showFilters.value = !showFilters.value;
};

</script>


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