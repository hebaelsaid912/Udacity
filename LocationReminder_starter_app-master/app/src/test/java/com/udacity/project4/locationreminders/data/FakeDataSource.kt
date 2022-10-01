package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

class FakeDataSource(private val reminders: MutableList<ReminderDTO>? = mutableListOf()) :
    ReminderDataSource {

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders?.add(reminder)
    }

    override suspend fun deleteAllReminders() {
        reminders?.clear()
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        reminders?.let { return Result.Success(it) }
        return Result.Error("No reminders found")
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        reminders?.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        return Result.Error("No element with id $id found")
    }
}