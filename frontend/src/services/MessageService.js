import {toast} from "react-toastify";

export class MessageService {
    static errorMap = {
        "CSV_ID_ALREADY_EXISTS": "A CSV with this id has already been uploaded!",
        "INVALID_CSV_FILE": "The selected CSV is invalid!",
    }

    static error(message) {
        if (this.errorMap[message] != null)
            toast.error(this.errorMap[message])
        else
            toast.error(message)
    }
}