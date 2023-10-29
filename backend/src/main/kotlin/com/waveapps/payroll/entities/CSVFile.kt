package com.waveapps.payroll.entities

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*


@Entity
@Table(name = "csv_files")
class CSVFile {
    @Id
    var id: Int = 0

    @Column(name = "creation_date")
    var creationDate: Timestamp = Timestamp(Date().time)
}