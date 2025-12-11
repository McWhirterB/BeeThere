<template>
	<div class="mythical-realm">
		<!-- Floating particles/stars background -->
		<div class="cosmic-particles">
			<div v-for="n in 50" :key="n" class="particle" :style="getParticleStyle(n)"></div>
		</div>

		<!-- Floating ethereal title -->
		<div class="ethereal-header">
			<div class="magic-circle">
				<v-icon size="150" class="divine-icon" color="#ffd700">mdi-star-four-points</v-icon>
			</div>
			<h1 class="mythical-title">✨ BeeThere ✨</h1>
			<p class="subtitle-glow">Where Reality Meets Dreams</p>
			<div class="floating-runes">
				<span class="rune">☽</span>
				<span class="rune">✧</span>
				<span class="rune">☾</span>
			</div>
		</div>

		<div v-if="loading" class="loading-realm">
			<v-progress-circular indeterminate color="purple" size="120" width="8"></v-progress-circular>
			<p class="loading-text">✨ Summoning ancient wisdom... ✨</p>
		</div>

		<div v-else-if="aboutInfo" class="content-flow">
			<!-- Mission - Flowing cosmic river -->
			<div class="cosmic-river">
				<div class="river-flow"></div>
				<div class="mission-constellation">
					<v-icon size="100" class="constellation-star orbit-icon" color="#9c27b0">mdi-creation</v-icon>
					<h2 class="cosmic-heading">🌙 Our Sacred Mission 🌙</h2>
					<p class="prophecy-text">
						{{ aboutInfo.missionStatement }}
					</p>
					<div class="mystical-symbols">
						<span class="symbol">◈</span>
						<span class="symbol">❖</span>
						<span class="symbol">◈</span>
					</div>
				</div>
			</div>

			<!-- Floating ethereal divider -->
			<div class="ethereal-divider">
				<div class="energy-wave"></div>
				<div class="energy-wave"></div>
				<div class="energy-wave"></div>
			</div>

			<!-- Team - Scattered like constellations -->
			<div class="constellation-map">
				<h2 class="constellation-title">✧･ﾟ: *✧･ﾟ:* The Council of Creators *:･ﾟ✧*:･ﾟ✧</h2>
				<svg class="constellation-lines" viewBox="0 0 1200 1000">
					<defs>
						<linearGradient id="line-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
							<stop offset="0%" style="stop-color:#9c27b0;stop-opacity:0.3" />
							<stop offset="50%" style="stop-color:#e91e63;stop-opacity:0.5" />
							<stop offset="100%" style="stop-color:#9c27b0;stop-opacity:0.3" />
						</linearGradient>
					</defs>
					<!-- Mystical connecting lines between team members -->
					<path d="M 200 150 L 500 200 L 800 100 L 300 400 L 600 450 L 900 500 L 400 700 L 700 750" 
						stroke="url(#line-gradient)" 
						stroke-width="2" 
						fill="none"
						stroke-dasharray="5,5"
						class="constellation-path"/>
				</svg>
				
				<div class="floating-souls">
					<div 
						v-for="(role, name, index) in aboutInfo.developmentTeam" 
						:key="name"
						class="soul-orb"
						:style="getSoulPosition(index)"
					>
						<div class="orb-glow" :style="`animation-delay: ${index * 0.3}s`"></div>
						<div class="orb-inner">
							<div class="soul-symbol">{{ getMythicalSymbol(index) }}</div>
							<div class="soul-name">{{ name }}</div>
							<div class="soul-role">{{ role }}</div>
							<div class="soul-element">{{ getElement(index) }}</div>
						</div>
						<div class="orb-ring"></div>
					</div>
				</div>
			</div>

			<!-- Mystical portal divider -->
			<div class="portal-section">
				<div class="portal">
					<div class="portal-ring"></div>
					<div class="portal-ring"></div>
					<div class="portal-ring"></div>
					<div class="portal-center">∞</div>
				</div>
			</div>

			<!-- Contact & Copyright - Floating scrolls -->
			<div class="ancient-scrolls">
				<div class="scroll scroll-left">
					<div class="scroll-rod top"></div>
					<div class="scroll-paper">
						<v-icon size="80" class="scroll-icon pulse-glow" color="#00bcd4">mdi-email-seal</v-icon>
						<h3 class="scroll-title">📜 Summon Us 📜</h3>
						<p class="scroll-text">{{ aboutInfo.contactUs }}</p>
						<div class="magical-seal">☆</div>
					</div>
					<div class="scroll-rod bottom"></div>
				</div>

				<div class="scroll scroll-right">
					<div class="scroll-rod top"></div>
					<div class="scroll-paper">
						<v-icon size="80" class="scroll-icon pulse-glow" color="#ff5722">mdi-shield-crown</v-icon>
						<h3 class="scroll-title">⚔️ Ancient Decree ⚔️</h3>
						<p class="scroll-text">{{ aboutInfo.copyright }}</p>
						<div class="magical-seal">✦</div>
					</div>
					<div class="scroll-rod bottom"></div>
				</div>
			</div>

			<!-- Epic footer -->
			<div class="realm-footer">
				<div class="dragon-divider">
					<span class="dragon">🐉</span>
					<span class="fire">༄</span>
					<span class="dragon mirror">🐉</span>
				</div>
				<p class="epic-quote">
					"In the realm of BeeThere, meetings transcend time and space"
				</p>
				<p class="blessed-text">
					✧༺♥༻∞ May your reservations be blessed ∞༺♥༻✧
				</p>
				<div class="final-runes">
					<span>◈</span><span>❖</span><span>✦</span><span>☆</span><span>✧</span>
				</div>
			</div>
		</div>

		<div v-else class="error-realm">
			<v-icon size="150" color="red" class="error-icon">mdi-skull</v-icon>
			<p class="error-text">⚠️ The ancient magic has failed! ⚠️</p>
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

