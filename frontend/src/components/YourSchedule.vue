<template>
  <v-row class="fill-height">
    <v-col>
      <v-sheet height="675">
        <v-calendar
          ref="calendar"
          v-model="value"
          :event-color="getEventColor"
          :event-ripple="false"
          :events="events"
          color="primary"
          type="week"
					@click:event="onEventClick"
          @mousedown:time="startTime"
        >
					<template #event="{ event, timed, eventSummary }">
						<div
							class="my-event"
							@mousedown.stop
							@click.stop="onEventClick({ event })"
						>
							<div class="v-event-draggable">
								<component :is="eventSummary"></component>
							</div>
						</div>
					</template>
        </v-calendar>
				<v-dialog v-model="addReservationDialog" max-width="800"> 
					<template v-slot:default="{ isActive }">
						<v-card elevation="4" rounded="lg">
							<!-- Header -->
							<v-card-title class="bg-grey-lighten-4 py-4">
								<div class="d-flex align-center justify-space-between">
									<div class="d-flex align-center">
										<v-icon class="mr-2" color="primary">mdi-calendar-plus</v-icon>
										<span class="text-h6">New Meeting</span>
									</div>
									<v-btn 
										icon 
										variant="text"
										size="small"
										@click="addReservationDialog=false"
									>
										<v-icon>mdi-close</v-icon>
									</v-btn>
								</div>
							</v-card-title>

							<v-divider></v-divider>

							<v-card-text class="pa-6">
								<!-- Date & Time -->
								<v-row class="mb-4">
									<v-col cols="12">
										<v-text-field 
											v-model="rsvpDate" 
											type="date" 
											label="Meeting Date"
											variant="outlined"
											density="comfortable"
											prepend-inner-icon="mdi-calendar"
											hide-details
										></v-text-field>	
									</v-col>
								</v-row>
								<v-row class="mb-4">
									<v-col cols="12" md="6">
										<div class="text-body-2 font-weight-medium text-grey-darken-2 mb-2">Start Time</div>
										<v-time-picker 
											v-model="rsvpStartTime"
											width="100%"
											elevation="1"
											color="primary"
										></v-time-picker>
									</v-col>
									<v-col cols="12" md="6">
										<div class="text-body-2 font-weight-medium text-grey-darken-2 mb-2">End Time</div>
										<v-time-picker 
											v-model="rsvpEndTime"
											width="100%"
											elevation="1"
											color="primary"
										></v-time-picker>
									</v-col>
								</v-row>

								<!-- Rooms Section -->
								<v-divider class="mb-4"></v-divider>
								
								<div class="d-flex align-center justify-space-between mb-3">
									<div class="text-body-2 font-weight-medium text-grey-darken-2">
										Meeting Rooms
										<v-chip 
											v-if="rsvp.rooms.length > 0"
											class="ml-2" 
											color="success" 
											variant="flat"
											size="x-small"
										>
											{{ rsvp.rooms.length }}
										</v-chip>
									</div>
									<v-menu v-model="menu" :close-on-content-click="false" location="bottom">
										<template #activator="{ props }">
											<v-btn 
												v-bind="props" 
												variant="tonal" 
												color="primary" 
												size="small"
												prepend-icon="mdi-plus" 
												@click="getRoomsByAvailability()"
											> 
												Add Rooms 
											</v-btn>
										</template>

										<v-card width="380" max-height="420" class="d-flex flex-column" elevation="4">
											<v-card-title class="text-subtitle-2 bg-grey-lighten-4 py-3">
												<v-icon start size="small">mdi-office-building</v-icon>
												Available Rooms
											</v-card-title>

											<v-divider></v-divider>

											<div style="overflow-y: auto; flex: 1;">
												<v-expansion-panels flat variant="accordion">
													<v-expansion-panel
														v-for="room in rooms"
														:key="room.id"
														:class="{ 'bg-green-lighten-5': isSelected(room) }"
													>
														<v-expansion-panel-title class="py-2">
															<div class="d-flex align-center justify-space-between w-100">
																<div class="d-flex align-center">
																	<v-icon 
																		color="green" 
																		size="x-small" 
																		class="mr-2"
																	>
																		mdi-circle
																	</v-icon>
																	<span class="text-caption text-success mr-2">Available</span>
																	<span class="text-body-2 font-weight-medium">{{ room.building }} - {{ room.roomNumber }}</span>
																</div>
																<v-btn 
																	icon 
																	size="x-small" 
																	variant="flat"
																	:color="isSelected(room) ? 'success' : 'grey-lighten-1'"
																	@click.stop="toggleRoom(room)"
																>
																	<v-icon size="small">
																		{{ isSelected(room) ? "mdi-check-circle" : "mdi-plus-circle-outline" }}
																	</v-icon>
																</v-btn>
															</div>
														</v-expansion-panel-title>
														<v-expansion-panel-text>
															<v-list density="compact" class="bg-transparent py-0">
																<v-list-item v-if="room.location" class="px-0 py-1" density="compact">
																	<template #prepend>
																		<v-icon size="x-small" color="primary" class="mr-2">mdi-map-marker</v-icon>
																	</template>
																	<div class="d-flex justify-space-between w-100">
																		<span class="text-caption text-grey-darken-1">Location:</span>
																		<span class="text-caption font-weight-medium">{{ room.location }}</span>
																	</div>
																</v-list-item>
																
																<v-list-item class="px-0 py-1" density="compact">
																	<template #prepend>
																		<v-icon size="x-small" color="primary" class="mr-2">mdi-folder-table</v-icon>
																	</template>
																	<div class="d-flex justify-space-between w-100">
																		<span class="text-caption text-grey-darken-1">Type:</span>
																		<span class="text-caption font-weight-medium text-capitalize">{{ room.type || 'Standard' }}</span>
																	</div>
																</v-list-item>
																
																<v-list-item class="px-0 py-1" density="compact">
																	<template #prepend>
																		<v-icon size="x-small" color="primary" class="mr-2">mdi-seat</v-icon>
																	</template>
																	<div class="d-flex justify-space-between w-100">
																		<span class="text-caption text-grey-darken-1">Capacity:</span>
																		<span class="text-caption font-weight-medium">{{ room.seatCount }} seats</span>
																	</div>
																</v-list-item>
															</v-list>
														</v-expansion-panel-text>
													</v-expansion-panel>
												</v-expansion-panels>

												<div v-if="rooms.length === 0" class="text-center pa-6">
													<v-icon size="48" color="grey-lighten-1">mdi-calendar-remove</v-icon>
													<p class="text-caption text-grey mt-2">No rooms available for the selected time</p>
												</div>
											</div>

											<v-divider></v-divider>

											<v-card-actions class="px-3 py-2">
												<v-chip 
													v-if="rsvp.rooms.length > 0" 
													color="success" 
													variant="flat"
													size="x-small"
												>
													{{ rsvp.rooms.length }} selected
												</v-chip>
												<v-spacer></v-spacer>
												<v-btn variant="text" size="small" @click="menu = false">Close</v-btn>
											</v-card-actions>
										</v-card>
									</v-menu>
								</div>

								<!-- Selected Rooms List -->
								<div v-if="rsvp.rooms.length === 0" class="text-center py-6">
									<v-icon size="48" color="grey-lighten-2">mdi-office-building-outline</v-icon>
									<p class="text-caption text-grey mt-2">No rooms selected yet</p>
								</div>

								<v-list v-else class="bg-grey-lighten-5 rounded pa-2">
									<v-list-item
										v-for="(room, i) in rsvp.rooms" 
										:key="i"
										class="rounded mb-1"
										density="comfortable"
									>
										<template #prepend>
											<v-avatar color="primary" size="32">
												<v-icon size="18" color="white">mdi-domain</v-icon>
											</v-avatar>
										</template>
										<v-list-item-title class="text-body-2 font-weight-medium">
											{{ room.building }}
										</v-list-item-title>
										<v-list-item-subtitle class="text-caption">
											Room {{ room.roomNumber }}
										</v-list-item-subtitle>
										<template #append>
											<v-btn 
												icon
												size="x-small" 
												variant="text"
												@click="toggleRoom(room)"
											>
												<v-icon size="small">mdi-close</v-icon>
											</v-btn>
										</template>
									</v-list-item>
								</v-list>
							</v-card-text>

							<v-divider></v-divider>

							<!-- Footer Actions -->
							<v-card-actions class="px-6 py-4">
								<v-spacer></v-spacer>
								<v-btn 
									variant="text"
									@click="addReservationDialog=false"
								>
									Cancel
								</v-btn>
								<v-btn 
									color="primary" 
									variant="tonal"
									@click="postReservation()"
								>
									Create Reservation
								</v-btn>
							</v-card-actions>
						</v-card>
					</template>
				</v-dialog>
			<v-dialog v-model="reservationInfoDialog" max-width="700">
  <v-card elevation="4" rounded="lg">
    <v-card-title class="bg-grey-lighten-4 py-4">
      <div class="d-flex align-center">
        <v-icon class="mr-2" color="primary">mdi-pencil</v-icon>
        <span class="text-h6">Edit Reservation</span>
      </div>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text class="pa-6">
      <!-- Employee Information -->
      <v-row class="mb-2">
        <v-col cols="12" md="6">
          <v-text-field
            label="Employee Name"
            v-model="selectedReservation.data.employeeName"
            variant="outlined"
            density="comfortable"
            hide-details
          />
        </v-col>

        <v-col cols="12" md="6">
          <v-text-field
            label="Employee ID"
            type="number"
            v-model="selectedReservation.data.employeeId"
            variant="outlined"
            density="comfortable"
            hide-details
          />
        </v-col>
      </v-row>

      <!-- Date & Time -->
      <v-row class="mb-2">
        <v-col cols="12" md="6">
          <v-text-field
            label="Start Time"
            type="datetime-local"
            v-model="editReservation.start"
            variant="outlined"
            density="comfortable"
            hide-details
          />
        </v-col>

        <v-col cols="12" md="6">
          <v-text-field
            label="End Time"
            type="datetime-local"
            v-model="editReservation.end"
            variant="outlined"
            density="comfortable"
            hide-details
          />
        </v-col>
      </v-row>

      <!-- Rooms Section -->
      <v-divider class="my-4"></v-divider>
      
      <div class="text-subtitle-2 font-weight-medium mb-3 text-grey-darken-2">
        Assigned Rooms
      </div>

      <div v-if="selectedReservation.data.rooms.length === 0" class="text-center py-4">
        <v-icon color="grey-lighten-1" size="40">mdi-home-alert-outline</v-icon>
        <p class="text-body-2 text-grey mt-2">No rooms assigned</p>
      </div>

      <v-list v-else class="bg-transparent pa-0">
        <v-list-item
          v-for="(room, i) in selectedReservation.data.rooms"
          :key="i"
          class="px-0 mb-1"
          rounded="lg"
        >
          <template v-slot:prepend>
            <v-avatar color="primary" size="32">
              <v-icon size="18" color="white">mdi-domain</v-icon>
            </v-avatar>
          </template>
          <v-list-item-title class="text-body-2 font-weight-medium">
            {{ room.building }}
          </v-list-item-title>
          <v-list-item-subtitle class="text-caption">
            Room {{ room.roomNumber }}
          </v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </v-card-text>

    <v-divider></v-divider>

    <v-card-actions class="pa-4">
      <v-btn 
        variant="tonal" 
        color="error"
        @click="deleteReservation(selectedReservation.data.reservationId)"
      >
        Delete
      </v-btn>

      <v-spacer></v-spacer>

      <v-btn 
        variant="text"
        @click="reservationInfoDialog=false"
      >
        Cancel
      </v-btn>

      <v-btn 
        variant="elevated" 
        color="primary"
        @click="updateReservation(selectedReservation.data.reservationId)"
      >
        Update
      </v-btn>
    </v-card-actions>
  </v-card>
