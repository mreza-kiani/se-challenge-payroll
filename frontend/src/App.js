import './App.css';
import 'react-toastify/dist/ReactToastify.css';
import 'filepond/dist/filepond.min.css';

import {ToastContainer} from "react-toastify";
import React, {useState} from "react";
import {RequestService} from "./services/RequestService";
import EmployeeReports from "./components/EmployeeReports";
import FileUploader from "./components/FileUploader";


function App() {
    const [employeeReports, setEmployeeReports] = useState(null);

    const fetchReport = (event) => {
        RequestService.get('/report',
            (data) => setEmployeeReports(data.payrollReport.employeeReports),
            (error) => setEmployeeReports([]))
    };

    return (
        <div className="container">
            <h1>Payroll System</h1>
            <FileUploader/>
            <button onClick={fetchReport}>Fetch Report</button>
            {employeeReports && <EmployeeReports items={employeeReports}/>}
            <ToastContainer/>
        </div>
    );
}

export default App;
