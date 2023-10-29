package com.waveapps.payroll.repositories

import com.waveapps.payroll.entities.JobGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobGroupRepository : JpaRepository<JobGroup, Int>