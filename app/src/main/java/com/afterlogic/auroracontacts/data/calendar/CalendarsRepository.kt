package com.afterlogic.auroracontacts.data.calendar

import com.afterlogic.auroracontacts.application.AppScope
import com.afterlogic.auroracontacts.data.db.CalendarDbe
import com.afterlogic.auroracontacts.data.db.CalendarEventDbe
import com.afterlogic.auroracontacts.data.db.CalendarsDao
import com.afterlogic.auroracontacts.data.db.SyncSettings
import com.afterlogic.auroracontacts.data.preferences.Prefs
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by sunny on 08.12.2017.
 * mail: mail@sunnydaydev.me
 */

@AppScope
class CalendarsRepository @Inject constructor(
        private val prefs: Prefs,
        private val dao: CalendarsDao,
        private val calendarMapper: CalendarMapper,
        private val remoteServiceProvider: CalendarRemoteServiceProvider
) {

    private val remoteService: Single<CalendarRemoteService> get() = remoteServiceProvider.get()

    //region// Calendars

    fun getCalendarsInfo(): Flowable<List<AuroraCalendarInfo>> {

        return getLocalCalendarsInfo()
                .flatMap {
                    if (prefs.calendarsFetched) Flowable.just(it)
                    else Flowable.empty()
                }
                .mergeWith(getRemoteCalendars().toCompletable().toFlowable())

    }

    fun setSyncEnabled(calendar: AuroraCalendarInfo, enabled: Boolean): Completable =
            Completable.fromAction { dao.setSyncEnabled(calendar.id, enabled) }

    //endregion

    private fun getRemoteCalendars(): Single<List<RemoteCalendar>> {

        return remoteService.flatMap { it.getCalendars() }
                .doOnSuccess {
                    it.map { calendarMapper.toDbe(it) } .let { dao += it }
                    prefs.calendarsFetched = true
                }

    }

    private fun getLocalCalendarsInfo(): Flowable<List<AuroraCalendarInfo>> {
        return dao.all
                .map { it.map(calendarMapper::toPlain) }
    }

}

interface CalendarRemoteService {
    fun getCalendars(): Single<List<RemoteCalendar>>
    fun getEvents(calendarId: String): Single<List<RemoteCalendarEvent>>
    fun updateEvent(request: UpdateCalendarEventRequest): Completable
    fun deleteEvent(request: DeleteCalendarEventsRequest): Completable
}

class CalendarMapper @Inject constructor() {

    fun toDbe(source: RemoteCalendar, syncEnabled: Boolean = false): CalendarDbe {

        val settings = SyncSettings(syncEnabled)

        return CalendarDbe(
                source.id,
                source.name,
                source.description,
                source.color,
                settings
        )

    }

    fun toPlain(souce: CalendarDbe): AuroraCalendarInfo {

        val settings = AuroraCalendarSettings(
                souce.settings.syncEnabled
        )

        return AuroraCalendarInfo(
                souce.id,
                souce.name,
                souce.description,
                souce.color,
                settings
        )
    }

}

class EventsMapper @Inject constructor() {

    fun toDbe(remote: RemoteCalendarEvent): CalendarEventDbe {
        TODO()
    }

    fun toPlain(dbe: CalendarEventDbe): AuroraCalendarEvent {
        TODO()
    }

    fun toRemote(plain: AuroraCalendarEvent): RemoteCalendarEvent {
        TODO()
    }

}