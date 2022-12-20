import User from "@/models/User";

/**
 * @Author: Dylan Weijgertze
 */
export default class SessionSbServiceInMemory {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCE_URL;
    router;
    static isLoggedIn;

    storedUser;
    storedToken;
    storedRefreshToken;

    constructor(currentUser) {
        this.storedUser = currentUser;
    }

    getLoggedIn() {
        return SessionSbServiceInMemory.isLoggedIn;
    }

    /**
     * Logs on to the backend, and retrieves user details and the JWT authentication token
     * from the backend.
     *
     * @param {string} email
     * @param {string} password
     * @returns {Promise<Object>}
     */
    signIn(email, password) {}

    /**
     * Attempt to sign in with info still in the browser
     *
     * @return {boolean|Promise<boolean>}
     */
    signInFromBrowserStorage() {}

    /**
     * Refresh the JWT token
     *
     * @return {Promise<null|*>}
     */
    async refreshJWT() {}

    /**
     * Check if the JWT token is expired
     *
     * @return {boolean} If the token is expired or not
     */
    isTokenExpired() {}

    /**
     * Discards user details and the JWT authentication token from the service.
     */
    signOut() {
    }

    /**
     * saves the JWT authentication token and user details into the service and browser
     * storage for automatic retrieval after application or page reload.
     *
     * @param {string} token The JWT token to store
     * @param {string} refreshToken The token to refresh the JWT with
     */
    saveTokensIntoBrowserStorage(token, refreshToken) {
    }

    /**
     * Retrieves the JWT authentication token from the browser storage
     *
     * @returns {string}
     */
    getTokenFromBrowserStorage() {
    }

    getRefreshTokenFromBrowserStorage() {
    }

    /**
     * Get the user stored in the browser
     *
     * @return {User|void} the current logged-in user
     */
    getCurrentUser() {
        const storedUser = this.storedUser;

        // Sign out if user isn't stored
        if (storedUser === null) {
            this.signOut();
            return;
        }

        return User.createUserFromJson(storedUser);
    }
}