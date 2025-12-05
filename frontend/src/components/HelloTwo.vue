<template>
	<v-btn @click="testClick()">test</v-btn>
	<v-btn @click="openSnackbar()">test</v-btn>
	<v-container>
	<v-row>
		<v-col cols="4">
				<v-card v-if="test">
					<v-card-title class="text-center">Create a Reservation</v-card-title>
					<v-card-text>
						<v-date-picker v-model="dateValue"></v-date-picker>
						<v-text-field :model-value="startTime" readonly variant="outlined" label="Start Time" prepend-inner-icon="mdi-clock-in">
							<v-menu v-model="showStartTimeMenu" :close-on-content-click="false"
											activator="parent" min-width="0">
								<v-time-picker v-model="startTime"></v-time-picker>
							</v-menu>
						</v-text-field>
						<v-text-field :model-value="endTime" readonly variant= "outlined" label="End Time" prepend-inner-icon="mdi-clock-out">
							<v-menu v-model="showEndTimeMenu" :close-on-content-click="false"
											activator="parent" min-width="0">
								<v-time-picker v-model="endTime"></v-time-picker>
							</v-menu>
						</v-text-field>
						<v-row>
							<v-col class="text-center">
								<v-container>
									<v-card>
										<v-list v-for="(room, i) in rsvp.rooms" :key="i">
											<v-list-item>
												<v-list-item-title v-text="room.roomNumber"></v-list-item-title>
											</v-list-item>
										</v-list>
									</v-card>
								</v-container>
								<v-dialog style="width: 80%">
									<template v-slot:activator="{ props: activatorProps }">
										<v-btn text-align="center"
													 v-bind="activatorProps" variant="outlined" text="Add Rooms">Add Rooms</v-btn>
									</template>
									<template v-slot:default="{ isActive }">
										<v-card>
											<v-card-title class="text-center">
												List of Available Rooms
											</v-card-title>
											<v-card-text>
											<v-text-field v-model="roomSearch" variant="outlined" class="text-center"></v-text-field>
												<v-row>
													<v-col>
														<v-data-table :headers="headers" :search="roomSearch" :items="apiResponse">
															<template v-slot:item.actions="{item}">
																<v-btn elevation="0" icon="mdi-plus-box-outline" @click="rsvp.rooms.push(item)"></v-btn>
															</template>
														</v-data-table>
													</v-col>
												</v-row>
											</v-card-text>
										</v-card>
									</template>
								</v-dialog>
							</v-col>
						</v-row>
						<v-br />
						<v-row>
							<v-col class="text-center">
								<v-btn variant="outlined" @click="postReservation()">Create Reservation</v-btn>
							</v-col>
						</v-row>
					</v-card-text>
				</v-card>
				<v-card v-else>
					<v-card-title>Reservation</v-card-title>
					<v-br />
					<v-card-text>
						<v-text-field variant="solo" label="Day of Reservation" prepend-inner-icon="mdi-calendar-account-outline"></v-text-field>
						<v-text-field variant="solo" label="Start Time" prepend-inner-icon="mdi-clock-in"></v-text-field>
						<v-text-field variant="solo" label="End Time" prepend-inner-icon="mdi-clock-out"></v-text-field>
						<v-br />
						<v-row>
							<v-col class="text-center">
								<v-btn size="large" text-align="center">Add Rooms</v-btn>
							</v-col>
						</v-row>
						<v-br />
						<v-row>
							<v-col class="text-center">
								<v-btn size="large">Modify</v-btn>
								<v-btn size="large">Delete</v-btn>
							</v-col>
						</v-row>
					</v-card-text>
				</v-card>
			</v-col>
			<v-col>
				<v-row>
					<v-col>
						<v-card> 
							<v-row>
								<v-col class="text-center">
									<v-btn variant="text" size="large" @click="test = true">List Rooms</v-btn>
									<v-btn variant="text" size="large" @click="test = false">List My Reservations</v-btn>
								</v-col>
							</v-row>
							<v-card-title> </v-card-title>
								<v-card-text>
									<v-table>
										<thead>
											<tr>
												<th>
													Location	
												</th>
												<th>
													Room Number	
												</th>
												<th>
													Seat Count
												</th>
												<th>
													Room Type
												</th>
											</tr>
										</thead>
										<tbody v-for="item in apiResponse" :key="item.id">
											<tr>
												<td>{{ item.location }}</td>
												<td>{{ item.roomNumber }}</td>
												<td>{{ item.seatCount }}</td>
												<td>{{ item.type }}</td>
											</tr>
										</tbody>
									</v-table>
								</v-card-text>
							</v-card>
						</v-col>
					</v-row>
			</v-col>
		</v-row>	
	</v-container>
