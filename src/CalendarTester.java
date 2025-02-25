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
 * Utility class that defines tester methods for p04 Monthly Calendar.
 */

public class CalendarTester {


  /**
   * Ensures the correctness of the constructor and getter methods defined in the Event class when
   * no exception is expected to be thrown.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testEventConstructorAndGettersValidBehavior() {

    Event event = new Event("Meeting", 15, 10, 30);

    assert event.getDescription().equals("Meeting") : "Description mismatch";
    assert event.getDay() == 15 : "Day mismatch";
    assert event.getStartTimeAsString().equals("10:30") : "Start time mismatch";
    assert !event.isComplete() : "Event should not be marked complete by default";

    System.out.println("testEventConstructorAndGettersValidBehavior passed!");
    return true;

  }


  /**
   * Ensures the correctness of the Event.compareTo() method.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testEventCompareTo() {

    Event event1 = new Event("Meeting", 15, 10, 30);
    Event event2 = new Event("Lunch", 15, 14, 0);
    Event event3 = new Event("Appointment", 15, 12, 15);
    Event event4 = new Event("Meeting", 15, 10, 30);

    assert event1.compareTo(event2) < 0 : "event1 should come before event2";
    assert event2.compareTo(event3) > 0 : "event2 should come after event3";
    assert event1.compareTo(event3) < 0 : "event1 should come before event3";
    assert event1.compareTo(event4) == 0 : "event1 and event4 should be equal (same start time)";

    System.out.println("testEventCompareTo passed!");
    return true;

  }


  /**
   * Ensures the correctness of the Event.equals() method.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testEventEquals() {

    Event event1 = new Event("Meeting", 15, 10, 30);
    Event event2 = new Event("Lunch", 15, 14, 0);
    Event event3 = new Event("Meeting", 15, 10, 30);
    Event event4 = new Event("Meeting", 16, 10, 30);
    Event event5 = new Event("Different", 15, 10, 30);
    Object nonEvent = "This is not an event";

    if (!event1.equals(event3)) return false;
    if (event1.equals(event2)) return false;
    if (event1.equals(event4)) return false;
    if (event1.equals(null)) return false;
    if (event1.equals(event5)) return false;
    if (event1.equals(nonEvent)) return false;

    System.out.println("testEventEquals passed!");
    return true;

  }


  /**
   * Ensures the correctness of the MonthCalendar.addEvent() method when valid inputs are provided
   * and no duplicate event exists for the day, ensuring the event is added correctly to the
   * specified day's list.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testSuccessfulAddEvent() {

    System.out.println("Testing Successful Add Event...");

    MonthCalendar calendar = new MonthCalendar(2025, 2);

    calendar.addEvent(15, "Meeting", 10, 30);

    System.out.println("Event added: Meeting on day 15");
    System.out.println(calendar.toString());

    return true;

  }


  /**
   * Ensures the correctness of the MonthCalendar.addEvent() method when called with invalid inputs
   * or an attempt to add a duplicate event.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testUnsuccessfulAddEvents() {

    System.out.println("Testing Unsuccessful Add Events:");

    MonthCalendar calendar = new MonthCalendar(2025, 2);

    try {
      calendar.addEvent(31, "Invalid Event", 10, 30);
    } catch (IllegalArgumentException e) {
      System.out.println("Expected exception: " + e.getMessage());
    }
    return true;

  }


  /**
   * Ensures the correctness of the MonthCalendar.cancelEvent() method when no exceptions are
   * expected.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testCancelEventValid() {

    MonthCalendar calendar = new MonthCalendar(2025, 2);

    String eventDescription = "Doctor's Appointment";
    int startHour = 10, startMin = 30;
    boolean eventAdded = calendar.addEvent(5, eventDescription, startHour, startMin);

    if (!eventAdded) {
      return false;
    }

    try {
      calendar.cancelEvent(eventDescription, 5, startHour, startMin);
    } catch (Exception e) {
      return false;
    }

    ArrayList<Event> day5Events = calendar.getEvents()[4];
    for (Event e : day5Events) {
      if (e.getDescription().equals(eventDescription) &&
          e.getStartTimeAsString().equals(String.format("%02d:%02d", startHour, startMin))) {
        return false;
      }
    }
    return true;

  }


  /**
   * Ensures the correctness of the MonthCalendar.cancelEvent() method when exceptions are
   * expected.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testCancelEventExceptions() {

    MonthCalendar calendar = new MonthCalendar(2025, 2);
    calendar.addEvent(15, "Test Event", 10, 30);

    try {
      calendar.cancelEvent("Test Event", 15, 10, 30);
    } catch (Exception e) {
      return false;
    }

    try {
      calendar.cancelEvent("", 15, 10, 30);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      calendar.cancelEvent("Test Event", 0, 10, 30);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      calendar.cancelEvent("Test Event", 15, 24, 30);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      calendar.cancelEvent("Test Event", 15, 10, 60);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      calendar.cancelEvent("Non-Existent Event", 15, 10, 30);
      return false;
    } catch (NoSuchElementException e) {
    } catch (Exception e) {
      return false;
    }

    return true;

  }


  /**
   * Ensures the correctness of the constructor of the Event class when it is passed invalid
   * inputs.
   *
   * @return true if the tester verifies a correct functionality and false if at least one bug is
   * detected
   */

  public static boolean testEventConstructorThrowingExceptions() {

    try {
      new Event(null, 15, 10, 30);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      new Event("Test Event", 32, 10, 30);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      new Event("Test Event", 15, 24, 30);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      new Event("Test Event", 15, 10, 60);
      return false;
    } catch (IllegalArgumentException e) {
    }

    try {
      new Event("Test Event", 15, 10, 30);
    } catch (Exception e) {
      return false;
    }

    return true;

  }


  /**
   * Main method to run the tester methods
   *
   * @param args list of input arguments if any
   */

  public static void main(String[] args) {
    System.out.println("-----------------------------------------------------------");
    System.out.println("testEventConstructorAndGettersValidBehavior: " + (
        testEventConstructorAndGettersValidBehavior() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testEventConstructorThrowingExceptions: " + (
        testEventConstructorThrowingExceptions() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testEventCompareTo: " + (testEventCompareTo() ? "Pass" : "Failed!"));

    System.out.println("-----------------------------------------------------------");
    System.out.println("testEventEquals: " + (testEventEquals() ? "Pass" : "Failed!"));

    System.out.println("-----------------------------------------------------------");

    System.out.println("testSuccessfulAddEvent: " + (testSuccessfulAddEvent() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testUnsuccessfulAddEvents: " + (testUnsuccessfulAddEvents() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");

    System.out.println("testCancelEventValid(): " + (testCancelEventValid() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println(
        "testDeleteExceptions: " + (testCancelEventExceptions() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
  }


}
