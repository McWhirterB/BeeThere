import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('AuthStore', () => {
	const token = ref('eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJEdWNrd29ydGgiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjoyLCJkZXBhcnRtZW50IjoiSW5mb3JtYXRpb24gVGVjaG5vbG9neSIsInRpdGxlIjoiRGV2ZWxvcGVyIiwiZmlyc3RfbmFtZSI6Iktlbm5hbiIsInN1YiI6Iktlbm5hbiBEdWNrd29ydGgiLCJpYXQiOjE3NjUzOTgxNjIsImV4cCI6MTc2NTQwMTc2Mn0.HPJljloEIroEChgGS1NSwVZU78KoRNNrP631XOYuqjs');

	return { token } 
});
