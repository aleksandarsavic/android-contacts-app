package com.afterlogic.auroracontacts.data.calendar

/**
 * Created by sunny on 08.12.2017.
 * mail: mail@sunnydaydev.me
 */

data class RemoteCalendar(
        val id: String,
        val name: String,
        val description: String?,
        val color: Int,
        val eTag: String?,
        val cTag: String,
        val owner: String,
        val accessLevel: AccessLevel
) {

    enum class AccessLevel { READ, EDITOR }

}

data class AuroraCalendarInfo(val id: String,
                              val name: String,
                              val description: String?,
                              val color: Int,
                              val settings: AuroraCalendarSettings)

data class AuroraCalendarSettings(val syncEnabled: Boolean)


data class RemoteCalendarEvent(val id: String, val lastModified: Long, val eTag: String, val data: String)

data class UpdateCalendarEventRequest(val id: String?, val calendarId: String, val icsData: String)

data class DeleteCalendarEventsRequest(val calendarId: String, val ids: List<String>)

data class AuroraCalendarEvent(val id: String)