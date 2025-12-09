<template>
  <v-app>
			<v-layout>
				<v-main>
					<v-app-bar elevation="1" 
						image="https://images.pexels.com/photos/29506613/pexels-photo-29506613.jpeg"
						theme="dark">
						<v-app-bar-title>BeeThere</v-app-bar-title>
						<template v-slot:append>
							<v-menu min-width="200px">
								<template v-slot:activator="{ props }">
									<v-btn icon v-bind="props">
										<v-avatar color="grey-lighten-2" size="large">
											<span class="text-h5">{{ user.initials }}</span>
										</v-avatar>
									</v-btn>
								</template>
								<v-card>
									<v-card-text>
										<div class="mx-auto text-center">
											<v-avatar color="brown">
												<span class="text-h5">{{ user.initials }}</span>
											</v-avatar>
											<h3>{{ user.fullName }}</h3>
											<p class="text-caption mt-1">
												{{ user.email }}
											</p>
											<v-divider class="my-3"></v-divider>
											<v-btn variant="text" rounded>
												Edit Account
											</v-btn>
											<v-divider class="my-3"></v-divider>
											<v-btn variant="text" rounded>
												Disconnect
											</v-btn>
										</div>
									</v-card-text>
								</v-card>
							</v-menu>
						</template>
					</v-app-bar>
					<v-navigation-drawer 
						image=""
						width="350">
						<v-list nav>
							<v-date-picker hide-header width="">
							</v-date-picker>
							<v-list-item prepend-icon="mdi-clock" value="test">
								Your Schedule
							</v-list-item>
							<v-list-item prepend-icon="mdi-domain" value="test2">
								Room Index
							</v-list-item>
						</v-list>
					</v-navigation-drawer>
					<router-view />
				</v-main>	
			</v-layout>
		<GlobalSnackbar
			:error-msg="snackbar.message"
			:snackbar-actions="snackbar.customActions"
			:snackbar-show="snackbar.isOpen"
			:snackbar-status="snackbar.status" />
  </v-app>
</template>

<script lang="ts" setup>
	import { useRefs } from 'vuetify/lib/composables/refs.mjs';
	import { useSnackbarStore } from './stores/snackbar-store.ts';
	const snackbar = useSnackbarStore();
	const user = {
		initials: 'DW',
		fullName: 'Darrian Ward',
		email: 'swaglord@sauce.com'
	}
//
</script>