</template>
<script>
	import { ref } from 'vue';
	import axios from 'axios';
	import { useSnackbarStore } from '../stores/snackbar-store.ts'

	//const snackbar = useSnackbarStore();
	export default {
		data () {
			return {
				test: true,
				snackbar: useSnackbarStore(),
				apiResponse: [],
				headers: [
					{
						align: 'start',
						key: 'location',
						sortable: false,
						title: 'Location',
					},
					{ key: 'roomNumber', title: 'Room Number' },
					{ key: 'seatCount', title: 'Seat Count' },
					{ key: 'type', title: 'Room Type' },
					{ key: 'actions', title: 'Actions' },
				],
				roomSearch: '',
				dateValue: null,
				startTime: null,
				endTime: null,
				showStartTimeMenu: false,
				showEndTimeMenu: false,
				roomsToReserve: [],
				rsvp: {
					reservationId: 44,
					employeeId: 128,
					employeeName: "LuhTyrese",
					startTime: "2025-12-04T15:00:00.000+00:00",
					endTime: "2025-12-04T16:30:00.000+00:00",
					rooms: [],
				},
				//rsvp: {
				//	reservationId: 44,
				//	employeeId: 128,
				//	employeeName: "LuhTyrese",
				//	startTime: "2025-12-04T15:00:00.000+00:00",
				//	endTime: "2025-12-04T16:30:00.000+00:00",
				//	rooms: [
				//		{
				//				location: "Japan",
				//				building: "Tokyo",
				//				roomNumber: "4-220",
				//				type: "conference",
				//				id: 3,
				//				seatCount: 8
				//		},
				//		{
				//				location: "Japan",
				//				building: "Tokyo",
				//				roomNumber: "2-102",
				//				type: "conference",
				//				id: 2,
				//				seatCount: 12
				//		}
				//	],
				//},
			};
		},
		methods: {
			 // success, warning, error
			 openSnackbar() {
				this.snackbar.showSnackbar('Show snackbar', 'success', [
					{ actionName: 'test', link: '/test' },
					{ actionName: 'test', link: '/test' },
				])
				console.log('test');
			},
			testClick() {
				let time = this.startTime.split(":");
				this.dateValue.setHours(time[0]);
				this.dateValue.setMinutes(time[1]);
				this.rsvp.startTime = this.dateValue.toISOString();
				
				time = this.endTime.split(":");
				this.dateValue.setHours(time[0]);
				this.dateValue.setMinutes(time[1]);	
				this.rsvp.endTime = this.dateValue.toISOString();
				console.log(this.rsvp.startTime);
				console.log(this.rsvp.endTime);
			},
			async postReservation() {
				try {
					await axios.post("http://localhost:8080/reservations/", this.rsvp, {
												headers: {
													"Bearer": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJGaXNoYmllIiwibG9jYXRpb24iOiJKYXBhbiIsImlkIjo2NTgsImRlcGFydG1lbnQiOiJMZWdhbCIsInRpdGxlIjoiTWFuYWdlciIsImZpcnN0X25hbWUiOiJKb2xlZW4iLCJzdWIiOiJKb2xlZW4gRmlzaGJpZSIsImlhdCI6MTc2NDk2ODg5NiwiZXhwIjoxNzY0OTcyNDk2fQ.rsAmGit86_PLXOwxcdye0qbyl_wqXP3lp4QBJutziUQ",
													"Access-Control-Allow-Origin": "*",	
												},
												responseType: "json",
											}).then((response) => {
												console.log(response);
											});	
				} catch (error) {
					console.error("unable to post reservation: ", error)
				}	
			}
		},
		// happens when component is placed in page
		async mounted() {
			try {
				await axios.get("http://localhost:8080/rooms/", {
															headers: {
																"Bearer": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJGaXNoYmllIiwibG9jYXRpb24iOiJKYXBhbiIsImlkIjo2NTgsImRlcGFydG1lbnQiOiJMZWdhbCIsInRpdGxlIjoiTWFuYWdlciIsImZpcnN0X25hbWUiOiJKb2xlZW4iLCJzdWIiOiJKb2xlZW4gRmlzaGJpZSIsImlhdCI6MTc2NDk2ODg5NiwiZXhwIjoxNzY0OTcyNDk2fQ.rsAmGit86_PLXOwxcdye0qbyl_wqXP3lp4QBJutziUQ",
																"Access-Control-Allow-Origin": "*",	
															},
															responseType: "json",
														}).then((response) => {
															this.apiResponse = response.data;
														});
				console.log(this.apiResponse);
				//this.snackbar.showSnackbar('
			} catch (error) {
				console.error('error fetching data: ', error);
			}
		}	
	}
</script>
