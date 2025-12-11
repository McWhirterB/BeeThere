<template>
	<div class="galaxy-realm">
		<!-- Animated nebula clouds -->
		<div class="nebula-layer">
			<div class="nebula nebula-1"></div>
			<div class="nebula nebula-2"></div>
			<div class="nebula nebula-3"></div>
			<div class="nebula nebula-4"></div>
		</div>

		<!-- Starfield with different sizes -->
		<div class="starfield">
			<div v-for="n in 100" :key="n" class="star" :style="getStarStyle(n)"></div>
		</div>

		<!-- Floating comets -->
		<div class="comet-trail comet-1"></div>
		<div class="comet-trail comet-2"></div>

		<!-- Cosmic Title centered in galaxy -->
		<div class="galaxy-title">
			<div class="supernova-burst"></div>
			<h1 class="cosmic-text">✦ BeeThere Galaxy ✦</h1>
			<p class="galaxy-subtitle">Navigate the Cosmos of Collaboration</p>
		</div>

		<div v-if="loading" class="loading-galaxy">
			<v-progress-circular indeterminate color="#00ffff" size="150" width="3"></v-progress-circular>
			<p class="loading-text">🌌 Charting the cosmos... 🌌</p>
		</div>

		<div v-else-if="aboutInfo" class="galaxy-map">
			<!-- Central Mission Nebula -->
			<div class="central-nebula">
				<div class="nebula-core pulsing">
					<div class="core-rings"></div>
					<div class="core-rings"></div>
					<div class="core-rings"></div>
				</div>
				<div class="mission-text-flow">
					<h2 class="nebula-title">⋆｡°✩ Our Cosmic Purpose ⋆｡°✩</h2>
					<p class="mission-prophecy">
						{{ aboutInfo.missionStatement }}
					</p>
				</div>
			</div>

			<!-- Flowing star dust river -->
			<svg class="cosmic-paths" viewBox="0 0 1400 2000" xmlns="http://www.w3.org/2000/svg">
				<defs>
					<linearGradient id="nebula-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
						<stop offset="0%" style="stop-color:#ff00ff;stop-opacity:0.6">
							<animate attributeName="stop-color" values="#ff00ff;#00ffff;#ff00ff" dur="4s" repeatCount="indefinite"/>
						</stop>
						<stop offset="50%" style="stop-color:#00ffff;stop-opacity:0.8">
							<animate attributeName="stop-color" values="#00ffff;#ff00ff;#00ffff" dur="4s" repeatCount="indefinite"/>
						</stop>
						<stop offset="100%" style="stop-color:#ff00ff;stop-opacity:0.6">
							<animate attributeName="stop-color" values="#ff00ff;#00ffff;#ff00ff" dur="4s" repeatCount="indefinite"/>
						</stop>
					</linearGradient>
					<filter id="glow">
						<feGaussianBlur stdDeviation="4" result="coloredBlur"/>
						<feMerge>
							<feMergeNode in="coloredBlur"/>
							<feMergeNode in="SourceGraphic"/>
						</feMerge>
					</filter>
				</defs>
				
				<!-- Flowing cosmic river connecting everything -->
				<path class="stardust-path" 
					d="M 700 50 
					   Q 400 200 500 400
					   Q 600 600 300 800
					   Q 100 1000 500 1200
					   Q 900 1400 700 1600
					   Q 500 1800 700 1950"
					stroke="url(#nebula-gradient)"
					stroke-width="40"
					fill="none"
					filter="url(#glow)"
					opacity="0.4"/>
			</svg>

			<!-- Star System - Team as planets/stars -->
			<div class="star-system">
				<h2 class="system-title">✧˖°. The Stellar Collective ✧˖°.</h2>
				
				<div class="celestial-bodies">
					<div 
						v-for="(role, name, index) in aboutInfo.developmentTeam" 
						:key="name"
						class="planet-orbit"
						:style="getPlanetOrbit(index)"
					>
						<!-- Planet with rings and atmosphere -->
						<div class="planet" :class="`planet-${index}`">
							<div class="atmosphere"></div>
							<div class="planet-surface" :style="getPlanetColor(index)">
								<div class="planet-symbol">{{ getCelestialSymbol(index) }}</div>
							</div>
							<div class="planet-ring"></div>
							<div class="planet-info">
								<div class="info-bubble">
									<div class="planet-name">{{ name }}</div>
									<div class="planet-role">{{ role }}</div>
									<div class="planet-constellation">{{ getConstellation(index) }}</div>
								</div>
							</div>
						</div>
						<!-- Orbital trail -->
						<div class="orbit-trail"></div>
					</div>
				</div>
			</div>

			<!-- Wormhole divider -->
			<div class="wormhole-section">
				<svg class="wormhole" viewBox="0 0 400 400" xmlns="http://www.w3.org/2000/svg">
					<defs>
						<radialGradient id="wormhole-gradient">
							<stop offset="0%" style="stop-color:#000;stop-opacity:1" />
							<stop offset="30%" style="stop-color:#4a148c;stop-opacity:0.8" />
							<stop offset="60%" style="stop-color:#7b1fa2;stop-opacity:0.5" />
							<stop offset="100%" style="stop-color:transparent;stop-opacity:0" />
						</radialGradient>
					</defs>
					<circle cx="200" cy="200" r="180" fill="url(#wormhole-gradient)" class="wormhole-center"/>
					<circle cx="200" cy="200" r="150" fill="none" stroke="#00ffff" stroke-width="2" class="wormhole-ring ring-1"/>
					<circle cx="200" cy="200" r="120" fill="none" stroke="#ff00ff" stroke-width="2" class="wormhole-ring ring-2"/>
					<circle cx="200" cy="200" r="90" fill="none" stroke="#00ffff" stroke-width="2" class="wormhole-ring ring-3"/>
					<circle cx="200" cy="200" r="60" fill="none" stroke="#ff00ff" stroke-width="2" class="wormhole-ring ring-4"/>
				</svg>
			</div>

			<!-- Contact & Copyright - Floating space stations -->
			<div class="space-stations">
				<div class="station station-contact">
					<div class="station-structure">
						<div class="station-core">
							<v-icon size="70" class="station-icon rotating" color="#00ffff">mdi-satellite-variant</v-icon>
						</div>
						<div class="station-panels panel-left"></div>
						<div class="station-panels panel-right"></div>
					</div>
					<div class="transmission-beam"></div>
					<div class="station-data">
						<h3 class="data-title">◈ Transmission Frequency ◈</h3>
						<p class="data-content">{{ aboutInfo.contactUs }}</p>
					</div>
				</div>

				<div class="station station-decree">
					<div class="station-structure">
						<div class="station-core">
							<v-icon size="70" class="station-icon rotating" color="#ff00ff">mdi-shield-star</v-icon>
						</div>
						<div class="station-panels panel-left"></div>
						<div class="station-panels panel-right"></div>
					</div>
					<div class="transmission-beam beam-purple"></div>
					<div class="station-data">
						<h3 class="data-title">◈ Galactic Charter ◈</h3>
						<p class="data-content">{{ aboutInfo.copyright }}</p>
					</div>
				</div>
			</div>

			<!-- Cosmic footer -->
			<div class="galaxy-footer">
				<div class="supernova-line"></div>
				<p class="cosmic-blessing">
					✧ "Across infinite dimensions, we connect" ✧
				</p>
				<p class="final-blessing">
					⋆｡‧˚ʚ♡ɞ˚‧｡⋆ May your journey through the cosmos be enlightening ⋆｡‧˚ʚ♡ɞ˚‧｡⋆
				</p>
				<div class="constellation-footer">
					<span>✦</span><span>✧</span><span>⋆</span><span>✦</span><span>✧</span><span>⋆</span>
				</div>
			</div>
		</div>

		<div v-else class="error-galaxy">
			<v-icon size="150" color="#ff0000" class="error-icon">mdi-black-hole</v-icon>
			<p class="error-text">⚠️ Lost in a black hole! Navigation failed! ⚠️</p>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useAuthStore } from '../stores/auth-store.ts'
