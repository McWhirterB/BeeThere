/**
 * plugins/vuetify.ts
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// Composables
import { createVuetify } from 'vuetify'

const beeThereTheme = {
	dark: false,
	colors: {
		background: '#F5F5F5',
		primary: '#A5D6A7',
	},
}

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    defaultTheme: 'light',
		themes: {
			beeThereTheme,
		},
  },
})
