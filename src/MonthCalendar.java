//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P04 Monthly Calendar
// Course:   CS 300 Spring 2025
//
// Author:   Jasmy Mavilla
// Email:    jmavilla@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         Murali Mavilla (dad)
//                  -
// Online Sources:  Chatgpt
//                  -
//                  P04 Write-Up
//                  - https://canvas.wisc.edu/courses/447785/pages/p04?module_item_id=8292819
//
///////////////////////////////////////////////////////////////////////////////


import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Represents a calendar for a specific month, allowing for the addition, cancellation,
 * completion, and display of events for each day of the month
 */

public class MonthCalendar {


  private final YearMonth MONTH;
  private int daysCount;
  private ArrayList<Event>[] events;
  private ArrayList<Event> completedEvents;


  /**
   * Constructs a MonthCalendar for the specified year and month
   *
   * @param year the year of the calendar
   * @param month the month of the calendar
   * @throws DateTimeException if the year or month is invalid
   */

  public MonthCalendar(int year, int month) {

    try {
      this.MONTH = YearMonth.of(year, month);
      this.daysCount = MONTH.lengthOfMonth();
      this.events = new ArrayList[daysCount];
      for (int i = 0; i < daysCount; i++) {
        events[i] = new ArrayList<>();
      }
      this.completedEvents = new ArrayList<>();
    } catch (DateTimeException e) {
      throw new DateTimeException("Invalid year or month.");
    }

  }


  /**
   * Adds an event to the specified day's list of events, maintaining order based on event's start
   * time
   * If an event with the same description, day, and start time already exists,
   * the event will not be added
   *
   * @param day the day of the month
   * @param description the description of the event
   * @param startHour the hour the event starts
   * @param startMin the minute the event starts
   * @return true if the event was successfully added, false if the event could not be added
   */

  public boolean addEvent(int day, String description, int startHour, int startMin) {

    if (day <= 0 || day > daysCount) {
      throw new IllegalArgumentException("Invalid day for this month.");
    }

    try {
      Event newEvent = new Event(description, day, startHour, startMin);

      for (Event event : events[day - 1]) {
        if (event.equals(newEvent)) {
          return false;
        }
      }

      int index = 0;
      while (index < events[day - 1].size() && events[day - 1].get(index).compareTo(newEvent) < 0) {
        index++;
      }

      events[day - 1].add(index, newEvent);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }

  }


  /**
   * Cancels an event on the specified day based on its description, start hour, and start minute
   *
   * @param description The description of the event to cancel.
   * @param day The day of the event
   * @param startHour The start hour of the event
   * @param startMin The start minute of the event
   * @throws IllegalArgumentException if the input values are invalid
   * @throws NoSuchElementException if no matching event is found
   */

  public void cancelEvent(String description, int day, int startHour, int startMin) {

    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException();
    }

    if (day <= 0 || day > daysCount) {
      throw new IllegalArgumentException();
    }

    if (startHour < 0 || startHour > 23) {
      throw new IllegalArgumentException();
    }

    if (startMin < 0 || startMin > 59) {
      throw new IllegalArgumentException();
    }

    int dayIndex = day - 1;
    ArrayList<Event> dayEvents = events[dayIndex];
    boolean foundEvent = false;

    for (Event event : dayEvents) {
      if (event.getDescription().equals(description) &&
          event.getStartTimeAsString().equals(String.format("%02d:%02d", startHour, startMin))) {
        dayEvents.remove(event);
        foundEvent = true;
        break;
      }
    }

    if (!foundEvent) {
      throw new NoSuchElementException();
    }

  }


  /**
   * Marks the event matching the specific input argument event e as complete if a match is found
   * in the list of events. Removes the matching event from the list of the events scheduled on the
   * event e's day, and adds it to index 0 of the list of completed events
   *
   * @param e a specific event scheduled at a valid day of the month
   * @throws IllegalArgumentException if e is null or scheduled for a non-valid day of the month
   * @throws NoSuchElementException if no matching event with e is found in the list of events
   */

  public void markEventAsComplete(Event e) {

    if (e == null) {
      throw new IllegalArgumentException("Event cannot be null.");
    }

    if (e.getDay() <= 0 || e.getDay() > daysCount) {
      throw new IllegalArgumentException("Invalid day for the event.");
    }

    int dayIndex = e.getDay() - 1;
    ArrayList<Event> dayEvents = events[dayIndex];

    boolean foundEvent = false;
    for (Event scheduledEvent : dayEvents) {
      if (scheduledEvent.equals(e)) {
        dayEvents.remove(scheduledEvent);
        completedEvents.add(0, scheduledEvent);
        scheduledEvent.markAsComplete();
        foundEvent = true;
        break;
      }
    }

    if (!foundEvent) {
      throw new NoSuchElementException("No matching event found.");
    }

  }


  /**
   * Returns the textual representation of the month in full style format, such as 'February'.
   *
   * @return the text value of the month in full text style length
   */

  public String getMonthName() {

    return MONTH.getMonth().toString();

  }


  /**
   * Gets the month-of-year field as an int from 1 to 12 of this MonthCalendar.
   *
   * @return the month as an int from 1 to 12.
   */

  public int getMonthNumber() {

    return MONTH.getMonthValue();

  }


  /**
   * Gets the number of days in the current month calendar.
   *
   * @return the number of days in the current calendar.
   */

  public int getDaysCount() {

    return daysCount;

  }


  /**
   * Provided method -- Returns a String representation of all completed events
   *
   * @return a String containing all completed events with their completed days
   * on separate lines,and an empty string if the list of completed events is empty.
   */

  public String getCompletedEventsAsString() {
    String retval = "";
    for (Event e : completedEvents) {
      retval += e.toString() + "\n";
    }
    return retval.strip();
  }


  /**
   * Clears all completed events stored in the completedEvents list. After calling this method,
   * the completedEvents list will be empty
   */

  public void clearAllCompletedEvents() {

    completedEvents.clear();

  }


  /**
   * Returns a deep copy of the completed events list for this MonthCalendar
   *
   * @return a deep copy of the list of completed events scheduled for this MonthCalendar
   */

  public ArrayList<Event> getCompletedEvents() {

    ArrayList<Event> deepCopy = new ArrayList<>();
    for (Event e : completedEvents) {
      deepCopy.add(e.copy());
    }
    return deepCopy;

  }


  /**
   * Returns a deep copy of the array of events for this MonthCalendar
   *
   * @return a deep copy of the array of uncompleted events scheduled for this MonthCalendar
   */

  public ArrayList<Event>[] getEvents() {

    ArrayList<Event>[] deepCopyArray = new ArrayList[events.length];

    for (int i = 0; i < events.length; i++) {
      deepCopyArray[i] = new ArrayList<>();
      for (Event e : events[i]) {
        deepCopyArray[i].add(e.copy());
      }
    }

    return deepCopyArray;

  }


  /**
   * Provided method -- Returns a String representation of all uncompleted events.
   *
   * Events scheduled on the same day must be in the increasing order.
   *
   * @return a String representation of All the events stored in the events list,
   * and an empty string if the list of events is empty.
   */

  @Override
  public String toString() {

    String retval = "";
    for (int i = 0; i < events.length; i++) {
      if (!events[i].isEmpty()) {
        retval += "Events for Day " + (i + 1) + ":\n";
        for (Event e : events[i]) {
          retval += e.toString() + "\n";
        }
      } }
    return retval.strip();

  }


}
