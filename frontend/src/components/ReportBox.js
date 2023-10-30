import './style.css';
import React from 'react';
import {Card, Spinner} from "react-bootstrap";


export class ReportBox extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="main-container">
                {this.props.loading && <Spinner animation="border"/>}
                {(!this.props.loading) && (this.props.records.map((record, i) => {
                        return <Card className="card" key={i}>
                            <Card.Body>
                                <Card.Title>Employee: {record.employeeId}</Card.Title>
                                <Card.Text>
                                    From: {record.payPeriod.startDate}
                                    To: {record.payPeriod.endDate}
                                    Amount: {record.amountPaid}
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    }))
                }
            </div>
        )
    }
}