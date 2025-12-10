<template>
</template>
<script>
	import { ref } from 'vue';
	import axios from 'axios';
	import { useSnackbarStore } from '../stores/snackbar-store.ts'

	//const snackbar = useSnackbarStore();
	export default {
		data () {
			return {
				token: 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJEdWNrd29ydGgiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjoyLCJkZXBhcnRtZW50IjoiSW5mb3JtYXRpb24gVGVjaG5vbG9neSIsInRpdGxlIjoiRGV2ZWxvcGVyIiwiZmlyc3RfbmFtZSI6Iktlbm5hbiIsInN1YiI6Iktlbm5hbiBEdWNrd29ydGgiLCJpYXQiOjE3NjUzMzk4NzksImV4cCI6MTc2NTM0MzQ3OX0.i05NO-DJ-IZjA_oNR6SIkmJTpz6BoO-0AM4z0tHWYTc',
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
																"Bearer": this.token,
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
		}	
	}
</script>