const mythicalSymbols = ['☯', '⚡', '🔮', '🌟', '⚔️', '🔱', '👁️', '🦅']
const elements = ['Fire', 'Water', 'Earth', 'Air', 'Spirit', 'Light', 'Shadow', 'Ether']

// Soul positions - scattered like stars
const soulPositions = [
	{ top: '10%', left: '15%' },
	{ top: '25%', left: '45%' },
	{ top: '15%', left: '75%' },
	{ top: '45%', left: '20%' },
	{ top: '50%', left: '55%' },
	{ top: '55%', left: '85%' },
	{ top: '75%', left: '30%' },
	{ top: '80%', left: '65%' }
]

function getParticleStyle(n) {
	const size = Math.random() * 3 + 1
	const left = Math.random() * 100
	const animationDuration = Math.random() * 3 + 2
	const delay = Math.random() * 2
	return {
		width: `${size}px`,
		height: `${size}px`,
		left: `${left}%`,
		top: `${Math.random() * 100}%`,
		animationDuration: `${animationDuration}s`,
		animationDelay: `${delay}s`
	}
}

function getSoulPosition(index) {
	const pos = soulPositions[index % soulPositions.length]
	return pos
}

function getMythicalSymbol(index) {
	return mythicalSymbols[index % mythicalSymbols.length]
}

function getElement(index) {
	return elements[index % elements.length]
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
/* MYTHICAL REALM CONTAINER */
.mythical-realm {
	min-height: 100vh;
	max-height: 100vh;
	overflow-y: auto;
	overflow-x: hidden;
	background: radial-gradient(ellipse at top, #1a0033 0%, #000000 50%, #0a0015 100%);
	position: relative;
	padding: 60px 20px;
	color: white;
}

/* COSMIC PARTICLES */
.cosmic-particles {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	pointer-events: none;
	z-index: 0;
}

.particle {
	position: absolute;
	background: radial-gradient(circle, #ffffff 0%, transparent 70%);
	border-radius: 50%;
	animation: twinkle 3s ease-in-out infinite, float-particle 5s ease-in-out infinite;
	opacity: 0.6;
}

@keyframes twinkle {
	0%, 100% { opacity: 0.3; }
	50% { opacity: 1; }
}

@keyframes float-particle {
	0%, 100% { transform: translateY(0px); }
	50% { transform: translateY(-30px); }
}

/* ETHEREAL HEADER */
.ethereal-header {
	position: relative;
	z-index: 1;
	text-align: center;
	margin-bottom: 100px;
}

.magic-circle {
	position: relative;
	display: inline-block;
	margin-bottom: 30px;
}

.magic-circle::before {
	content: '';
	position: absolute;
	inset: -30px;
	border: 3px solid;
	border-color: #ffd700 transparent #ffd700 transparent;
	border-radius: 50%;
	animation: rotate-circle 8s linear infinite;
}

@keyframes rotate-circle {
	to { transform: rotate(360deg); }
}

.divine-icon {
	animation: pulse-divine 3s ease-in-out infinite, rotate-slow 10s linear infinite;
	filter: drop-shadow(0 0 30px #ffd700);
}

@keyframes pulse-divine {
	0%, 100% { transform: scale(1); opacity: 0.8; }
	50% { transform: scale(1.2); opacity: 1; }
}

@keyframes rotate-slow {
	to { transform: rotate(360deg); }
}

.mythical-title {
	font-family: 'Georgia', 'Palatino', serif;
	font-size: 6rem;
	font-weight: bold;
	background: linear-gradient(45deg, #ffd700, #ff00ff, #00ffff, #ffd700);
	background-size: 300% 300%;
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	background-clip: text;
	animation: rainbow-flow 4s ease infinite;
	text-shadow: 2px 2px 20px rgba(255, 215, 0, 0.5);
	margin: 20px 0;
	letter-spacing: 0.3em;
}

@keyframes rainbow-flow {
	0%, 100% { background-position: 0% 50%; }
	50% { background-position: 100% 50%; }
}

.subtitle-glow {
	font-family: 'Georgia', serif;
	font-size: 2rem;
	color: #e0b3ff;
	text-shadow: 0 0 20px #b388ff, 0 0 40px #7c4dff;
	font-style: italic;
	animation: glow-pulse 2s ease-in-out infinite;
}

@keyframes glow-pulse {
	0%, 100% { text-shadow: 0 0 20px #b388ff, 0 0 40px #7c4dff; }
	50% { text-shadow: 0 0 40px #b388ff, 0 0 80px #7c4dff, 0 0 120px #651fff; }
}

.floating-runes {
	margin-top: 30px;
	font-size: 3rem;
}

.rune {
	display: inline-block;
	margin: 0 40px;
	color: #ffd700;
	animation: float-rune 3s ease-in-out infinite;
	filter: drop-shadow(0 0 15px #ffd700);
}

.rune:nth-child(2) { animation-delay: 0.5s; }
.rune:nth-child(3) { animation-delay: 1s; }

@keyframes float-rune {
	0%, 100% { transform: translateY(0px) rotate(0deg); }
	50% { transform: translateY(-20px) rotate(180deg); }
}

/* LOADING REALM */
.loading-realm {
	position: relative;
	z-index: 1;
	text-align: center;
	padding: 150px 0;
}

.loading-text {
	font-family: 'Georgia', serif;
	font-size: 2rem;
	color: #e0b3ff;
	margin-top: 40px;
	animation: glow-pulse 2s ease-in-out infinite;
}

/* CONTENT FLOW */
.content-flow {
	position: relative;
	z-index: 1;
	max-width: 1400px;
	margin: 0 auto;
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
