import User from "@/models/User";

/**
 * @Author: Dylan Weijgertze
 */
export default class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCE_URL;
    router;
    static isLoggedIn;

    constructor(resourceUrl, browserStorageItemName, router) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCE_URL = resourceUrl;
        this.router = router;
        SessionSbService.isLoggedIn = this.signInFromBrowserStorage();
    }

    getLoggedIn() {
        return SessionSbService.isLoggedIn;
    }

    /**
     * Logs on to the backend, and retrieves user details and the JWT authentication token
     * from the backend.
     *
     * @param {string} email
     * @param {string} password
     * @returns {Promise<Object>}
     */
    async signIn(email, password) {
        const body = JSON.stringify({email, password});
        const response = await fetch(this.RESOURCE_URL + '/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body,
            credentials: 'include'
        });

        if (response.ok) {
            const data = await response.json();
            // Store data locally
            this.saveTokensIntoBrowserStorage(data.jwtToken, data.refreshToken);
            localStorage.setItem("user", JSON.stringify(data.user));
            SessionSbService.isLoggedIn = true;

            return data;
        } else {
            this.signOut();
            return null;
        }
    }

    /**
     * Attempt to sign in with info still in the browser
     *
     * @return {boolean|Promise<boolean>}
     */
    signInFromBrowserStorage() {
        const token = localStorage.getItem("token");
        const refreshToken = localStorage.getItem("refreshToken");
        const user = this.getCurrentUser();

        // logout if userInfo is missing
        if (!token || !refreshToken || !user) {
            this.signOut();
            return false;
        }

        if (this.isTokenExpired()) {
            return this.refreshJWT().then(token => !!token);
        }

        return true;
    }

    /**
     * Refresh the JWT token
     *
     * @return {Promise<null|*>}
     */
    async refreshJWT() {
        const refreshToken = this.getRefreshTokenFromBrowserStorage();
        // Remove current tokens
        localStorage.removeItem("token");

        // Get the new token
        const body = JSON.stringify({refreshToken});
        const response = await fetch(`${this.RESOURCE_URL}/refresh-token`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body,
            credentials: 'include'
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.jwtToken);
            SessionSbService.isLoggedIn = true;
            return data.jwtToken;
        } else {
            this.signOut();
            return null;
        }
    }

    /**
     * Check if the JWT token is expired
     *
     * @return {boolean} If the token is expired or not
     */
    isTokenExpired() {
        const token = this.getTokenFromBrowserStorage();
        // Check if token exists
        if (token != null) {
            const payload = atob(token.split('.')[1]);
            const expiration = JSON.parse(payload).exp;
            return expiration < (Date.now() / 1000);
        } else {
            return false;
        }
    }

    /**
     * Discards user details and the JWT authentication token from the service.
     */
    signOut() {
        localStorage.removeItem("token");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("user");
        SessionSbService.isLoggedIn = false;
    }

    /**
     * saves the JWT authentication token and user details into the service and browser
     * storage for automatic retrieval after application or page reload.
     *
     * @param {string} token The JWT token to store
     * @param {string} refreshToken The token to refresh the JWT with
     */
    saveTokensIntoBrowserStorage(token, refreshToken) {
        localStorage.setItem("token", token);
        localStorage.setItem("refreshToken", refreshToken);
    }

    /**
     * Retrieves the JWT authentication token from the browser storage
     *
     * @returns {string}
     */
    getTokenFromBrowserStorage() {
        return localStorage.getItem("token");
    }

    getRefreshTokenFromBrowserStorage() {
        return localStorage.getItem("refreshToken");
    }

    /**
     * Get the user stored in the browser
     *
     * @return {User|void} the current logged-in user
     */
    getCurrentUser() {
        const storedUser = JSON.parse(localStorage.getItem("user"));

        // Sign out if user isn't stored
        if (storedUser === null) {
            this.signOut();
            return;
        }

        return User.createUserFromJson(storedUser);
    }
}