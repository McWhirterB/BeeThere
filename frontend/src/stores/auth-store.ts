import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

/**
 * Auth Store
 * 
 * Manages authentication token and employee identity.
 * 
 * Token can be provided via URL query parameter:
 * Example: http://localhost:3000/?token=YOUR_JWT_TOKEN_HERE
 * 
 * If no token is provided in URL, a default token will be used.
 */
export const useAuthStore = defineStore('AuthStore', () => {
	const identity = ref({
		employee_id: 0,
		location: '',
		first_name: '',
		last_name: '',
		department: '',
		title: '',
	});	

	// Initialize token from URL query parameter or use default
	const initializeToken = () => {
		const urlParams = new URLSearchParams(window.location.search);
		const tokenParam = urlParams.get('token');
		
		if (tokenParam) {
			console.log('Token loaded from URL parameter');
			return tokenParam;
		}
		
		// Default token if none provided in URL
		console.log('Using default token');
		return 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJHaWxtb3VyIiwibG9jYXRpb24iOiJHZXJtYW55IiwiaWQiOjQ2LCJkZXBhcnRtZW50IjoiU2FsZXMiLCJ0aXRsZSI6IlNhbGVzIEFnZW50IiwiZmlyc3RfbmFtZSI6IkFndXN0aW4iLCJzdWIiOiJBZ3VzdGluIEdpbG1vdXIiLCJpYXQiOjE3NjU0ODc5MjgsImV4cCI6MTc2NTQ5MTUyOH0.wjp2UvXHp0TPedxJkY6wyVdzi_7YdRgcdhIHkHGkNVc';
	};

	const token = ref(initializeToken());

	const isLoading = ref(false);
	const error = ref<string | null>(null);

	// Fetch employee details from backend and populate identity
	async function loadIdentity() {
		isLoading.value = true;
		error.value = null;
		
		try {
			const response = await axios.get('http://localhost:8080/employees/employee', {
				headers: {
					'Bearer': token.value
				}
			});
			
			console.log('Raw response data:', response.data);
			
			// Populate identity with employee data from backend
			identity.value = {
				employee_id: response.data.id,
				location: response.data.loc,
				first_name: response.data.fname,
				last_name: response.data.lname,
				department: response.data.dept,
				title: response.data.title,
			};
			
			console.log('Identity loaded:', identity.value);
		} catch (err: any) {
			error.value = err.response?.data || 'Failed to load employee identity';
			console.error('Failed to load identity:', err);
		} finally {
			isLoading.value = false;
		}
	}

	// Method to manually set the token
	function setToken(newToken: string) {
		token.value = newToken;
		console.log('Token manually updated');
	}

	return { 
		token, 
		identity, 
		isLoading, 
		error,
		loadIdentity,
		setToken
	} 
});
