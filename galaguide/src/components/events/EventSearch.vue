<template>
	<div style="display: flex; flex-direction: row;">
		<div style="width: 15%; display: flex;">
			<h2>This is side bar</h2>
		</div>
		<div class="event-center">
			<div class="search-container">
				<SearchBox></SearchBox>
			</div>
			<div class="filter-container">
				<div class="filter-category" v-for="category in selectedFilters" :key="category.name">
					<label>{{ category.name }}</label>
					<div class="filter-options">
						<button v-for="option in category.options" :key="option"
							:class="{ 'selected': isSelected(category.name, option) }" @click="toggleFilter(category.name, option)">
							{{ option }}
						</button>
					</div>
				</div>
				<button @click="applyFilters" style="width: 10%; align-self: center;">Filter</button>
			</div>
			<div class="events-list">
				<EventCard v-for="event in searchResults" :key="event.id" :event="event" />
			</div>

		</div>
	</div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import axios from 'axios';
import SearchBox from './SearchBox.vue';
import EventCard from './EventCard.vue';
// TODO: implement the search query and filter logic
// const searchQuery = ref('');
const searchQuery = this.$route.query || '';
const selectedFilters = reactive([
	{ name: 'Category', options: ['Sport', 'Music', 'Lecture'], selected: [] },
	{ name: 'Time', options: ['Morning', 'Afternoon', 'Evening'], selected: [] },
	{ name: 'Price', options: ['Free', 'Paid'], selected: [] },
	{ name: 'Status', options: ['Upcoming', 'Ongoing', 'Past'], selected: [] }
]);

const searchResults = ref([]);
// const filteredResults = ref([]);
onMounted(() => {
	fetchEvents();
});

const fetchEvents = () => {
	// axios.get('/api/events')
	// 	.then((response) => {
	// 		searchResults.value = response.data;
	// 	})
	// 	.catch((error) => {
	// 		console.error('Error fetching events:', error);
	// 	});

	if (searchQuery) {
		axios.get(`/api/events?search=${searchQuery}`)
			.then((response) => {
				searchResults.value = response.data;
			})

	}
	else {
		axios.get('/api/events')
			.then((response) => {
				searchResults.value = response.data;
			})
	}
}
const toggleFilter = (category, option) => {
	const selectedCategory = selectedFilters.find((filter) => filter.name === category);
	if (selectedCategory) {
		const index = selectedCategory.selected.indexOf(option);
		if (index > -1) {
			selectedCategory.selected.splice(index, 1);
		} else {
			selectedCategory.selected.push(option);
		}
	}
};

const isSelected = (category, option) => {
	return selectedFilters.find((filter) => filter.name === category)?.selected.includes(option);
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
	width: 80%;

}

.search-container,
.filter-container {
	display: flex;
	flex-direction: column;
	margin-bottom: 20px;
	gap: 10px;
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
	align-items: center;
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
	margin-right: 40px;
	/* Pushes the label to the left and options to the right */
	font-weight: bold;
	/* Makes the label bold */
	align-self: center;
}

.filter-options {
	display: flex;
	gap: 10px;
}

.filter-options button {
	/* Add styles for your buttons here */
	padding: 5px 10px;
	/* Example padding, adjust as needed */
	cursor: pointer;
	/* Changes the mouse cursor on hover */
	border: 1px solid #dcdcdc;
	/* Example border, adjust as needed */
	border-radius: 4px;
	/* Example border-radius for rounded corners */
}

.filter-container .filter-options .selected {
	background-color: #dcdcdc;
}
</style>