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
				<!-- let user input min and max price -->
				<div class="filter-category">
					<label>Price</label>
					<input type="number" v-model="minPrice" placeholder="Min" />
					<input type="number" v-model="maxPrice" placeholder="Max" />
				</div>
				<!-- let user input min and max date -->
				<div class="filter-category">
					<label>Date</label>
					<input type="date" v-model="startDate" />
					<input type="date" v-model="endDate" />
				</div>
				<button @click="applyFilters" style="width: 10%; align-self: center;">Filter</button>
			</div>
			<div class="events-list">
				<div v-for="event in searchResults" :key="event.id" class="event-item">
					<EventCard :title="event.title" :posterId="event.posterId" :hostId="event.hostId" />
				</div>
			</div>

		</div>
	</div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import axios from 'axios';
import SearchBox from './SearchBox.vue';
import EventCard from './EventCard.vue';
import { useRoute } from 'vue-router';
// TODO: implement the search query and filter logic
// const searchQuery = ref('');

const route = useRoute();
const query = route.query;

const selectedFilters = reactive([
	{ name: 'Category', options: ['Sports', 'Music', 'Lecture', 'Education'], selected: [] },
]);
const searchQuery = ref("")
const minPrice = ref(0);
const maxPrice = ref(1000);
const startDate = ref(1);
const endDate = ref(2000000000000000);
const category = ref("")
const searchResults = ref([]);

// const filteredResults = ref([]);
onMounted(() => {
	console.log(query)
	//selectedFilters[0].selected = query.category ? query.category : [];
	searchQuery.value = query.searchQuery;
	minPrice.value = query.minPrice;
	maxPrice.value = query.maxPrice;
	category.value = query.category;
	startDate.value = query.startDate;
	endDate.value = query.endDate;
	// convert minPrice and maxPrice to number
	minPrice.value = parseInt(minPrice.value);
	maxPrice.value = parseInt(maxPrice.value);
	startDate.value = Math.min(parseInt(startDate.value), new Date(startDate.value).getTime() / 1000);
	endDate.value = Math.max(parseInt(endDate.value), new Date(endDate.value).getTime() / 1000);
	fetchEvents();
});

const fetchEvents = () => {
	const token = localStorage.getItem("token");
	if (token) {
		axios.defaults.headers.common["Bearer"] = token;
	}
	// axios.get('/api/event')
	// 	.then((response) => {
	// 		searchResults.value = response.data.data;
	// 	})
	// 	.catch((error) => {
	// 		console.error('Error fetching events:', error);
	// 	});
	// convert category to []
	
	console.log(searchQuery.value)
	console.log(category.value)
	console.log(minPrice.value)
	console.log(maxPrice.value)
	console.log(startDate.value)
	console.log(endDate.value)
	console.log(category.value)


	// add the filter values inside post body
	axios.post('/api/event/filter', {
		searchQuery: searchQuery.value,
		category: category.value,
		minPrice: minPrice.value,
		maxPrice: maxPrice.value,
		startDate: startDate.value,
		endDate: endDate.value
	})
	.then((response) => {
		console.log(response.data)
		searchResults.value = response.data.data;
	})


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
/*
* AI-generated-content
* tool: Copilot
* version: latest
* usage: CSS Modify
* weight: part of
*/
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