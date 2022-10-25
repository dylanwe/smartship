import fetchIntercept from "fetch-intercept";

export default class FetchInterceptor {
    // the singleton instance that has been registered
    static theInstance;
    session;
    router;
    // callback function to unregister this instance at shutdown
    unregister;

    constructor(session, router) {
        this.session = session;
        this.router = router;

        FetchInterceptor.theInstance = this;
        this.unregister = fetchIntercept.register(this);
    }

    async request(url, options) {
        let token = FetchInterceptor.theInstance.session.getTokenFromBrowserStorage();

        // Check if token is expired
        if (FetchInterceptor.theInstance.session.isTokenExpired()) {
            token = await FetchInterceptor.theInstance.session.refreshJWT();
        }

        if (token == null) {
            // No change
            return [url, options];
        } else if (options == null) {
            // The authorization header is the only custom option
            return [url, {headers: {Authorization: token}}];
        } else {
            // Add authorization header to other options
            let newOptions = {...options};
            newOptions.headers = {
                // TODO MIX WITH GIVEN HEADERS
                'Content-Type': 'application/json',
                Authorization: token
            }
            return [url, newOptions];
        }
    }

    requestError(error) {
        return Promise.reject(error);
    }

    response(response) {
        switch (response.status) {
            case 401:
                // Redirect home
                FetchInterceptor.theInstance.session.signOut();
                FetchInterceptor.theInstance.router.push("/");
                return Promise.reject(response);
            default:
                return response
        }
    }

    responseError(error) {
        return Promise.reject(error);
    }
}