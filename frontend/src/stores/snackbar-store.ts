import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface CustomAction {
	actionName: string;
	do: any;
}

export const useSnackbarStore = defineStore('SnackbarStore', () => {
	const isOpen = ref(false);
	const message = ref('');
	const status = ref('error');
	const customActions: Ref<CustomAction[]> = ref([]);

	const showSnackbar = (msg: string, st: string, optionalActions?: CustomAction[]) => {
		message.value = msg;
		status.value = st;
		isOpen.value = true;
		if (optionalActions) {
			customActions.value.push(...optionalActions)
		}
		if (status.value.toLowerCase() === 'success') {
			setTimeout(() => {
				closeSnackbar();
			}, 2500);
		}
	}

	function closeSnackbar() {
		isOpen.value = false;
		customActions.value.splice(0, customActions.value.length);
	}

	return { isOpen, message, status, showSnackbar, closeSnackbar, customActions }
});
