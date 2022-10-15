export default class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCE_URL;

    constructor(resourceUrl, browserStorageItemName) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCE_URL = resourceUrl;
        this.getTokenFromBrowserStorage();
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
            this.saveTokensIntoBrowserStorage(data.jwtToken, data.refreshToken);

            return data;
        } else {
            console.log(response);
            return null;
        }
    }

    /**
     * Refresh the JWT token
     *
     * @return {Promise<null|*>}
     */
    async refreshJWT() {
        const refreshToken = this.getRefreshTokenFromBrowserStorage();
        // Remove current tokens
        this.signOut();

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
            this.saveTokensIntoBrowserStorage(data.jwtToken, refreshToken);
            return data.jwtToken;
        } else {
            this.signOut();
            return null;
        }
    }

    /**
     * Check if the JWT token is expired
     *
     * @param token The token to check the expiration of
     * @return {boolean} If the token is expired or not
     */
    isTokenExpired(token) {
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
     * Retrieves the JWT authentication token and user details from the browser storage
     * into the service after application or page reload.
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
     * Check if a user is authenticated
     */
    isAuthenticated() {
        return !this.isTokenExpired(this.getTokenFromBrowserStorage());
    }
}