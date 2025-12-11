import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('AuthStore', () => {
	const token = ref('eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJGaWxpcGN6eW5za2kiLCJsb2NhdGlvbiI6Ikdlcm1hbnkiLCJpZCI6MTksImRlcGFydG1lbnQiOiJJbmZvcm1hdGlvbiBUZWNobm9sb2d5IiwidGl0bGUiOiJEZXZlbG9wZXIiLCJmaXJzdF9uYW1lIjoiTWFyZ2l0Iiwic3ViIjoiTWFyZ2l0IEZpbGlwY3p5bnNraSIsImlhdCI6MTc2NTQ4Mzk0MiwiZXhwIjoxNzY1NDg3NTQyfQ.cPgwIgY4LSSBItQ9pmCTiXwyehqREfWB48-hGSpaHsI');

	return { token } 
});
