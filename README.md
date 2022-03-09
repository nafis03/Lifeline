# CPSC 210 Term Project: Task Manager

## Nafis Ahsan (d9u2a)

**Project Description:**
My project will be a task-manager application, oriented towards students and their planning needs for school.
It would be able to log upcoming assignments/deadlines (like a to-do list) and also log upcoming events
(like a calendar). It would then also be able to retrieve all the upcoming events and/or assignments in a given day,
week, month or just the total list of upcoming events and/or assignments. It would also be able to retrieve all the 
completed assignments and passed events. Like a to-do list, it would also be able to check off items if the user wishes,
and mark overdue assignments. It would also be able to categorize/tag assingments and events, so that it may retrieve a 
list of a certain type of assignment(s) or event(s). Finally, a user would also be able to re-edit/change certain data
of an event/assignment if they need to do so.

An assignment would have:
 - Name
 - Description
 - Deadline/Due Date
 - Status (completed/not completed/overdue)
 - Type (what school class it is for)

An event would have: 
 - Name
 - Description
 - Date
 - Time
 - Type

**Why I am interested in this project:** 
Personally I wanted 2 things out of this project: 1) to practice my fundamentals in software design and 2) create
something that would be useful that might impress a future employer. I think that this idea will accomplish both. I 
wanted to pick something manageable, since choosing something overly complicated would overwhelm me, and since this is
my first solo coding project. However, at the same time, I think that overtime I will be able to add enough unique 
functionality that it would give me something impressive to show future employers; I think it would be better to do a 
simpler thing well rather than a complicated thing badly.

## User Stories (Phase 1):
As a user I want to be able to:
 - Log/add an assignment to my task manager
 - Log/add an upcoming event to my task manager
 - View the list of all assignments due for a given day, week, month
 - View the list of all events that take place on a given day, week, or month
 - Mark an assignment and/or event as complete, thereby removing from the list(s)
 - Find and then edit/change some information about an assignment or event or delete it entirely

## User Stories (Phase 2):
As a user I want to be able to:
 - Save my task manager, so it saves all the work I've done (saves all the logged tasks I've put in)
 - Load my previous task manager, so it pulls up all the logged tasks I put in last time

## Phase 3 Tasks:
As defined by project outline:
 - Your GUI must include a panel in which all the Xs that have already been added to Y are displayed. 

For this I created a panel that displays all the assignments (X) that have already been added to the assignment list (Y)

 - You are also required to implement two related events; the events that you implement must be somehow related to the
   Xs and Y. 
   
For this I created two events/buttons: the add assignment button and the remove assignment button. They both do as their
name suggests, and they are related to the Xs and Ys since they add or remove assignments (X) from the assignment list (Y)

 - "As a user, I want to be able to load and save the state of the application", you could have buttons or menu items
   that the user can click when they want to load/save data to/from file
   
 For this I created a save button which saves all the data that was made about the assignment list, and also a load 
button which both retrieves the previously stored save data and also displays that saved data on the display

 - Your GUI must also include an audio-visual component

For this I made it so that by clicking the save button, a noise is made to indicate that the task manager has been 
successfully saved.
 
## Phase 4 Task 2:
My choice was:
 - Test and design a class in your model package that is robust.  You must have at least one method that throws a 
   checked exception. You must have one test for the case where the exception is expected and another where the 
   exception is not expected.
   
To fulfill this task, I implemented the Date constructor (in the Date class) to throw two new exceptions
(TooEarlyException and InvalidDateException). This way, whenever any new Date object was being constructed, it would
need values that made sense,(i.e. no invalid values like the month being 13 or the day being 0). Otherwise it would 
throw these one of these two types of exceptions to be handled. I then tested these exception throws in the DateTest 
test class.

## Phase 4 Task 3:

Note: UML class diagram is in root of project, right below README.md file

Looking back on this class diagram, I do wish I refactored my program structure a bit. The main thing I would change 
would be adding an abstract class that Assignment and Event could extend, something like "Task". The reason for this is 
that both have very similar structures and behave very similarly, with just a few small differences in fields.
Both are then later used very similarly in their respective List Classes (AssignmentList and EventList). By adding a 
Task Class, I could reduce the amount of repetitive code, and maybe even simplify AssignmentList and EventList as just 
one Class called TaskList. Overall though, I am pretty happy with the way my program came out.