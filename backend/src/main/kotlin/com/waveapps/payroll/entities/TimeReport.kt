package com.waveapps.payroll.entities

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "time_reports")
class TimeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "date")
    var date: Timestamp = Timestamp(Date().time)

    @Column(name = "employee_id")
    var employeeId: Int = 0

    @Column(name = "hours_worked")
    var hoursWorked: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "job_group_id")
    var jobGroup: JobGroup = JobGroup()

    @ManyToOne
    @JoinColumn(name = "csv_id")
    var csvFile: CSVFile = CSVFile()
}