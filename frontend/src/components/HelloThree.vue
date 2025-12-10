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
          @change="getEvents"
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
				<v-dialog v-model="addReservationDialog" width="60%"> 
					<template v-slot:default="{ isActive }">
						<v-card>
							<v-card-title align="center">
								Add Meeting
							</v-card-title>
							<v-card-text>
								<v-row>	
									<v-col align="center">
										<h1>Date</h1>
										<v-text-field v-model="rsvpDate" type="date" variant="outlined" width="40%"></v-text-field>	
									</v-col>
								</v-row>
								<v-row>
									<v-col align="center">
										<h1>Start Time</h1>
										<v-time-picker v-model="rsvpStartTime"></v-time-picker>
									</v-col>
									<v-col align="center">
										<h1>End Time</h1>
										<v-time-picker v-model="rsvpEndTime"></v-time-picker>
									</v-col>
								</v-row>
								<v-row>
									<v-col align="center">
										<v-card elevation="2" width="40%">
											<v-card-text>
												<v-row>
													<v-col> 
														<v-menu v-model="menu" :close-on-content-click="false" location="end">
															<template v-slot:activator="{ props }">
																<v-btn v-bind="props" variant="plain"> Add Rooms </v-btn>
															</template>

															<v-card min-width="300">
																<v-list>
																	<v-list-item>	
																		Rooms
																	</v-list-item>
																</v-list>

																<v-divider></v-divider>

																<v-list v-for="(room, i) in rooms" :key="i">
																	<v-list-item>
																		<v-list-item-title v-text="room.roomNumber"></v-list-item-title>
																		<v-icon icon="mdi-plus-box" @click="rsvp.rooms.push(room)"></v-icon>
																	</v-list-item>
																</v-list>

																<v-card-actions>
																	<v-spacer></v-spacer>

																</v-card-actions>
															</v-card>
														</v-menu>
													</v-col>
												</v-row>
												<v-row>
													<v-col>
														<v-list v-for="(room, i) in rsvp.rooms" :key="i">
															<v-list-item v-text="room.roomNumber"></v-list-item>
														</v-list>
													</v-col>
												</v-row>
											</v-card-text>	
										</v-card>
									</v-col>
								</v-row>
								<v-row>
									<v-col class="text-center">
										<v-btn @click="postReservation()" variant="flat">Create Reservation</v-btn>
										<v-btn @click="console.log(rsvpDate)">Test Reservation</v-btn>
									</v-col>
								</v-row>
							</v-card-text>
							<v-card-actions> 
								<v-btn @click="addReservationDialog=false">
									Close
								</v-btn>
							</v-card-actions>
						</v-card>
					</template>
				</v-dialog>
				<v-dialog v-model="reservationInfoDialog" width="60%">
					<v-card>
						<v-card-text>
							{{ selectedReservation?.name }}	
						</v-card-text>
					</v-card>
				</v-dialog>
      </v-sheet>
    </v-col>
  </v-row>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
	import axios from 'axios'
	import { useSnackbarStore } from  '../stores/snackbar-store.ts'

	const snackbar = useSnackbarStore();
  const value = ref('')
  const events = ref([])
  const colors = [
    '#2196F3', '#3F51B5', '#673AB7', '#00BCD4', '#4CAF50', '#FF9800', '#757575',
  ]
  const names = [
    'Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party',
  ]
  const dragEvent = ref(null)
  const dragTime = ref(null)
  const createEvent = ref(null)
  const createStart = ref(null)
  const extendOriginal = ref(null)
	const addReservationDialog = ref(false)
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
					reservationId: 28,
					employeeId: 128,
					employeeName: "LuhTyrese",
					startTime: "2025-12-10T15:00:00.000+00:00",
					endTime: "2025-12-10T16:30:00.000+00:00",
					rooms: [],
				})

	onMounted(() => {
		getRooms()
		getReservationsForUser()
	})

	function buildDateTime(dateString, timeString) {
		const [year, month, day] = dateString.split('-').map(Number)
		const [hour, minute] = timeString.split(':').map(Number)

		// build the date in local time 
		const date = new Date(year, month - 1, day, hour, minute, 0, 0)

		return date.toISOString()
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
		
	async function getRooms() {
		try {
				await axios.get("http://localhost:8080/rooms/", {
															headers: {
																"Bearer": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJEdWNrd29ydGgiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjoyLCJkZXBhcnRtZW50IjoiSW5mb3JtYXRpb24gVGVjaG5vbG9neSIsInRpdGxlIjoiRGV2ZWxvcGVyIiwiZmlyc3RfbmFtZSI6Iktlbm5hbiIsInN1YiI6Iktlbm5hbiBEdWNrd29ydGgiLCJpYXQiOjE3NjUzMzIzMTAsImV4cCI6MTc2NTMzNTkxMH0.Sgo4dAtSQuiz0TY_XygkBfgCz59_xQKDYanaXsPdkYg",
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

	async function getReservationsForUser() {
		try {
				await axios.get(`http://localhost:8080/reservations/user/${rsvp.value.employeeId}`, {
															headers: {
																"Bearer": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJEdWNrd29ydGgiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjoyLCJkZXBhcnRtZW50IjoiSW5mb3JtYXRpb24gVGVjaG5vbG9neSIsInRpdGxlIjoiRGV2ZWxvcGVyIiwiZmlyc3RfbmFtZSI6Iktlbm5hbiIsInN1YiI6Iktlbm5hbiBEdWNrd29ydGgiLCJpYXQiOjE3NjUzMzIzMTAsImV4cCI6MTc2NTMzNTkxMH0.Sgo4dAtSQuiz0TY_XygkBfgCz59_xQKDYanaXsPdkYg",
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
											"Bearer": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJEdWNrd29ydGgiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjoyLCJkZXBhcnRtZW50IjoiSW5mb3JtYXRpb24gVGVjaG5vbG9neSIsInRpdGxlIjoiRGV2ZWxvcGVyIiwiZmlyc3RfbmFtZSI6Iktlbm5hbiIsInN1YiI6Iktlbm5hbiBEdWNrd29ydGgiLCJpYXQiOjE3NjUzMzIzMTAsImV4cCI6MTc2NTMzNTkxMH0.Sgo4dAtSQuiz0TY_XygkBfgCz59_xQKDYanaXsPdkYg",
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
	}

  function startTime (nativeEvent, tms) {
		addReservationDialog.value = true;
    const mouse = toTime(tms)
		createStart.value = roundTime(mouse)
		const date = new Date(createStart.value)
		const hh = String(date.getHours()).padStart(2, '0');
		const mm = String(date.getMinutes()).padStart(2, '0');
		rsvpStartTime.value = `${hh}:${mm}`;
		rsvpEndTime.value = '';
		rsvp.value.rooms = [];

		// build YYYY-MM-DD for the date field
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