import { useSnackbarStore } from '../stores/snackbar-store.ts'

const snackbar = useSnackbarStore()
const aboutInfo = ref(null)
const loading = ref(true)
const auth = useAuthStore()

const celestialSymbols = ['🌟', '🪐', '☄️', '�', '✨', '�', '⭐', '🌠']
const constellations = ['Orion', 'Cassiopeia', 'Ursa Major', 'Phoenix', 'Draco', 'Andromeda', 'Pegasus', 'Lyra']
const planetColors = [
	'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
	'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
	'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
	'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
	'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
	'linear-gradient(135deg, #30cfd0 0%, #330867 100%)',
	'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)',
	'linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%)'
]

// Circular orbit positions (flowing around)
function getPlanetOrbit(index) {
	const total = 8
	const angle = (index / total) * Math.PI * 2
	const radius = 35 // percentage
	const centerX = 50
	const centerY = 50
	
	const x = centerX + radius * Math.cos(angle)
	const y = centerY + radius * Math.sin(angle)
	
	return {
		left: `${x}%`,
		top: `${y}%`,
		animationDelay: `${index * 0.5}s`
	}
}

function getStarStyle(n) {
	const size = Math.random() * 2 + 0.5
	const left = Math.random() * 100
	const top = Math.random() * 100
	const animationDuration = Math.random() * 4 + 2
	const delay = Math.random() * 3
	return {
		width: `${size}px`,
		height: `${size}px`,
		left: `${left}%`,
		top: `${top}%`,
		animationDuration: `${animationDuration}s`,
		animationDelay: `${delay}s`
	}
}