</v-dialog>

		</v-sheet>
		<v-row class="mb-2"> 
			<v-col align="center">
				<v-btn variant="elevated" @click="prevWeek">Previous Week</v-btn>
				<v-btn variant="elevated" @click="goToday">Today</v-btn>
				<v-btn variant="elevated" @click="nextWeek">Next Week</v-btn>
			</v-col>
		</v-row>
	</v-col>
</v-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useSnackbarStore } from  '../stores/snackbar-store.ts'
import { useAuthStore } from '../stores/auth-store.ts'
import { computed } from 'vue';

// formats for datetime-local input
const formattedStart = computed({
  get() {
    return selectedReservation.value
      ? new Date(selectedReservation.value.data.startTime).toISOString().slice(0, 16)
      : "";
  },
  set(val) {
    updateStartISO(val);
  }
});

const formattedEnd = computed({
  get() {
    return selectedReservation.value
      ? new Date(selectedReservation.value.data.endTime).toISOString().slice(0, 16)
      : "";
  },
  set(val) {
    updateEndISO(val);
  }
});

function localInputToIso(localString) {
  const date = new Date(localString);
  return date.toISOString();
}

function isoToLocalInput(isoString) {
  const date = new Date(isoString);
  const pad = (n) => String(n).padStart(2, "0");

  // Convert UTC time from backend to local time for display
  const yyyy = date.getFullYear();
  const mm = pad(date.getMonth() + 1);
  const dd = pad(date.getDate());
  const hh = pad(date.getHours());
  const min = pad(date.getMinutes());

  return `${yyyy}-${mm}-${dd}T${hh}:${min}`;
}

