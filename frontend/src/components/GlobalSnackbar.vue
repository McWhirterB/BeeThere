<template>
	<div class="text-center">
		<v-snackbar
			v-model="snackbarRef"
			class=""
			:color="snackbarStatus"
			
		>
			<div class="text-subtitle-1 pb-2 title-custom">{{ snackbarStatus }}</div>
			<p>{{ errorMsg }}</p>	
			<template>
				<v-btn
					v-for="act of snackbarActions"
					:key="act.actionName"
					:to="act.link"
				>
					{{ act.actionName }}
				</v-btn>
				<v-btn variant="tonal" @click="closeSnackbar"> Close </v-btn>
			</template>
		</v-snackbar>
	</div>
</template>

<script lang="ts" setup>
	import { toRef } from 'vue'
	import { customAction, useSnackbarStore } from '../stores/snackbar-store.ts'
	
	const props = defineProps({
		snackbarShow: Boolean,
		errorMsg: String,
		snackbarStatus: String,
		snackbarActions: {
			type: Array as () => CustomAction[],
		},
	});
	const snackbar = useSnackbarStore();
	const snackbarRef = toRef(props, 'snackbarShow');
	
	const closeSnackbar = () => {
		snackbar.closeSnackbar()
	}
</script>
