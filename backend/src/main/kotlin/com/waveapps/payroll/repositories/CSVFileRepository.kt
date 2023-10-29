package com.waveapps.payroll.repositories

import com.waveapps.payroll.entities.CSVFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CSVFileRepository: JpaRepository<CSVFile, Int>