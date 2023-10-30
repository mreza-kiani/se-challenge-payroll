import {MessageService} from "./MessageService";

export class RequestService {
    static BASE_URL = 'http://localhost:8080/api'

    static request(path, requestOptions, successCallback, errorCallback) {
        fetch(this.BASE_URL + path, requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.statusMessage);
                }
                return response.json();
            })
            .then(data => successCallback(data))
            .catch(error => {
                const message = error.message ? error.message : "An error happened with your request!";
                MessageService.error(message)
                errorCallback(error)
            });
    }

    static post(path, data, successCallback, errorCallback) {
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        };
        this.request(path, requestOptions, successCallback, errorCallback)
    }

    static get(path, successCallback, errorCallback) {
        const requestOptions = {
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        };
        this.request(path, requestOptions, successCallback, errorCallback)
    }

}