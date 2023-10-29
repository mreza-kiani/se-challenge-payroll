package com.waveapps.payroll.entities

import jakarta.persistence.*

@Entity
@Table(name = "job_groups")
class JobGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "name", unique = true)
    var name: String = ""

    @Column(name = "hourly_rate")
    var hourlyRate: Double = 0.0
}