import React, {useState} from 'react';
import {MessageService} from "../services/MessageService";
import {FilePond, registerPlugin} from "react-filepond";
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';


function FileUploader() {
    const [files, setFiles] = useState(null);

    registerPlugin(FilePondPluginFileValidateType);

    return (
        <FilePond
            files={files}
            acceptedFileTypes={['text/csv']}
            allowMultiple={false}
            allowReorder={true}
            maxFiles={1}
            server={{
                url: "http://localhost:8080/api",
                process: {
                    url: '/upload',
                    method: 'POST',
                    onerror: (response) => {
                        MessageService.error(JSON.parse(response).message)
                        setTimeout(() => {
                            setFiles([]);
                        }, 1500)
                    },
                    onload: (response) => {
                        setTimeout(() => {
                            setFiles([]);
                        }, 2000)
                    },
                },
            }}
            name="file"
            onupdatefiles={fileItems => {
                setFiles(fileItems.map(fileItem => fileItem.file))
            }}
        />
    );
}


export default FileUploader;