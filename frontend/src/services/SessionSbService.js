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
     * @returns {Promise<User>}
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
            const user = await response.json();
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
                user
            );

            return user;
        } else {
            console.log(response);
            return null;
        }
    }

    /**
     * Discards user details and the JWT authentication token from the service.
     */
    signOut() {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
    }

    /**
     * saves the JWT authentication token and user details into the service and browser
     * storage for automatic retrieval after application or page reload.
     *
     * @param {string} token The JWT token to store
     * @param {User} user The logged-in user
     */
    saveTokenIntoBrowserStorage(token, user) {
        localStorage.setItem("token", token);
        localStorage.setItem("user", JSON.stringify(user));
    }

    /**
     * Retrieves the JWT authentication token and user details from the browser storage
     * into the service after application or page reload.
     *
     * @returns {string}
     */
    getTokenFromBrowserStorage() {
        localStorage.getItem("user");
        return localStorage.getItem("token");
    }

    getUser() {
        const storedUser = JSON.parse(localStorage.getItem("user"));
        return storedUser;
    }

    getCurrentToken() {
        return localStorage.getItem("token");
    }

    /**
     * Check if a user is authenticated
     */
    isAuthenticated() {
        return !!localStorage.getItem("token");
    }
}