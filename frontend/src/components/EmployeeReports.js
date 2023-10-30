import './EmployeeReports.css';
import React from 'react';


function EmployeeReports({items}) {
    return (
        <div className="employee-reports">
            <h2>Employee Reports</h2>
            <div className="reports-list">
                <ul className="reports-ul">
                    {items.map((item, index) => (
                        <li key={index}>
                            Employee ID: {item.employeeId}<br/>
                            Pay Period: {item.payPeriod.startDate} to {item.payPeriod.endDate}<br/>
                            Amount Paid: {item.amountPaid}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default EmployeeReports;
