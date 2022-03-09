package ui;

import model.Assignment;
import model.AssignmentList;
import model.Event;
import model.EventList;
import model.Date;
import model.exceptions.InvalidDateException;
import model.exceptions.TooEarlyException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import java.io.*;
import javax.sound.sampled.*;

// Represents a task manger application
// test change

// NOTE: many of the ui methods and ideas (ex. runTaskManager, taskManagerApp, how to keep the task manager
// running, how to allow user to select options) were inspired by Teller App
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

// NOTE: THIS CLASS's DATA PERSISTENCE HAS BEEN HEAVILY INSPIRED BY JsonSerializationDemo from
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// NOTE: THE playSound() function was heavily inspired by stack overflow post at
// https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java
public class TaskManager extends JFrame implements ActionListener {

    private AssignmentList assignmentList;
    private EventList eventList;
    private Scanner input;

    private static final String JSONAL_STORE = "./data/assignmentList.json";
    private static final String JSONEL_STORE = "./data/eventList.json";
    private JsonWriter jsonALWriter;
    private JsonReader jsonALReader;
    private JsonWriter jsonELWriter;
    private JsonReader jsonELReader;

    private JPanel panel;
    private JPanel firstPanel;

    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel dayLabel;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel classLabel;

    private JTextField userName;
    private JTextField userDesc;
    private JTextField userDay;
    private JTextField userMonth;
    private JTextField userYear;
    private JTextField userClass;

    private JList list;
    private DefaultListModel listModel;
    private JButton addButton;
    private JButton removeButton;

    private JButton saveButton;
    private JButton loadButton;
    private JLabel saveLabel;
    private JLabel loadLabel;

    // EFFECTS: runs the task manager application
    public TaskManager() throws FileNotFoundException {
        super("Task Manager"); // makes it so Task Manager is a gui and has a frame
        initializeGraphics();
        initializeLists();
        runTaskManager();
    }

    private void initializeGraphics() {
        panel = new JPanel(); // creates a new panel to add on top of frame
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // setting panel border
        panel.setLayout(new GridLayout(1, 4)); // setting panel layout
        firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(1, 2));

        initializeCol1Labels();
        initializeCol2TextFields();
        panel.add(firstPanel);