function getCelestialSymbol(index) {
	return celestialSymbols[index % celestialSymbols.length]
}

function getConstellation(index) {
	return constellations[index % constellations.length]
}

function getPlanetColor(index) {
	return {
		background: planetColors[index % planetColors.length]
	}
}

async function fetchAboutInfo() {
	try {
		loading.value = true
		const response = await axios.get('http://localhost:8080/about/', {
			headers: {
				'Bearer': auth.token,
				'Access-Control-Allow-Origin': '*'
			},
			responseType: 'json'
		})
		aboutInfo.value = response.data
		console.log('About info:', aboutInfo.value)
	} catch (error) {
		console.error('Error fetching about info:', error)
		snackbar.showSnackbar('Error loading about information', 'error')
	} finally {
		loading.value = false
	}
}

onMounted(() => {
	fetchAboutInfo()
})
</script>

<style scoped>
/* GALAXY REALM */
.galaxy-realm {
	min-height: 100vh;
	max-height: 100vh;
	overflow-y: auto;
	overflow-x: hidden;
	background: 
		radial-gradient(ellipse at 20% 30%, rgba(138, 43, 226, 0.3) 0%, transparent 50%),
		radial-gradient(ellipse at 80% 70%, rgba(255, 0, 255, 0.2) 0%, transparent 50%),
		radial-gradient(ellipse at 50% 50%, rgba(0, 255, 255, 0.1) 0%, transparent 50%),
		linear-gradient(180deg, #000428 0%, #004e92 50%, #000000 100%);
	position: relative;
	padding: 40px 20px;
	color: white;
}

/* ANIMATED NEBULAS */
.nebula-layer {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	pointer-events: none;
	z-index: 0;
	overflow: hidden;
}

.nebula {
	position: absolute;
	border-radius: 50%;
	filter: blur(80px);
	opacity: 0.3;
	animation: nebula-drift 20s ease-in-out infinite;
}

.nebula-1 {
	width: 600px;
	height: 600px;
	background: radial-gradient(circle, #ff00ff, transparent);
	top: -200px;
	left: -200px;
}

.nebula-2 {
	width: 800px;
	height: 800px;
	background: radial-gradient(circle, #00ffff, transparent);
	top: 30%;
	right: -300px;
	animation-delay: 5s;
}

.nebula-3 {
	width: 500px;
	height: 500px;
	background: radial-gradient(circle, #ff1744, transparent);
	bottom: 10%;
	left: 20%;
	animation-delay: 10s;
}

.nebula-4 {
	width: 700px;
	height: 700px;
	background: radial-gradient(circle, #7c4dff, transparent);
	bottom: -200px;
	right: 10%;
	animation-delay: 15s;
}

@keyframes nebula-drift {
	0%, 100% { transform: translate(0, 0) scale(1); }
	33% { transform: translate(50px, -50px) scale(1.1); }
	66% { transform: translate(-30px, 30px) scale(0.9); }
}

/* STARFIELD */
.starfield {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	pointer-events: none;
	z-index: 1;
}

.star {
	position: absolute;
	background: white;
	border-radius: 50%;
	animation: star-twinkle 3s ease-in-out infinite;
	box-shadow: 0 0 4px white;
}

@keyframes star-twinkle {
	0%, 100% { opacity: 0.3; transform: scale(1); }
	50% { opacity: 1; transform: scale(1.5); }
}

/* COMETS */
.comet-trail {
	position: fixed;
	width: 200px;
	height: 2px;
	background: linear-gradient(90deg, transparent, #00ffff, transparent);
	box-shadow: 0 0 10px #00ffff;
	z-index: 1;
	pointer-events: none;
}

.comet-1 {
	top: 20%;
	animation: comet-fly-1 8s linear infinite;
}

.comet-2 {
	top: 60%;
	animation: comet-fly-2 12s linear infinite;
	animation-delay: 4s;
}

@keyframes comet-fly-1 {
	0% { left: -200px; transform: rotate(-30deg); opacity: 0; }
	10% { opacity: 1; }
	90% { opacity: 1; }
	100% { left: 100%; transform: rotate(-30deg); opacity: 0; }
}

@keyframes comet-fly-2 {
	0% { left: -200px; transform: rotate(-20deg); opacity: 0; }
	10% { opacity: 1; }
	90% { opacity: 1; }
	100% { left: 100%; transform: rotate(-20deg); opacity: 0; }
}

/* GALAXY TITLE */
.galaxy-title {
	position: relative;
	z-index: 2;
	text-align: center;
	margin-bottom: 120px;
	padding: 60px 20px;
}

.supernova-burst {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 300px;
	height: 300px;
	background: radial-gradient(circle, rgba(0, 255, 255, 0.4), transparent 70%);
	border-radius: 50%;
	animation: supernova-pulse 4s ease-in-out infinite;
	filter: blur(40px);
}

@keyframes supernova-pulse {
	0%, 100% { transform: translate(-50%, -50%) scale(0.8); opacity: 0.3; }
	50% { transform: translate(-50%, -50%) scale(1.5); opacity: 0.6; }
}

.cosmic-text {
	font-family: 'Georgia', 'Palatino', serif;
	font-size: 5rem;
	font-weight: bold;
	background: linear-gradient(45deg, #00ffff, #ff00ff, #00ffff, #ff00ff);
	background-size: 300% 300%;
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	background-clip: text;
	animation: cosmic-flow 5s ease infinite;
	text-shadow: 0 0 40px rgba(0, 255, 255, 0.5);
	margin: 0 0 20px 0;
	letter-spacing: 0.2em;
	position: relative;
	z-index: 1;
}

@keyframes cosmic-flow {
	0%, 100% { background-position: 0% 50%; }
	50% { background-position: 100% 50%; }
}

.galaxy-subtitle {
	font-family: 'Georgia', serif;
	font-size: 1.8rem;
	color: #00ffff;
	text-shadow: 0 0 20px #00ffff, 0 0 40px #00ffff;
	font-style: italic;
	animation: subtitle-glow 3s ease-in-out infinite;
	position: relative;
	z-index: 1;
}

@keyframes subtitle-glow {
	0%, 100% { text-shadow: 0 0 20px #00ffff; }
	50% { text-shadow: 0 0 40px #00ffff, 0 0 80px #00ffff; }
}

/* LOADING GALAXY */
.loading-galaxy {
	position: relative;
	z-index: 2;
	text-align: center;
	padding: 200px 0;
}

.loading-text {
	font-family: 'Georgia', serif;
	font-size: 2.2rem;
	color: #00ffff;
	margin-top: 50px;
	text-shadow: 0 0 20px #00ffff;
	animation: subtitle-glow 2s ease-in-out infinite;
}

/* GALAXY MAP */
.galaxy-map {
	position: relative;
	z-index: 2;
	max-width: 1600px;
	margin: 0 auto;
	padding: 40px 20px;
}

/* COSMIC PATHS */
.cosmic-paths {
	position: absolute;
	top: 0;
	left: 50%;
	transform: translateX(-50%);
	width: 100%;
	height: 100%;
	z-index: 0;
	pointer-events: none;
	opacity: 0.6;
}

.stardust-path {
	animation: path-glow 4s ease-in-out infinite;
}

/* COSMIC RIVER */
.cosmic-river {
	position: relative;
	margin: 100px 0;
	text-align: center;
}

.river-flow {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	height: 100%;
	background: linear-gradient(90deg, transparent 0%, rgba(156, 39, 176, 0.3) 50%, transparent 100%);
	animation: flow-river 3s ease-in-out infinite;
	pointer-events: none;
}

@keyframes flow-river {
	0%, 100% { opacity: 0.3; transform: scaleX(1); }
	50% { opacity: 0.6; transform: scaleX(1.5); }
}

.mission-constellation {
	position: relative;
	padding: 60px 40px;
}

.constellation-star {
	display: block;
	margin: 0 auto 30px;
	filter: drop-shadow(0 0 30px #9c27b0);
}

.orbit-icon {
	animation: orbit-spin 6s linear infinite, pulse-divine 3s ease-in-out infinite;
}

@keyframes orbit-spin {
	to { transform: rotate(360deg); }
}

.cosmic-heading {
	font-family: 'Georgia', serif;
	font-size: 4rem;
	color: #ffd700;
	text-shadow: 0 0 30px #ffd700, 0 0 60px #ffab00;
	margin-bottom: 40px;
	animation: float-rune 4s ease-in-out infinite;
}

.prophecy-text {
	font-family: 'Georgia', serif;
	font-size: 2.5rem;
	font-weight: bold;
	line-height: 1.8;
	background: linear-gradient(135deg, #e1bee7, #ce93d8, #ba68c8, #ab47bc);
	background-size: 200% 200%;
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	background-clip: text;
	animation: rainbow-flow 3s ease infinite;
	max-width: 900px;
	margin: 0 auto 40px;
}

.mystical-symbols {
	font-size: 3rem;
	color: #ffd700;
}

.symbol {
	display: inline-block;
	margin: 0 30px;
	animation: spin-symbol 4s ease-in-out infinite;
	filter: drop-shadow(0 0 20px #ffd700);
}

.symbol:nth-child(2) { animation-delay: 0.7s; }
.symbol:nth-child(3) { animation-delay: 1.4s; }

@keyframes spin-symbol {
	0%, 100% { transform: rotate(0deg) scale(1); }
	50% { transform: rotate(180deg) scale(1.3); }
}

/* ETHEREAL DIVIDER */
.ethereal-divider {
	position: relative;
	height: 100px;
	margin: 80px 0;
}

.energy-wave {
	position: absolute;
	top: 50%;
	left: 0;
	right: 0;
	height: 3px;
	background: linear-gradient(90deg, transparent, #e91e63, #9c27b0, #e91e63, transparent);
	animation: wave-flow 3s ease-in-out infinite;
}

.energy-wave:nth-child(2) {
	animation-delay: 0.5s;
	opacity: 0.6;
}

.energy-wave:nth-child(3) {
	animation-delay: 1s;
	opacity: 0.3;
}

@keyframes wave-flow {
	0%, 100% { transform: translateX(-100%); }
	50% { transform: translateX(100%); }
}

/* CONSTELLATION MAP */
.constellation-map {
	position: relative;
	margin: 150px 0;
	min-height: 1000px;
}

.constellation-title {
	text-align: center;
	font-family: 'Georgia', serif;
	font-size: 3.5rem;
	color: #e1bee7;
	text-shadow: 0 0 30px #ab47bc;
	margin-bottom: 80px;
	animation: glow-pulse 3s ease-in-out infinite;
}

.constellation-lines {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 0;
	pointer-events: none;
}

.constellation-path {
	animation: constellation-glow 3s ease-in-out infinite;
}

@keyframes constellation-glow {
	0%, 100% { stroke-opacity: 0.3; }
	50% { stroke-opacity: 0.8; }
}

.floating-souls {
	position: relative;
	z-index: 1;
}

.soul-orb {
	position: absolute;
	width: 180px;
	height: 180px;
	animation: float-soul 6s ease-in-out infinite;
	cursor: pointer;
	transition: transform 0.3s ease;
}

.soul-orb:nth-child(odd) { animation-delay: 1s; }
.soul-orb:hover {
	transform: scale(1.3) !important;
	z-index: 100;
}

@keyframes float-soul {
	0%, 100% { transform: translateY(0px); }
	50% { transform: translateY(-30px); }
}

.orb-glow {
	position: absolute;
	inset: -20px;
	border-radius: 50%;
	background: radial-gradient(circle, rgba(156, 39, 176, 0.4), transparent 70%);
	animation: pulse-orb 2s ease-in-out infinite;
}

@keyframes pulse-orb {
	0%, 100% { transform: scale(0.8); opacity: 0.5; }
	50% { transform: scale(1.2); opacity: 1; }
}

.orb-inner {
	position: relative;
	width: 100%;
	height: 100%;
	border-radius: 50%;
	background: radial-gradient(circle at 30% 30%, rgba(186, 104, 200, 0.9), rgba(74, 20, 140, 0.9));
	border: 3px solid #ffd700;
	box-shadow: 0 0 40px rgba(255, 215, 0, 0.6), inset 0 0 30px rgba(255, 215, 0, 0.3);
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 15px;
}

.soul-symbol {
	font-size: 3rem;
	margin-bottom: 10px;
	animation: rotate-slow 8s linear infinite;
}

.soul-name {
	font-family: 'Georgia', serif;
	font-size: 1.1rem;
	font-weight: bold;
	color: #fff;
	text-shadow: 0 2px 10px rgba(0,0,0,0.8);
	margin-bottom: 5px;
	text-align: center;
}

.soul-role {
	font-family: 'Georgia', serif;
	font-size: 0.85rem;
	color: #ffd700;
	font-style: italic;
	margin-bottom: 8px;
	text-align: center;
}

.soul-element {
	font-size: 0.75rem;
	color: #e0b3ff;
	text-transform: uppercase;
	letter-spacing: 2px;
}

.orb-ring {
	position: absolute;
	inset: -15px;
	border: 2px solid rgba(255, 215, 0, 0.3);
	border-radius: 50%;
	animation: rotate-ring 10s linear infinite;
}

@keyframes rotate-ring {
	to { transform: rotate(-360deg); }
}

/* PORTAL SECTION */
.portal-section {
	position: relative;
	text-align: center;
	margin: 150px 0;
	height: 300px;
}

.portal {
	position: relative;
	width: 250px;
	height: 250px;
	margin: 0 auto;
}

.portal-ring {
	position: absolute;
	inset: 0;
	border: 3px solid;
	border-color: #9c27b0 transparent #e91e63 transparent;
	border-radius: 50%;
	animation: rotate-portal 4s linear infinite;
}

.portal-ring:nth-child(2) {
	inset: 20px;
	animation-delay: 0.5s;
	animation-direction: reverse;
}

.portal-ring:nth-child(3) {
	inset: 40px;
	animation-delay: 1s;
}

@keyframes rotate-portal {
	to { transform: rotate(360deg); }
}

.portal-center {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	font-size: 5rem;
	color: #ffd700;
	text-shadow: 0 0 40px #ffd700;
	animation: pulse-divine 2s ease-in-out infinite;
}

/* ANCIENT SCROLLS */
.ancient-scrolls {
	display: flex;
	justify-content: center;
	gap: 100px;
	flex-wrap: wrap;
	margin: 150px 0;
	padding: 0 40px;
}

.scroll {
	position: relative;
	animation: float-scroll 5s ease-in-out infinite;
}

.scroll-left { animation-delay: 0s; }
.scroll-right { animation-delay: 1s; }

@keyframes float-scroll {
	0%, 100% { transform: translateY(0px) rotate(0deg); }
	50% { transform: translateY(-20px) rotate(2deg); }
}

.scroll-rod {
	width: 350px;
	height: 15px;
	background: linear-gradient(90deg, #3e2723, #5d4037, #3e2723);
	border-radius: 10px;
	box-shadow: 0 2px 10px rgba(0,0,0,0.5);
	position: relative;
}

.scroll-rod.top::before,
.scroll-rod.top::after,
.scroll-rod.bottom::before,
.scroll-rod.bottom::after {
	content: '';
	position: absolute;
	width: 25px;
	height: 25px;
	background: radial-gradient(circle, #ffd700, #ff6f00);
	border-radius: 50%;
	top: 50%;
	transform: translateY(-50%);
}

.scroll-rod.top::before { left: -10px; }
.scroll-rod.top::after { right: -10px; }
.scroll-rod.bottom::before { left: -10px; }
.scroll-rod.bottom::after { right: -10px; }

.scroll-paper {
	width: 350px;
	background: linear-gradient(180deg, #fff9e6, #f5e6d3);
	padding: 40px 30px;
	text-align: center;
	box-shadow: 0 10px 40px rgba(0,0,0,0.3);
	position: relative;
}

.scroll-icon {
	animation: pulse-glow 3s ease-in-out infinite;
	margin-bottom: 20px;
}

.pulse-glow {
	animation: pulse-glow 3s ease-in-out infinite !important;
}

@keyframes pulse-glow {
	0%, 100% { filter: drop-shadow(0 0 10px currentColor); transform: scale(1); }
	50% { filter: drop-shadow(0 0 30px currentColor); transform: scale(1.1); }
}

.scroll-title {
	font-family: 'Georgia', serif;
	font-size: 2rem;
	color: #4a148c;
	margin-bottom: 20px;
}

.scroll-text {
	font-family: 'Georgia', serif;
	font-size: 1.3rem;
	color: #3e2723;
	margin-bottom: 20px;
	font-weight: 600;
}

.magical-seal {
	font-size: 3rem;
	color: #d32f2f;
	animation: rotate-slow 6s linear infinite;
}

/* REALM FOOTER */
.realm-footer {
	text-align: center;
	margin: 150px 0 80px;
}

.dragon-divider {
	font-size: 4rem;
	margin-bottom: 40px;
}

.dragon {
	display: inline-block;
	animation: dragon-float 4s ease-in-out infinite;
	filter: drop-shadow(0 0 20px #ff5722);
}

.dragon.mirror {
	transform: scaleX(-1);
	animation-delay: 2s;
}

@keyframes dragon-float {
	0%, 100% { transform: translateY(0px); }
	50% { transform: translateY(-30px); }
}

.fire {
	display: inline-block;
	color: #ff5722;
	margin: 0 50px;
	animation: flicker 1s ease-in-out infinite;
}

@keyframes flicker {
	0%, 100% { opacity: 1; text-shadow: 0 0 20px #ff5722; }
	50% { opacity: 0.5; text-shadow: 0 0 40px #ff5722, 0 0 60px #ff9800; }
}

.epic-quote {
	font-family: 'Georgia', serif;
	font-size: 2rem;
	color: #e0b3ff;
	font-style: italic;
	margin-bottom: 30px;
	text-shadow: 0 0 20px #9c27b0;
}

.blessed-text {
	font-family: 'Georgia', serif;
	font-size: 1.8rem;
	color: #ffd700;
	margin-bottom: 30px;
	animation: glow-pulse 2s ease-in-out infinite;
}

.final-runes {
	font-size: 2.5rem;
	color: #ffd700;
}

.final-runes span {
	display: inline-block;
	margin: 0 20px;
	animation: float-rune 3s ease-in-out infinite;
}

.final-runes span:nth-child(1) { animation-delay: 0s; }
.final-runes span:nth-child(2) { animation-delay: 0.3s; }
.final-runes span:nth-child(3) { animation-delay: 0.6s; }
.final-runes span:nth-child(4) { animation-delay: 0.9s; }
.final-runes span:nth-child(5) { animation-delay: 1.2s; }

/* ERROR REALM */
.error-realm {
	position: relative;
	z-index: 1;
	text-align: center;
	padding: 150px 0;
}

.error-icon {
	animation: shake-error 0.5s ease-in-out infinite;
	filter: drop-shadow(0 0 30px red);
}

@keyframes shake-error {
	0%, 100% { transform: rotate(0deg); }
	25% { transform: rotate(-10deg); }
	75% { transform: rotate(10deg); }
}

.error-text {
	font-family: 'Georgia', serif;
	font-size: 2.5rem;
	color: #ff1744;
	margin-top: 40px;
	text-shadow: 0 0 30px #ff1744;
}

/* Scrollbar styling */
.mythical-realm::-webkit-scrollbar {
	width: 12px;
}

.mythical-realm::-webkit-scrollbar-track {
	background: #1a0033;
}

.mythical-realm::-webkit-scrollbar-thumb {
	background: linear-gradient(180deg, #9c27b0, #e91e63);
	border-radius: 10px;
}

.mythical-realm::-webkit-scrollbar-thumb:hover {
	background: linear-gradient(180deg, #e91e63, #ffd700);
}
</style>