function updateStartISO(event) {
  const val = event.startTime;   
  if (!val) return;
  selectedReservation.value.data.startTime = new Date(val).toISOString();
}

function updateEndISO(event) {
  const val = event.startTime;
  if (!val) return;
  selectedReservation.value.data.endTime = new Date(val).toISOString();
}

	async function updateReservation(id) {
		const updated = {
			...selectedReservation.value.data,
			startTime: new Date(editReservation.value.start).toISOString(),
			endTime: new Date(editReservation.value.end).toISOString(),
			rooms: editReservation.value.rooms
		};
		console.log(updated);
		try {
				await axios.put(`http://localhost:8080/reservations/${id}`, updated, {
															headers: {
																"Bearer": auth.token,
																"Access-Control-Allow-Origin": "*",	
															},
															responseType: "json",
														}).then((response) => {
															console.log(response);
														});
			snackbar.showSnackbar('Successfully updated reservation', 'success');
			reservationInfoDialog.value = false;
			getReservationsForUser()
		} catch (e) {
			console.log("unable to update reservation: ", e);
			snackbar.showSnackbar('Error updating reservation', 'error');
		}
	}

const editReservation = ref({
  start: "",
  end: "",
  rooms: []
});

const snackbar = useSnackbarStore();
const auth = useAuthStore();
const value = ref('')
const events = ref([])
const colors = [
	'#2196F3', '#3F51B5', '#673AB7', '#00BCD4', '#4CAF50', '#FF9800', '#757575',
]
const names = [
	'Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party',
]
const calendar = ref(null)
const dragEvent = ref(null)
const dragTime = ref(null)
const createEvent = ref(null)
const createStart = ref(null)
const extendOriginal = ref(null)
const addReservationDialog = ref(false)
const updateReservationDialog = ref(false)
const reservationInfoDialog = ref(false)
const selectedReservation = ref(null)
const showStartTimeMenu = ref(false)
const rsvpStartTime = ref('')
const rsvpEndTime = ref('')
const rsvpDate = ref('')
const rooms = ref([])
const menu = ref(false);
const roomsToAdd = ref([])
const rsvps = ref ([])
const rsvp = ref({
				employeeId: 2,
				employeeName: "Test User",
				startTime: "2025-12-10T15:00:00.000+00:00",
				endTime: "2025-12-10T16:30:00.000+00:00",
				rooms: [],
			})

