package com.waveapps.payroll.repositories

import com.waveapps.payroll.entities.TimeReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimeReportRepository : JpaRepository<TimeReport, Int>