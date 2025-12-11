/**
 * main.ts
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins'
import { createPinia } from 'pinia'

// Components
import App from './App.vue'

// Composables
import { createApp } from 'vue'

// Styles
import 'unfonts.css'

// Import auth store
import { useAuthStore } from '@/stores/auth-store'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
registerPlugins(app)

app.mount('#app')

// Load employee identity after app is mounted
const authStore = useAuthStore()
authStore.loadIdentity()
