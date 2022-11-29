package com.example.managerbookfreelancer.model

import java.util.UUID

data class JobModel (
    val id : String = UUID.randomUUID().toString(),
    val engaged : EngagedModel,
    val ownerJob : OwnerJob,
    val weedingDay : WeedingDayModel

)

