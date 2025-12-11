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
						width="380"
						elevation="2"
						class="custom-nav-drawer">
						
						<!-- Date Picker Section -->
						<div class="pa-2">
							<v-date-picker 
								hide-header 
								show-adjacent-months
								elevation="0"
								color="primary"
								width="100%"
								class="compact-date-picker"
							/>
						</div>

						<v-divider class="mx-4 my-2"></v-divider>

						<!-- Navigation Items -->
						<v-list nav density="comfortable" class="px-2">
							<v-list-item 
								prepend-icon="mdi-clock" 
								to="/"
								rounded="lg"
								class="mb-1 nav-item"
								active-class="active-nav-item"
							>
								<v-list-item-title class="font-weight-medium">
									Your Schedule
								</v-list-item-title>
							</v-list-item>

							<v-list-item 
								prepend-icon="mdi-domain" 
								to="/room"
								rounded="lg"
								class="mb-1 nav-item"
								active-class="active-nav-item"
							>
								<v-list-item-title class="font-weight-medium">
									Room Index
								</v-list-item-title>
							</v-list-item>

							<v-list-item 
								prepend-icon="mdi-badge-account-horizontal" 
								to="/manager"
								rounded="lg"
								class="mb-1 nav-item"
								active-class="active-nav-item"
							>
								<v-list-item-title class="font-weight-medium">
									Manager Index
								</v-list-item-title>
							</v-list-item>

							<v-divider class="my-3"></v-divider>

							<v-list-item 
								prepend-icon="mdi-information-outline" 
								to="/about"
								rounded="lg"
								class="mb-1 nav-item"
								active-class="active-nav-item"
							>
								<v-list-item-title class="font-weight-medium">
									About Us
								</v-list-item-title>
							</v-list-item>
						</v-list>

						<!-- Footer -->
						<template v-slot:append>
							<div class="pa-4 text-center">
								<v-divider class="mb-3"></v-divider>
								<p class="text-caption text-grey">
									BeeThere v1.0
								</p>
							</div>
						</template>
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

<style scoped>
.custom-nav-drawer {
	background-color: #fafafa;
}

.compact-date-picker {
	border-radius: 12px;
	background-color: white;
	box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.nav-item {
	transition: all 0.2s ease;
	margin-bottom: 4px;
}

.nav-item:hover {
	background-color: rgba(var(--v-theme-primary), 0.08);
	transform: translateX(4px);
}

.active-nav-item {
	background-color: rgba(var(--v-theme-primary), 0.12) !important;
	color: rgb(var(--v-theme-primary));
}

.active-nav-item .v-list-item-title {
	color: rgb(var(--v-theme-primary));
	font-weight: 700;
}

.active-nav-item .v-icon {
	color: rgb(var(--v-theme-primary));
}
</style>
