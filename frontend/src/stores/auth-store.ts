export default class AuthStore {
	public static storageKey: string = 'token';
	
	public static getToken() {
		return localStorage.getItem(AuthStore.storageKey);
	}

	public static setToken(token: string) {
		localStorage.setItem(AuthStore.storageKey, token);
	}

	public static removeToken(): void {
		localStorage.removeItem(AuthStore.storageKey);
	}

	public static isSignedIn(): boolean {
		return !!AuthStore.getToken();
	}
}
