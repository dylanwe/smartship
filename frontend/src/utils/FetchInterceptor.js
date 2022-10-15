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

    request(url, options) {
        let token = FetchInterceptor.theInstance.session.getTokenFromBrowserStorage();

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
                Authorization: token
                // TODO combine new Authorization header with existing headers
            }
            return [url, newOptions];
        }
    }

    requestError(error) {
        return Promise.reject(error);
    }

    response(response) {
        // Check for 401 error
        if (response.status === 401) {
            FetchInterceptor.theInstance.session.signOut();
            FetchInterceptor.theInstance.router.push("/");
            return Promise.reject(response);
        }

        return response;
    }

    responseError(error) {
        return Promise.reject(error);
    }
}