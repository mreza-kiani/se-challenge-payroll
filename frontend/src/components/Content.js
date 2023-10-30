import './style.css';
import React, {useState} from 'react';
import {ReportBox} from "./ReportBox";
import validator from 'validator'
import {RequestService} from "../services/RequestService";
import {MessageService} from "../services/MessageService";
import {Button} from "react-bootstrap";
import {FilePond} from "react-filepond";
import {toast} from "react-toastify";

export class Content extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            records: [], loading: false,
            files: []
        };

        this.handleChange = this.handleChange.bind(this);
        this.fetchReport = this.fetchReport.bind(this);
    }

    handleChange(event) {
        this.setState({url: event.target.value});
    }

    fetchReport() {
        this.setState({loading: true})
        RequestService.get('/report',
            (data) => this.setState({records: data.payrollReport.employeeReports, loading: false}),
            (error) => this.setState({records: [], loading: false}))
    }

    render() {
        return (
            <div className="container">
                <FilePond
                    ref={ref => (this.pond = ref)}
                    files={this.state.files}
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
                                    this.setState({files: []});
                                }, 1000)
                            },
                        },
                    }}
                    name="file"
                    onupdatefiles={fileItems => {
                        this.setState({
                            files: fileItems.map(fileItem => fileItem.file)
                        });
                    }}
                />
                <div>
                    <Button variant="primary" onClick={this.fetchReport}
                            className="sample-buttons">
                        Fetch Report
                    </Button>
                </div>
                <ReportBox records={this.state.records} loading={this.state.loading}/>
            </div>
        );
    }
}