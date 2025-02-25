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
 * Represents an event with a description, day, start time, and completion status
 */

public class Event implements Comparable<Event> {


  private String description;
  private int day;
  private int startTime;
  private boolean isComplete;
  private int startHour;
  private int startMin;


  /**
   * Constructs an Event with a description, day, hour, and minute
   *
   * @param description the description of the event
   * @param day the day of the event
   * @param startHour the hour of the event
   * @param startMin the minute of the event
   * @throws IllegalArgumentException if the description is null or blank,
   * if the day is not between 1 and 31, or if the time is invalid
   */

  public Event(String description, int day, int startHour, int startMin) {

    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or blank.");
    }
    if (day < 1 || day > 31) {
      throw new IllegalArgumentException("Day must be between 1 and 31.");
    }
    if (startHour < 0 || startHour > 23 || startMin < 0 || startMin > 59) {
      throw new IllegalArgumentException("Invalid time.");
    }

    this.description = description;
    this.day = day;
    this.startTime = startHour * 100 + startMin;
    this.isComplete = false;
    this.startHour = startHour;
    this.startMin = startMin;

  }


  /**
   * Gets the description of the event
   *
   * @return the description of the event
   */

  public String getDescription() {

    return description;

  }


  /**
   * Gets the day of the event
   *
   * @return the day of the event
   */

  public int getDay() {

    return day;

  }


  /**
   * Gets the start time of the event as a string
   *
   * @return the start time as a formatted string
   */

  public String getStartTimeAsString() {

    int hour = startTime / 100;
    int minute = startTime % 100;

    return String.format("%02d:%02d", hour, minute);

  }


  /**
   * Marks the event as complete
   */

  public void markAsComplete() {

    this.isComplete = true;

  }


  /**
   * Checks if the event is complete
   *
   * @return true if the event is complete, false if not
   */

  public boolean isComplete() {

    return isComplete;

  }


  /**
   * Creates a deep copy of this event.
   *
   * @return a new event with the same description, day, start time,
   * and completion status as this event
   */

  public Event copy() {

    Event copiedEvent = new Event(new String(this.description), this.day,
        this.startHour, this.startMin);
    copiedEvent.isComplete = this.isComplete;
    copiedEvent.startTime = this.startTime;
    return copiedEvent;

  }


  /**
   * Provided method - Returns a String representation of this Event
   *
   * @return a String representation of this event
   */

  @Override
  public String toString() {

    return this.description + " at " + this.startHour + ":" + this.startMin
        + (isComplete ? " completed on Day " + day : "");

  }


  /**
   * Compares this event to another event based on start times
   *
   * @param otherEvent the other event to compare to
   * @return a negative integer, zero, or a positive integer as this event's start time
   * is earlier than, equal to, or later than the specified event's start time
   */

  @Override
  public int compareTo(Event otherEvent) {

    return Integer.compare(this.startTime, otherEvent.startTime);

  }


  /**
   * Checks if this event is equal to another object
   *
   * @param o the object to compare this event to
   * @return true if the events are equal, false if not
   */

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Event)) return false;
    Event event = (Event) o;
    return this.day == event.day &&
        this.startHour == event.startHour &&
        this.startMin == event.startMin &&
        this.description.equals(event.description);
  }


}
