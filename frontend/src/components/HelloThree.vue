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
          @mouseleave="cancelDrag"
          @mousemove:time="mouseMove"
          @mouseup:time="endDrag"
        ><template #event="{ event, timed, eventSummary }">
  <!-- FULL-EVENT CLICK BLOCKER -->
  <div
    class="my-event"
    @mousedown.stop
    @click.stop="onEventClick({ event })"
  >

    <!-- event label/content -->
    <div class="v-event-draggable">
      <component :is="eventSummary"></component>
    </div>

    <!-- resize handle -->
    <div
      v-if="timed"
      class="v-event-drag-bottom"
      @mousedown.stop="extendBottom(event)"
      @click.stop
      @mouseup.stop
    ></div>
  </div>
</template>

        </v-calendar>
				<v-dialog v-model="addReservationDialog" width="60%"> 
					<template v-slot:default="{ isActive }">
						<v-card>
							<v-card-title>
								Add Meeting
							</v-card-title>
							<v-card-text>
<!--								<v-text-field readonly variant="outlined" label="Start Time" prepend-inner-icon="mdi-clock-in">
									<v-menu v-model="showStartTimeMenu" :close-on-content-click="false"
													activator="parent" min-width="0">
										<v-time-picker></v-time-picker>
									</v-menu> 
								</v-text-field> -->
								<v-row>
									<v-col>
										<v-time-picker>
											<template #clock></template>
										</v-time-picker>
									</v-col>
									<v-col>
										<v-time-picker></v-time-picker>
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
				<v-dialog v-model="reservationInfoDialog">
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
  import { ref } from 'vue'

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

	function onEventClick({ event }) {
		console.log(event)
		selectedReservation.value = event
		reservationInfoDialog.value = true
	}

  function startDrag (nativeEvent, { event, timed }) {
    if (event && timed) {
      dragEvent.value = event
      dragTime.value = null
      extendOriginal.value = null
    }
  }

  function startTime (nativeEvent, tms) {
		// if click landed inside an event
		//if (nativeEvent.target.closest('.v-event-draggable')) {
		//	return
		//}		

		addReservationDialog.value = true;
    const mouse = toTime(tms)
		createStart.value = roundTime(mouse)
		createEvent.value = {
			name: `Event #${events.value.length}`,
			color: rndElement(colors),
			start: createStart.value,
			end: createStart.value,
			timed: true,
		}
		events.value.push(createEvent.value)
  }

  function extendBottom (event) {
    createEvent.value = event
    createStart.value = event.start
    extendOriginal.value = event.end
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

  function endDrag () {
    dragTime.value = null
    dragEvent.value = null
    createEvent.value = null
    createStart.value = null
    extendOriginal.value = null
  }

  function cancelDrag () {
    if (createEvent.value) {
      if (extendOriginal.value) {
        createEvent.value.end = extendOriginal.value
      } else {
        const i = events.value.indexOf(createEvent.value)
        if (i !== -1) {
          events.value.splice(i, 1)
        }
      }
    }

    createEvent.value = null
    createStart.value = null
    dragTime.value = null
    dragEvent.value = null
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