        initializeCol3Display();
        initializeCol4SaveLoad(); // you have to pack your gui after adding
        add(panel, BorderLayout.CENTER); // adding panel to frame

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set what happens when they close the frame
        setTitle("My Task Manager"); // set title of window
        pack(); // set window to match a certain size
        setVisible(true); // set window to be visible and in focus
        setLocationRelativeTo(null);
    }

    private void initializeCol1Labels() {
        nameLabel = new JLabel("Name:");
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        dayLabel = new JLabel("Day:");
        dayLabel.setHorizontalAlignment(JLabel.CENTER);
        monthLabel = new JLabel("Month:");
        monthLabel.setHorizontalAlignment(JLabel.CENTER);
        yearLabel = new JLabel("Year:");
        yearLabel.setHorizontalAlignment(JLabel.CENTER);
        classLabel = new JLabel("Class:");
        classLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(6, 1));

        labelPanel.add(nameLabel);
        labelPanel.add(descriptionLabel);
        labelPanel.add(dayLabel);
        labelPanel.add(monthLabel);
        labelPanel.add(yearLabel);
        labelPanel.add(classLabel);
        firstPanel.add(labelPanel);
    }

    private void initializeCol2TextFields() {
        userName = new JTextField(1);
        userDesc = new JTextField(1);
        userDay = new JTextField(1);
        userMonth = new JTextField(1);
        userYear = new JTextField(1);
        userClass = new JTextField(1);

        JPanel assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new GridLayout(6, 1));

        assignmentPanel.add(userName);
        assignmentPanel.add(userDesc);
        assignmentPanel.add(userDay);
        assignmentPanel.add(userMonth);
        assignmentPanel.add(userYear);
        assignmentPanel.add(userClass);
        firstPanel.add(assignmentPanel);
    }

    private void initializeCol3Display() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));

        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(new GridLayout(2, 1));

        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(5);

        JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);

        addButton = new JButton("Add Assignment");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);

        removeButton = new JButton("Remove Assigment");
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        finalPanel.add(listScroller);
        finalPanel.add(buttonsPanel);
        panel.add(finalPanel);
    }

    private void initializeCol4SaveLoad() {
        saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);

        loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);

        saveLabel = new JLabel("Not Saved");
        saveLabel.setHorizontalAlignment(JLabel.CENTER);
        loadLabel = new JLabel("Not Loaded");
        loadLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new GridLayout(4, 1));

        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(loadButton);
        saveLoadPanel.add(saveLabel);
        saveLoadPanel.add(loadLabel);
        panel.add(saveLoadPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes new event list and assignment list
    private void initializeLists() throws FileNotFoundException {
        assignmentList = new AssignmentList();
        eventList = new EventList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonALWriter = new JsonWriter(JSONAL_STORE);
        jsonALReader = new JsonReader(JSONAL_STORE);
        jsonELWriter = new JsonWriter(JSONEL_STORE);
        jsonELReader = new JsonReader(JSONEL_STORE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            saveAssignmentList();
            saveEventList();
            playSaveSound("./data/UndertaleSoundEffectSaving.wav");
            saveLabel.setText("Saved!");
        } else if (e.getActionCommand().equals("load")) {
            loadAssignmentList();
            loadEventList();
            for (Assignment a : assignmentList.getAssignments()) {
                listModel.addElement(a.printAssignment());
            }
            loadLabel.setText("Loaded!");
        } else if (e.getActionCommand().equals("add")) {
            try {
                doAddAssignment();
            } catch (Exception exception) {
                //
            }
        } else if (e.getActionCommand().equals("remove")) {
            Assignment toBeRemoved = assignmentList.getAssignmentAtPosition(list.getSelectedIndex());
            toBeRemoved.setStatus("complete");
            assignmentList.updateAssignmentList();
            listModel.remove(list.getSelectedIndex());
        }
    }

    // MODIFIES: this
    // EFFECT: processes user input
    private void runTaskManager() throws FileNotFoundException {
        boolean running = true;
        String command = null;

        while (running) {
            startMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("c")) {
                running = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECT: processes user input
    private void processCommand(String command) {
        if (command.equals("a")) {
            addTask();
        } else if (command.equals("v")) {
            viewLists();
        } else if (command.equals("e")) {
            editTask();
//        } else if (command.equals("sa")) {
//            saveAssignmentList();
//        } else if (command.equals("la")) {
//            loadAssignmentList();
//        } else if (command.equals("se")) {
//            saveEventList();
//        } else if (command.equals("le")) {
//            loadEventList();
        } else {
            System.out.println("Your selection was invalid. Please try again");
        }
    }

    // EFFECT: returns the initial display menu of the task manager app
    private void startMenu() {
        System.out.println("\nWelcome to Task Manager! Please select one of the following options: ");
        System.out.println("\ta = Add new event/assignment");
        System.out.println("\tv = View my upcoming events or assignments");
        System.out.println("\te = Edit an upcoming event or assignment");
//        System.out.println("\tsa -> Save my assignments");
//        System.out.println("\tla -> Load my assignments");
//        System.out.println("\tse -> Save my events");
//        System.out.println("\tle -> Load my events");
        System.out.println("\tc = Close task manager");
    }

    // MODIFIES: this
    // EFFECTS: adds a new event or assignment to task manager
    private void addTask() {
        String selection = "";

        while (!(selection.equals("a") || selection.equals("e"))) {
            System.out.println("What would you like to add? \n a = assignment \n e = event");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("a")) {
            try {
                doAddAssignment();
            } catch (InvalidDateException e) {
                e.printStackTrace();
            } catch (TooEarlyException e) {
                e.printStackTrace();
            }
        } else {
            doAddEvent();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays either current list of assignments left to do, or current list of upcoming events
    private void viewLists() {
        String selection = "";

        while (!(selection.equals("a") || selection.equals("e"))) {
            System.out.println("What tasks would you like to view? \n a = assignments \n e = events");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("a")) {
            doViewAssignments();
        } else {
            doViewEvents();
        }
    }

    // MODIFIES: this
    // EFFECTS: edits an assignment or event as the user wishes
    private void editTask() {
        String selection = "";

        while (!(selection.equals("a") || selection.equals("e"))) {
            System.out.println("What lists would you like to edit? \n a = assignments \n e = events");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("a")) {
            doEditAssignments();
        } else {
            doEditEvents();
        }
    }

    // MODIFIES: this
    // EFFECT: adds given assignment
    private void doAddAssignment() throws InvalidDateException, TooEarlyException {
//        System.out.println("Enter the name:");
//        String name = input.next();
//        System.out.println("Enter a description:");
//        String description = input.next();
//        System.out.println("Enter the day of the deadline:");
//        int day = Integer.parseInt(input.next());
//        System.out.println("Enter the month of the deadline (as a number):");
//        int month = Integer.parseInt(input.next());
//        System.out.println("Enter the year of the deadline:");
//        int year = Integer.parseInt(input.next());
//        System.out.println("Enter a the class this assignment is for:");
//        String type = input.next();
//
//        if (day <= 0 || day > 31 || month <= 0 || month > 12 || year < 2021) {
//            System.out.println("Invalid entry.");
//        } else {
//            Date newDate = new Date(day, month, year);
//            Assignment newAssignment = new Assignment(name, description, newDate, type);
//            assignmentList.addAssignment(newAssignment);
//            System.out.println("Assignment added!");
//        }

        String assignmentName = userName.getText();
        String assignmentDesc = userDesc.getText();
        int day = Integer.parseInt(userDay.getText());
        int month = Integer.parseInt(userMonth.getText());
        int year = Integer.parseInt(userYear.getText());
        Date assignmentDate = new Date(day, month, year);
        String assignmentCourse = userClass.getText();

        Assignment newAssignment = new Assignment(assignmentName, assignmentDesc, assignmentDate, assignmentCourse);
        assignmentList.addAssignment(newAssignment);

        int pos = assignmentList.getPositionOfAssignment(newAssignment);
        listModel.add(pos, newAssignment.printAssignment());
    }

    // MODIFIES: this
    // EFFECT: adds given event
    private void doAddEvent() {
        System.out.println("Enter the name:");
        String name = input.next();
        System.out.println("Enter a description:");
        String description = input.next();
        System.out.println("Enter a day:");
        int day = Integer.parseInt(input.next());
        System.out.println("Enter a month (as a number):");
        int month = Integer.parseInt(input.next());
        System.out.println("Enter a year:");
        int year = Integer.parseInt(input.next());
        System.out.println("Enter the time this event will take place as military time:");
        int time = Integer.parseInt(input.next());

        try {
            Date newDate = new Date(day, month, year);
            Event newEvent = new Event(name, description, newDate, time);
            eventList.addEvent(newEvent);
            System.out.println("Event added!");
        } catch (Exception e) {
            System.out.println("Invalid date");
        }
    }

    // EFFECT: displays list of assignments for given day/week/month
    private void doViewAssignments() {
        String selection = "";

        while (!(selection.equals("d") || selection.equals("w") || selection.equals("m"))) {
            System.out.println("View all assignments in a day, week or month? \n d = day \n w = week \n m = month");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("d")) {
            printAssignmentsOnDay();
        } else if (selection.equals("w")) {
            printAssignmentsOnWeek();
        } else {
            printAssignmentsOnMonth();
        }
    }

    // EFFECT: displays list of events for given day/week/month
    private void doViewEvents() {
        String selection = "";

        while (!(selection.equals("d") || selection.equals("w") || selection.equals("m"))) {
            System.out.println("View all events in a day, week or month? \n d = day \n w = week \n m = month");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("d")) {
            printEventsOnDay();
        } else if (selection.equals("w")) {
            printEventsOnWeek();
        } else {
            printEventsOnMonth();
        }
    }

    // MODIFIES: this
    // EFFECT: changes the fields of an assignment in assignmentList
    private void doEditAssignments() {
        Assignment wanted = null;

        System.out.println("What is the name of the assignment you would like to edit?");
        String selection = input.next();

        for (int index = 0; index < assignmentList.getSize(); index++) {
            if (selection.equals(assignmentList.getAssignmentAtPosition(index).getName())) {
                wanted = assignmentList.getAssignmentAtPosition(index);
                index = assignmentList.getSize();
            }
        }

        if (wanted == null) {
            System.out.println("No assignments have been logged with that name.");
        } else {
            displayEditOptions();
            selection = input.next().toLowerCase();
            if (selection.equals("name")) {
                changeAssignmentName(wanted);
            } else if (selection.equals("description")) {
                changeAssignmentDescription(wanted);
            } else {
                changeAssignmentStatus(wanted);
            }
        }
        assignmentList.updateAssignmentList();
    }

    // MODIFIES: this
    // EFFECT: changes the fields of an assignment in assignmentList
    private void doEditEvents() {
        Event wanted = null;

        System.out.println("What is the name of the event you would like to edit?");
        String selection = input.next();

        for (int index = 0; index < eventList.getSize(); index++) {
            if (selection.equals(eventList.getEventAtPosition(index).getName())) {
                wanted = eventList.getEventAtPosition(index);
                index = eventList.getSize();
            }
        }

        if (wanted == null) {
            System.out.println("No events have been logged with that name.");
        } else {
            displayEditOptions();
            selection = input.next().toLowerCase();
            if (selection.equals("name")) {
                changeEventName(wanted);
            } else if (selection.equals("description")) {
                changeEventDescription(wanted);
            } else {
                changeEventStatus(wanted);
            }
        }
        eventList.updateEventList();
    }


    // EFFECT: prints all assignments on given day
    private void printAssignmentsOnDay() {
        System.out.println("Enter the day:");
        int day = Integer.parseInt(input.next());
        System.out.println("Enter the month:");
        int month = Integer.parseInt(input.next());
        System.out.println("Enter the year:");
        int year = Integer.parseInt(input.next());
        System.out.println(assignmentList.getAssignmentListForDay(day, month, year).viewAssignmentList());
    }

    // EFFECT: prints all assignments on in week of given day
    private void printAssignmentsOnWeek() {
        System.out.println("Enter the day:");
        int day = Integer.parseInt(input.next());
        System.out.println("Enter the month:");
        int month = Integer.parseInt(input.next());
        System.out.println("Enter the year:");
        int year = Integer.parseInt(input.next());
        System.out.println(assignmentList.getAssignmentListForWeek(day, month, year).viewAssignmentList());
    }

    // EFFECT: prints all assignments on given month
    private void printAssignmentsOnMonth() {
        System.out.println("Enter the month:");
        int month = Integer.parseInt(input.next());
        System.out.println("Enter the year:");
        int year = Integer.parseInt(input.next());
        System.out.println(assignmentList.getAssignmentListForMonth(month, year).viewAssignmentList());
    }

    // EFFECT: prints all events on given day
    private void printEventsOnDay() {
        System.out.println("Enter the day:");
        int day = Integer.parseInt(input.next());
        System.out.println("Enter the month:");
        int month = Integer.parseInt(input.next());
        System.out.println("Enter the year:");
        int year = Integer.parseInt(input.next());
        System.out.println(eventList.getEventListForDay(day, month, year).viewEventList());
    }

    // EFFECT: prints all events on in week of given day
    private void printEventsOnWeek() {
        System.out.println("Enter the day:");
        int day = Integer.parseInt(input.next());
        System.out.println("Enter the month:");
        int month = Integer.parseInt(input.next());
        System.out.println("Enter the year:");
        int year = Integer.parseInt(input.next());
        System.out.println(eventList.getEventListForWeek(day, month, year).viewEventList());
    }

    // EFFECT: prints all events on given month
    private void printEventsOnMonth() {
        System.out.println("Enter the month:");
        int month = Integer.parseInt(input.next());
        System.out.println("Enter the year:");
        int year = Integer.parseInt(input.next());
        System.out.println(eventList.getEventListForMonth(month, year).viewEventList());
    }


    // MODIFIES: this
    // EFFECT: changes the status of the chosen assignment in the list of assignments
    private void changeAssignmentStatus(Assignment wanted) {
        String selection;

        System.out.println("What is the status?");
        selection = input.next();
        selection.toLowerCase();

        if (selection.equals("incomplete") || selection.equals("complete") || selection.equals("overdue")) {
            wanted.setStatus(selection);
        } else {
            System.out.println("Invalid entry.");
        }
    }

    // MODIFIES: this
    // EFFECT: changes the assignment description in the list of assignments
    private void changeAssignmentDescription(Assignment wanted) {
        String selection;

        System.out.println("What is the new description?");
        selection = input.next();
        wanted.setDescription(selection);
    }


    // MODIFIES: this
    // EFFECT: changes the assignment name in the list of assignments
    private void changeAssignmentName(Assignment wanted) {
        String selection;

        System.out.println("What is the new name?");
        selection = input.next();
        wanted.setName(selection);
    }

    // MODIFIES: this
    // EFFECT: completes the event (changes status to true) in the list of events
    private void changeEventStatus(Event wanted) {
        String selection;

        System.out.println("Would you like to complete this event? Answer 'yes' to complete.");
        selection = input.next().toLowerCase();

        if (selection.equals("yes")) {
            wanted.completeEvent();
        }
    }

    // MODIFIES: this
    // EFFECT: changes the description field of the chosen event in list of events
    private void changeEventDescription(Event wanted) {
        String selection;

        System.out.println("What is the new description?");
        selection = input.next();
        wanted.setDescription(selection);
    }

    // MODIFIES: this
    // EFFECT: changes the name field of the chosen event in list of events
    private void changeEventName(Event wanted) {
        String selection;

        System.out.println("What is the new name?");
        selection = input.next();
        wanted.setName(selection);
    }

    // EFFECT: displays options of what fields user can change
    private void displayEditOptions() {
        System.out.println("\n Would you like to:");
        System.out.println("\n name = change the name");
        System.out.println("\n description = change the description");
        System.out.println("\n set status = change the status of the task");
    }

    // EFFECTS: saves the assignment list to file
    private void saveAssignmentList() {
        try {
            jsonALWriter.open();
            jsonALWriter.writeAssignmentList(assignmentList);
            jsonALWriter.close();
            System.out.println("Saved assignments to " + JSONAL_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSONAL_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads assignment list from file
    private void loadAssignmentList() {
        try {
            assignmentList = jsonALReader.readAL();
            System.out.println("Loaded assignments from " + JSONAL_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSONAL_STORE);
        }
    }

    // EFFECTS: saves the event list to file
    private void saveEventList() {
        try {
            jsonELWriter.open();
            jsonELWriter.writeEventList(eventList);
            jsonELWriter.close();
            System.out.println("Saved events to " + JSONEL_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSONEL_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads event list from file
    private void loadEventList() {
        try {
            eventList = jsonELReader.readEL();
            System.out.println("Loaded assignments from " + JSONEL_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSONEL_STORE);
        }
    }

    public void playSaveSound(String fileName) {
        try {
            File yourFile = new File(fileName);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            // do nothing
        }
    }

}
