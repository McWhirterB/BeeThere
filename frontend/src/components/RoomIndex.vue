<template>
  <div class="rooms-container">
    <v-container>
      <v-row>
        <v-col
          v-for="room in apiResponse"
          :key="room.id"
          cols="12"
          sm="6"
          md="4"
        >
          <v-card class="pa-3" outlined>
            <v-card-title class="text-h6">
              {{ room.building }} – {{ room.roomNumber }}
            </v-card-title>

            <v-card-subtitle>
              {{ room.type.toUpperCase() }} | {{ room.seatCount }} seats
            </v-card-subtitle>

            <v-card-text>
              <p><strong>Location:</strong> {{ room.location }}</p>
              <p><strong>Building:</strong> {{ room.building }}</p>
              <p><strong>Room:</strong> {{ room.roomNumber }}</p>
              <p><strong>Type:</strong> {{ room.type }}</p>
              <p><strong>Seats:</strong> {{ room.seatCount }}</p>
            </v-card-text>

            <v-card-actions>
              <v-btn color="primary" @click="openRoom(room.id)">
                View Room
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

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
			};
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
				this.snackbar.showSnackbar('Swag!', 'success');
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
				this.snackbar.showSnackbar('Swag!', 'success');
			} catch (error) {
				console.error('error fetching data: ', error);
				this.snackbar.showSnackbar('Error retrieving room data', 'error');
			}
		}	
	}
</script>
