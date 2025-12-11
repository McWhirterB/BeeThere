<template>
  <div class="reservation-list-container d-flex flex-column" style="height: calc(100vh - 64px);">
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

    <!-- Scrollable Reservations List -->
    <v-container class="flex-grow-1 overflow-y-auto pb-6">
      <!-- Empty State -->
      <v-row v-if="filteredReservations.length === 0" class="mt-8">
        <v-col cols="12" class="text-center">
          <v-icon size="80" color="grey-lighten-2">mdi-calendar-remove</v-icon>
          <p class="text-h6 text-grey mt-4">No reservations found</p>
          <p class="text-body-2 text-grey">Try adjusting your search criteria</p>
        </v-col>
      </v-row>

      <!-- Reservations -->
      <v-expansion-panels v-else variant="accordion" class="rounded-lg">
        <v-expansion-panel
          v-for="reservation in filteredReservations"
          :key="reservation.reservationId"
          elevation="2"
          class="mb-3 rounded-lg"
        >
          <v-expansion-panel-title class="py-4">
            <div class="d-flex align-center justify-space-between w-100 pr-4">
              <!-- Left: Employee Info -->
              <div class="d-flex align-center">
                <v-avatar color="primary" size="40" class="mr-4">
                  <v-icon color="white">mdi-account</v-icon>
                </v-avatar>
                <div>
                  <div class="text-body-1 font-weight-bold">
                    {{ reservation.employeeName }}
                  </div>
                  <div class="text-caption text-grey-darken-1">
                    ID: {{ reservation.employeeId }}
                  </div>
                </div>
              </div>

              <!-- Right: Time & Status -->
              <div class="d-flex align-center">
                <v-chip 
                  :color="getReservationStatus(reservation).color"
                  variant="flat"
                  size="small"
                  class="mr-4 font-weight-medium"
                >
                  {{ getReservationStatus(reservation).text }}
                </v-chip>
                <div class="text-body-2 text-right">
                  <div>{{ formatDateTime(reservation.startTime) }}</div>
                  <div class="text-caption text-grey-darken-1">
                    <v-icon size="12">mdi-arrow-down</v-icon>
                    {{ formatDateTime(reservation.endTime) }}
                  </div>
                </div>
              </div>
            </div>
          </v-expansion-panel-title>

          <v-expansion-panel-text>
            <v-divider class="mb-4"></v-divider>

            <!-- Reservation Details -->
            <v-row class="mb-4">
              <v-col cols="12" md="6">
                <v-card elevation="0" class="bg-grey-lighten-5 pa-4" rounded="lg">
                  <div class="d-flex align-center mb-3">
                    <v-icon size="18" color="success" class="mr-2">mdi-calendar-start</v-icon>
                    <span class="text-body-2 font-weight-medium">{{ formatFullDateTime(reservation.startTime) }}</span>
                  </div>
                  
                  <div class="d-flex align-center">
                    <v-icon size="18" color="error" class="mr-2">mdi-calendar-end</v-icon>
                    <span class="text-body-2 font-weight-medium">{{ formatFullDateTime(reservation.endTime) }}</span>
                  </div>
                </v-card>
              </v-col>

              <v-col cols="12" md="6">
                <v-card elevation="0" class="bg-grey-lighten-5 pa-4" rounded="lg">
                  <div class="d-flex align-center mb-3">
                    <v-icon size="18" color="primary" class="mr-2">mdi-barcode</v-icon>
                    <span class="text-body-2 font-weight-medium">Reservation ID: {{ reservation.reservationId }}</span>
                  </div>
                  
                  <div class="d-flex align-center">
                    <v-icon size="18" color="primary" class="mr-2">mdi-door</v-icon>
                    <span class="text-body-2 font-weight-medium">{{ reservation.rooms.length }} room(s) reserved</span>
                  </div>
                </v-card>
              </v-col>
            </v-row>

            <!-- Assigned Rooms -->
            <div class="mb-4">
              <div class="text-subtitle-1 font-weight-bold mb-3 text-grey-darken-2">
                Assigned Rooms
              </div>

              <div v-if="reservation.rooms.length === 0" class="text-center py-4">
                <v-icon size="48" color="grey-lighten-2">mdi-home-alert-outline</v-icon>
                <p class="text-body-2 text-grey mt-2">No rooms assigned</p>
              </div>

              <v-row v-else>
                <v-col 
                  v-for="room in reservation.rooms" 
                  :key="room.id"
                  cols="12"
                  sm="6"
                  md="4"
                >
                  <v-card elevation="2" rounded="lg" class="room-mini-card">
                    <v-card-text class="pa-3">
                      <div class="d-flex align-center">
                        <v-avatar color="primary" size="32" class="mr-3">
                          <v-icon size="18" color="white">mdi-domain</v-icon>
                        </v-avatar>
                        <div>
                          <div class="text-body-2 font-weight-medium">
                            {{ room.building }}
                          </div>
                          <div class="text-caption text-grey-darken-1">
                            Room {{ room.roomNumber }}
                          </div>
                        </div>
                      </div>
                    </v-card-text>
                  </v-card>
                </v-col>
              </v-row>
            </div>
          </v-expansion-panel-text>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-container>
  </div>
</template>

<style scoped>
.reservation-list-container {
  background-color: #fafafa;
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

.room-mini-card {
  transition: all 0.2s ease;
}

.room-mini-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15) !important;
}
</style>

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
    formatDateTime(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('en-US', { 
        month: 'short', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      });
    },

    formatFullDateTime(dateString) {
      const date = new Date(dateString);
      return date.toLocaleString('en-US', { 
        weekday: 'short',
        year: 'numeric',
        month: 'short', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      });
    },

    getReservationStatus(reservation) {
      const now = new Date();
      const start = new Date(reservation.startTime);
      const end = new Date(reservation.endTime);

      if (now < start) {
        return { text: 'Upcoming', color: 'info' };
      } else if (now >= start && now <= end) {
        return { text: 'Active', color: 'success' };
      } else {
        return { text: 'Past', color: 'grey' };
      }
    },

    async deleteReservation(reservationId) {
      if (!confirm('Are you sure you want to delete this reservation?')) {
        return;
      }

      try {
        await axios.delete(`http://localhost:8080/reservations/${reservationId}`, {
          headers: { "Bearer": this.auth.token },
        });

        // Remove from local array
        this.apiResponse = this.apiResponse.filter(r => r.reservationId !== reservationId);
        this.snackbar.showSnackbar("Reservation deleted successfully", "success");
      } catch (error) {
        console.error(error);
        this.snackbar.showSnackbar("Error deleting reservation", "error");
      }
    },

    editReservation(reservation) {
      // TODO: Implement edit functionality
      // You could emit an event, navigate to edit page, or open a dialog
      this.snackbar.showSnackbar(`Edit functionality for reservation ${reservation.reservationId} - Coming soon!`, "info");
    },
  },

  async mounted() {
    try {
      const response = await axios.get("http://localhost:8080/reservations/", {
        headers: { "Bearer": this.auth.token },
      });

      this.apiResponse = response.data;
      this.snackbar.showSnackbar("Reservations loaded successfully!", "success");
    } catch (error) {
      console.error(error);
      this.snackbar.showSnackbar("Error loading reservations", "error");
    }
  },
};
</script>


