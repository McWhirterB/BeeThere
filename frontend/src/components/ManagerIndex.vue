<template>
  <div class="reservation-list-container">
    <v-container>

      <!-- 🔍 Search Bar -->
      <v-text-field
        v-model="searchQuery"
        label="Search reservations (name, employee ID, room)..."
        prepend-inner-icon="mdi-magnify"
        clearable
        class="mb-4"
      />

      <v-expansion-panels multiple>
        <v-expansion-panel
          v-for="reservation in filteredReservations"
          :key="reservation.reservationId"
        >
          <v-expansion-panel-title>
            <div class="d-flex justify-space-between w-100">
              <div><strong>{{ reservation.employeeName }}</strong></div>
              <div>
                {{ formatDate(reservation.startTime) }} →
                {{ formatDate(reservation.endTime) }}
              </div>
            </div>
          </v-expansion-panel-title>

          <v-expansion-panel-text>
            <p><strong>Reservation ID:</strong> {{ reservation.reservationId }}</p>
            <p><strong>Employee:</strong> {{ reservation.employeeName }} ({{ reservation.employeeId }})</p>
            <p><strong>Start:</strong> {{ reservation.startTime }}</p>
            <p><strong>End:</strong> {{ reservation.endTime }}</p>

            <strong>Rooms:</strong>
            <ul>
              <li v-if="reservation.rooms.length === 0">No rooms linked</li>
              <li v-for="room in reservation.rooms" :key="room.id">
                {{ room.building }} – {{ room.roomNumber }}
              </li>
            </ul>
          </v-expansion-panel-text>
        </v-expansion-panel>
      </v-expansion-panels>

    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import { useSnackbarStore } from "../stores/snackbar-store.ts";
import { useAuthStore } from "../stores/auth-store.ts";

export default {
  data() {
    return {
      apiResponse: [],
      searchQuery: "",
      snackbar: useSnackbarStore(),
      auth: useAuthStore(),
    };
  },

  computed: {
    filteredReservations() {
      if (!this.searchQuery.trim()) return this.apiResponse;

      const q = this.searchQuery.toLowerCase();

      return this.apiResponse.filter(res => {
        const nameMatch = res.employeeName?.toLowerCase().includes(q);
        const employeeIdMatch = String(res.employeeId).includes(q);

        // Search through rooms array
        const roomMatch = res.rooms?.some(room =>
          room.building?.toLowerCase().includes(q) ||
          room.roomNumber?.toLowerCase().includes(q)
        );

        return nameMatch || employeeIdMatch || roomMatch;
      });
    }
  },

  methods: {
    formatDate(dateString) {
      return new Date(dateString).toLocaleString();
    },
  },

  async mounted() {
    try {
      const response = await axios.get("http://localhost:8080/reservations/", {
        headers: { "Bearer": this.auth.token },
      });

      this.apiResponse = response.data;
      this.snackbar.showSnackbar("Reservations loaded!", "success");
    } catch (error) {
      console.error(error);
      this.snackbar.showSnackbar("Error loading reservations", "error");
    }
  },
};
</script>

