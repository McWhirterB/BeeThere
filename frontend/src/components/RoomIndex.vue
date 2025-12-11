<template>
  <div class="rooms-container d-flex flex-column" style="height: calc(100vh - 64px);">
    <!-- Search Bar - Fixed at top -->
    <v-container class="flex-grow-0 py-4">
      <v-text-field
        v-model="searchQuery"
        label="Search"
        prepend-inner-icon="mdi-magnify"
        variant="outlined"
        density="comfortable"
        clearable
        color="primary"
        hide-details
        rounded="lg"
      />
    </v-container>

    <!-- Scrollable Room Grid -->
    <v-container class="flex-grow-1 overflow-y-auto pb-6">
      <v-row v-if="filteredRooms.length === 0" class="mt-8">
        <v-col cols="12" class="text-center">
          <v-icon size="80" color="grey-lighten-2">mdi-magnify-close</v-icon>
          <p class="text-h6 text-grey mt-4">No rooms found</p>
          <p class="text-body-2 text-grey">Try adjusting your search criteria</p>
        </v-col>
      </v-row>

      <v-row v-else>
        <v-col
          v-for="room in filteredRooms"
          :key="room.id"
          cols="12"
          sm="6"
          md="4"
          lg="3"
        >
          <v-card 
            class="room-card position-relative" 
            elevation="4"
            rounded="lg"
            hover
          >
            <!-- Available Indicator -->
            <v-avatar 
              color="success" 
              size="16" 
              class="availability-indicator"
            ></v-avatar>

            <!-- Room Icon Header -->
            <div class="text-center pt-6 pb-2">
              <v-avatar color="primary" size="64">
                <v-icon size="32" color="white">
                  {{ getRoomIcon(room.type) }}
                </v-icon>
              </v-avatar>
            </div>

            <!-- Room Title -->
            <v-card-title class="text-h6 text-center pb-1">
              {{ room.building }}
            </v-card-title>

            <v-card-subtitle class="text-center pb-3">
              <v-chip 
                size="small" 
                color="primary" 
                variant="tonal"
                class="font-weight-medium"
              >
                Room {{ room.roomNumber }}
              </v-chip>
            </v-card-subtitle>

            <v-divider></v-divider>

            <!-- Room Details -->
            <v-card-text class="pb-4">
              <v-list density="compact" class="bg-transparent">
                <v-list-item density="compact" class="px-0 min-height-auto">
                  <template v-slot:prepend>
                    <v-icon size="20" color="primary">mdi-map-marker</v-icon>
                  </template>
                  <v-list-item-title class="text-body-2">
                    {{ room.country }}
                  </v-list-item-title>
                </v-list-item>

                <v-list-item density="compact" class="px-0 min-height-auto">
                  <template v-slot:prepend>
                    <v-icon size="20" color="primary">mdi-door</v-icon>
                  </template>
                  <v-list-item-title class="text-body-2">
                    {{ capitalizeFirstLetter(room.type) }}
                  </v-list-item-title>
                </v-list-item>

                <v-list-item density="compact" class="px-0 min-height-auto">
                  <template v-slot:prepend>
                    <v-icon size="20" color="primary">mdi-account-group</v-icon>
                  </template>
                  <v-list-item-title class="text-body-2">
                    {{ room.seatCount }} {{ room.seatCount === 1 ? 'seat' : 'seats' }}
                  </v-list-item-title>
                </v-list-item>
              </v-list>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style scoped>
.rooms-container {
  background-color: #fafafa;
}

.room-card {
  transition: all 0.3s ease;
  height: 100%;
}

.room-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(0,0,0,0.15) !important;
}

.availability-indicator {
  position: absolute;
  top: 12px;
  right: 12px;
  border: 2px solid white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.overflow-y-auto {
  overflow-y: auto;
}

.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.min-height-auto {
  min-height: auto !important;
}
</style>

<script>
	import { ref } from 'vue';
	import axios from 'axios';
	import { useSnackbarStore } from '../stores/snackbar-store.ts'
	import { useAuthStore } from '../stores/auth-store.ts'

	const snackbar = useSnackbarStore();
	const auth = useAuthStore();
	export default {
		data () {
			return {
				snackbar: useSnackbarStore(),
				apiResponse: [],
				searchQuery: '',
			};
		},
		computed: {
			filteredRooms() {
				if (!this.searchQuery || this.searchQuery.trim() === '') {
					return this.apiResponse;
				}

				const query = this.searchQuery.toLowerCase().trim();

				return this.apiResponse.filter(room => {
					return (
						room.building?.toLowerCase().includes(query) ||
						room.roomNumber?.toLowerCase().includes(query) ||
						room.location?.toLowerCase().includes(query) ||
						room.country?.toLowerCase().includes(query) ||
						room.type?.toLowerCase().includes(query) ||
						room.seatCount?.toString().includes(query)
					);
				});
			}
		},
		methods: {
			 // success, warning, error
			 openSnackbar() {
				this.snackbar.showSnackbar('Show snackbar', 'success', [
					{ actionName: 'test', do: () => console.log("test") },
					{ actionName: 'test', do: () => this.$router.push('/test') },
				])
			},
			testClick() {
			},
			getRoomIcon(type) {
				const icons = {
					'conference': 'mdi-account-group',
					'office': 'mdi-desk',
					'meeting': 'mdi-calendar-account',
					'classroom': 'mdi-school',
					'lab': 'mdi-flask',
					'studio': 'mdi-palette'
				};
				return icons[type?.toLowerCase()] || 'mdi-door';
			},
			capitalizeFirstLetter(str) {
				if (!str) return '';
				return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
			}
		},
		// happens when component is placed in page
		async mounted() {
			try {
				await axios.get("http://localhost:8080/rooms/", {
															headers: {
																"Bearer": auth.token,
																"Access-Control-Allow-Origin": "*",	
															},
															responseType: "json",
														}).then((response) => {
															this.apiResponse = response.data;
														});
				console.log(this.apiResponse);
				this.snackbar.showSnackbar('Rooms loaded successfully!', 'success');
			} catch (error) {
				console.error('error fetching data: ', error);
				this.snackbar.showSnackbar('Error retrieving room data', 'error');
			}
			try {
				await axios.get("http://localhost:8080/reservations/", {
															headers: {
																"Bearer": auth.token,
																"Access-Control-Allow-Origin": "*",	
															},
															responseType: "json",
														}).then((response) => {
															console.log(response.data)
														});
			} catch (error) {
				console.error('error fetching data: ', error);
				this.snackbar.showSnackbar('Error retrieving reservation data', 'error');
			}
		}	
	}
</script>