onMounted(() => {
	getReservationsForUser()
})

function prevWeek() {
	calendar.value.prev()
}

function nextWeek() {
	calendar.value.next()
}

	function goToday() {
		value.value = new Date().toISOString()
	}

	function isSelected(room) {
		return rsvp.value.rooms.some(r => r.id === room.id)
	}

	function toggleRoom(room) {
		if (isSelected(room)) {
			rsvp.value.rooms = rsvp.value.rooms.filter(r => r.id !== room.id)
		} else {
			rsvp.value.rooms.push(room)
		}
	}

	function buildDateTime(dateString, timeString) {
		const [year, month, day] = dateString.split('-').map(Number)
		const [hour, minute] = timeString.split(':').map(Number)

		// Build date in local time, then convert to UTC for API
		const date = new Date(year, month - 1, day, hour, minute, 0, 0)
		
		console.log(`=== buildDateTime Debug ===`)
		console.log(`Input: ${dateString} ${timeString}`)
		console.log(`Local time selected: ${hour}:${minute}`)
		console.log(`Date object created: ${date.toString()}`)
		console.log(`Date in UTC: ${date.toUTCString()}`)
		console.log(`Timezone offset (minutes): ${date.getTimezoneOffset()}`)
		console.log(`Local hours: ${date.getHours()}, UTC hours: ${date.getUTCHours()}`)

		// toISOString() automatically converts to UTC
		const isoString = date.toISOString()
		console.log(`ISO String (UTC): ${isoString}`)
		console.log(`========================`)
		return isoString
	}

	function convertReservationsToEvents(reservations) {
		return reservations.map(res => ({
			name: `${res.employeeName} - ${res.rooms.map(r => r.roomNumber).join(", ")}`,
			start: new Date(res.startTime).getTime(),  
			end: new Date(res.endTime).getTime(),      
			color: "#1976D2",
			timed: true,
			data: res,
		}))
	}
		
	async function getRoomsByAvailability() {
		try {
				// Build ISO datetime strings from date + time
				const startISO = (rsvpDate.value && rsvpStartTime.value) 
					? buildDateTime(rsvpDate.value, rsvpStartTime.value) 
					: null;
				const endISO = (rsvpDate.value && rsvpEndTime.value) 
					? buildDateTime(rsvpDate.value, rsvpEndTime.value) 
					: null;
				
					console.log(startISO);
					console.log(endISO);

				await axios.get("http://localhost:8080/rooms/", {
															headers: {
																"Bearer": auth.token,
																"Access-Control-Allow-Origin": "*",	
															},
															params: {
																start: startISO,
																end: endISO,
															},
															responseType: "json",
														}).then((response) => {
															rooms.value = response.data;
														});
			console.log(rooms);
			snackbar.showSnackbar('Succesfully gathered room info', 'success');
		} catch (e) {
			console.log("unable to get rooms: ", e);
			snackbar.showSnackbar('Error retrieving room info', 'error');
		}
	}

	async function getRooms() {
		try {
				await axios.get("http://localhost:8080/rooms/", {
															headers: {
																"Bearer": auth.token,
																"Access-Control-Allow-Origin": "*",	
															},
															responseType: "json",
														}).then((response) => {
															rooms.value = response.data;
														});
			console.log(rooms);
			snackbar.showSnackbar('Succesfully gathered room info', 'success');
		} catch (e) {
			console.log("unable to get rooms: ", e);
			snackbar.showSnackbar('Error retrieving room info', 'error');
		}
	}

	async function deleteReservation(id) {
		try {
				await axios.delete(`http://localhost:8080/reservations/${id}`, {
															headers: {
																"Bearer": auth.token,
																"Access-Control-Allow-Origin": "*",	
															},
															responseType: "json",
														}).then((response) => {
															console.log(response);
														});
			snackbar.showSnackbar('Succesfully deleted reservation', 'success');
			reservationInfoDialog.value = false;
			getReservationsForUser();
		} catch (e) {
			console.log("unable to delete reservation", e);
			snackbar.showSnackbar('Error deleting reservation', 'error');
		}
	}

	async function getReservationsForUser() {
		try {
				await axios.get(`http://localhost:8080/reservations/user/${rsvp.value.employeeId}`, {
															headers: {
																"Bearer": auth.token,
																"Access-Control-Allow-Origin": "*",	
															},
															responseType: "json",
														}).then((response) => {
															rsvps.value = response.data;
															events.value = convertReservationsToEvents(response.data);
														});
		} catch (e) {
			console.log("unable to get reservations: ", e);
			snackbar.showSnackbar('Error retrieving reservation info', 'error');
		}
	}

	async function postReservation() {
		const startISO = buildDateTime(rsvpDate.value, rsvpStartTime.value)
		const endISO   = buildDateTime(rsvpDate.value, rsvpEndTime.value)
		rsvp.value.startTime = startISO
		rsvp.value.endTime   = endISO
		console.log(rsvp.value)
		try {
			await axios.post("http://localhost:8080/reservations/", rsvp.value, {
										headers: {
											"Bearer": auth.token,
											"Access-Control-Allow-Origin": "*",	
										},
										responseType: "json",
									}).then((response) => {
										console.log(response);
										getReservationsForUser();
									});	
			snackbar.showSnackbar('Successfully created reservation', 'success');
			addReservationDialog.value = false;
		} catch (error) {
			snackbar.showSnackbar('Error creating reservation', 'error');
			console.error("unable to post reservation: ", error)
		}	
	}
		
	function onEventClick({ event }) {
		console.log(event)
		selectedReservation.value = event
		reservationInfoDialog.value = true	

		// copy values into the edit buffer
		editReservation.value.start = isoToLocalInput(event.data.startTime)
		editReservation.value.end   = isoToLocalInput(event.data.endTime)
		editReservation.value.rooms = [...event.data.rooms];
	}

  function startTime (nativeEvent, tms) {
		addReservationDialog.value = true;
    const mouse = toTime(tms)
		createStart.value = roundTime(mouse)
		const date = new Date(createStart.value)
		// Display in local time for user
		const hh = String(date.getHours()).padStart(2, '0');
		const mm = String(date.getMinutes()).padStart(2, '0');
		rsvpStartTime.value = `${hh}:${mm}`;
		rsvpEndTime.value = '';
		rsvp.value.rooms = [];

		// build YYYY-MM-DD for the date field (local time)
		const yyyy = date.getFullYear()
		const month = String(date.getMonth() + 1).padStart(2, '0')
		const day = String(date.getDate()).padStart(2, '0')
		rsvpDate.value = `${yyyy}-${month}-${day}`

		//createEvent.value = {
		//	title: `Event #${events.value.length}`,
		//	color: rndElement(colors),
		//	start: createStart.value,
		//	end: createStart.value,
		//	timed: true,
		//}
		//events.value.push(createEvent.value)
  }

  function mouseMove (nativeEvent, tms) {
    const mouse = toTime(tms)

    if (dragEvent.value && dragTime.value !== null) {
      const start = dragEvent.value.start
      const end = dragEvent.value.end
      const duration = end - start
      const newStartTime = mouse - dragTime.value
      const newStart = roundTime(newStartTime)
      const newEnd = newStart + duration

      dragEvent.value.start = newStart
      dragEvent.value.end = newEnd
    } else if (createEvent.value && createStart.value !== null) {
      const mouseRounded = roundTime(mouse, false)
      const min = Math.min(mouseRounded, createStart.value)
      const max = Math.max(mouseRounded, createStart.value)

      createEvent.value.start = min
      createEvent.value.end = max
    }
  }

  function roundTime (time, down = true) {
    const roundTo = 30 // minutes
    const roundDownTime = roundTo * 60 * 1000

    return down
      ? time - time % roundDownTime
      : time + (roundDownTime - (time % roundDownTime))
  }

  function toTime (tms) {
    return new Date(tms.year, tms.month - 1, tms.day, tms.hour, tms.minute).getTime()
  }

  function getEventColor (event) {
    const rgb = parseInt(event.color.substring(1), 16)
    const r = (rgb >> 16) & 0xFF
    const g = (rgb >> 8) & 0xFF
    const b = (rgb >> 0) & 0xFF

    return event === dragEvent.value
      ? `rgba(${r}, ${g}, ${b}, 0.7)`
      : event === createEvent.value
        ? `rgba(${r}, ${g}, ${b}, 0.7)`
        : event.color
  }

  function getEvents ({ start, end }) {
    const newEvents = []

    const min = new Date(`${start.date}T00:00:00`).getTime()
    const max = new Date(`${end.date}T23:59:59`).getTime()
    const days = (max - min) / 86400000
    const eventCount = rnd(days, days + 20)

    for (let i = 0; i < eventCount; i++) {
      const timed = rnd(0, 3) !== 0
      const firstTimestamp = rnd(min, max)
      const secondTimestamp = rnd(2, timed ? 8 : 288) * 900000
      const startTime = firstTimestamp - (firstTimestamp % 900000)
      const endTime = startTime + secondTimestamp

      newEvents.push({
        name: rndElement(names),
        color: rndElement(colors),
        start: startTime,
        end: endTime,
        timed,
      })
    }

    events.value = newEvents
  }

  function rnd (a, b) {
    return Math.floor((b - a + 1) * Math.random()) + a
  }

  function rndElement (arr) {
    return arr[rnd(0, arr.length - 1)]
  }
</script>

<style scoped>
	/*
	:deep(.v-time-picker-clock) {
		display: none !important;
	}
	*/	
  .v-event-draggable {
    padding-left: 6px;
  }

  .v-event-timed {
    user-select: none;
    -webkit-user-select: none;
  }

  .v-event-drag-bottom {
    position: absolute;
    left: 0;
    right: 0;
    bottom: 4px;
    height: 4px;
    cursor: ns-resize;

    &::after {
      display: none;
      position: absolute;
      left: 50%;
      height: 4px;
      border-top: 1px solid white;
      border-bottom: 1px solid white;
      width: 16px;
      margin-left: -8px;
      opacity: 0.8;
      content: '';
    }

    &:hover::after {
      display: block;
    }
  }

	.my-event {
		height: 100%;
		width: 100%;
		position: relative;
	}

</style>

