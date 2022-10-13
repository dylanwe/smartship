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
        console.log('FetchInterceptor has been registered; current token = ',
            FetchInterceptor.theInstance.session.getTokenFromBrowserStorage());
    }

    request(url, options) {
        let token = FetchInterceptor.theInstance.session.getTokenFromBrowserStorage();
        console.log(url);
        console.log(token);

        if (token == null) {
            return [url, options];
        } else if (options == null) {
            return [url, {headers: {Authorization: token}}];
        } else {
            let newOptions = {...options};
            newOptions.headers= {
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
        return response;
    }

    responseError(error) {
        return Promise.reject(error);
    }
}