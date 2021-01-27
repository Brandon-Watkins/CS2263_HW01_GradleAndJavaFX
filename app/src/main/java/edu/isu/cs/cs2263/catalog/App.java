/**
 * This program utilizes JavaFX to display a course catalog, allowing the user to
 * add courses to the catalog, and list the added courses.
 *
 * @author Brandon Watkins
 * @version 2.0.0
 * @since 2021-01-20
 */
package edu.isu.cs.cs2263.catalog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.*;

public class App extends Application {
    private static final int WINDOW_WIDTH = 650;
    private static final int WINDOW_HEIGHT = 300;
    private static final int CONTROL_HEIGHT = 25;

    private ComboBox<Department> cbDepartment;
    private Button btnAddCourse;
    private TextField txtCourseNumber;
    private TextField txtCourseName;
    private TextField txtCourseCredits;

    private HBox hbErrorMessage;
    private Label lblErrorMessage;
    private String errorMessage = "";

    private Button btnDisplayAll;
    private Button btnDisplayDepartment;
    private Button btnExit;

    private ListView<Course> lvOutput;

    private Vector<Course> courses = new Vector<>();

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Adds a course to the courses Vector / course catalog.
     * @param course Course object to be added to the courses vector.
     */
    private void addCourse(Course course) { courses.add(course); }

    /**
     * Gets all added courses, regardless of department.
     * @return ArrayList of Courses from the courses Vector.
     */
    private ArrayList<Course> getCourses() {
        ArrayList<Course> out = new ArrayList<>();
        Department[] departments = Course.getDepartments();
        for (Department d: departments) {
            out.addAll(getCourses(d));
        }
        return out;
    }

    /**
     * Gets all added courses with the given department.
     * @param department Department object that you want to find classes belonging to.
     * @return ArrayList of Courses from the courses Vector.
     */
    private ArrayList<Course> getCourses(Department department) {
        ArrayList<Course> out = new ArrayList<>();
        for (Course c: courses) {
            if (c.getDepartment() == department) {
                out.add(c);
            }
        }
        return out;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Course Catalog");
        GridPane gpInput = createInputGrid();
        GridPane gpButtons = createButtonsGrid();
        HBox hbOutput = createOutputBox();
        createEventHandlers(primaryStage);
        VBox window = new VBox(gpInput, gpButtons, hbOutput);

        Scene scene = new Scene(window, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the GridPane that houses all of the input fields.
     * @return GridPane that houses all of the input fields.
     */
    private GridPane createInputGrid() {
        Label lblDepartment = new Label("Department");
        cbDepartment = createDepartmentComboBox();
        cbDepartment.setMinHeight(CONTROL_HEIGHT);
        Label lblCourseNumber = new Label("Course #");
        txtCourseNumber = new TextField("");
        txtCourseNumber.setMinHeight(CONTROL_HEIGHT);
        Label lblCourseName = new Label("Course Name");
        txtCourseName = new TextField("");
        txtCourseName.setMinHeight(CONTROL_HEIGHT);
        Label lblCourseCredits = new Label("Credits");
        txtCourseCredits = new TextField("");
        txtCourseCredits.setMinHeight(CONTROL_HEIGHT);
        Label lblAddCourse = new Label("");
        btnAddCourse = new Button("Add Course");
        btnAddCourse.setMinHeight(CONTROL_HEIGHT);

        VBox vbDepartment = new VBox(lblDepartment, cbDepartment);
        VBox vbCourseNumber = new VBox(lblCourseNumber, txtCourseNumber);
        VBox vbCourseName = new VBox(lblCourseName, txtCourseName);
        VBox vbCourseCredits = new VBox(lblCourseCredits, txtCourseCredits);
        VBox vbAddCourse = new VBox(lblAddCourse, btnAddCourse);

        GridPane out = new GridPane();
        out.setHgap(6);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(lblAddCourse.getFont().getSize() * 4);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(lblAddCourse.getFont().getSize() * 3.5);
        ColumnConstraints col5 = new ColumnConstraints();
        out.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

        out.add(vbDepartment, 0, 0);
        out.add(vbCourseNumber, 1, 0);
        out.add(vbCourseName, 2, 0);
        out.add(vbCourseCredits, 3, 0);
        out.add(vbAddCourse, 4, 0);
        out.setPadding(new javafx.geometry.Insets(6, 6, 6, 6));

        return out;
    }

    /**
     * Creates the dropdown list (ComboBox) of Departments.
     * @return ComboBox containing the available Departments.
     */
    private ComboBox<Department> createDepartmentComboBox() {
        ComboBox<Department> out = new ComboBox<>();
        Department[] departments = Course.getDepartments();
        for (Department d : departments) {
            out.getItems().add(d);
        }
        return out;
    }

    /**
     * Creates the box that houses error messages.
     * @return HBox that houses the error messages.
     */
    private HBox createErrorMessageBox() {
        lblErrorMessage = new Label(errorMessage);
        lblErrorMessage.setMinHeight(CONTROL_HEIGHT);
        lblErrorMessage.setAlignment(Pos.CENTER);
        HBox out = new HBox(lblErrorMessage);
        out.setAlignment(Pos.BASELINE_CENTER);
        out.setMinHeight(CONTROL_HEIGHT);
        out.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
        return out;
    }

    /**
     * Creates the GridPane that houses the filter and exit buttons.
     * @return GridPane housing Course Filter and Exit buttons.
     */
    private GridPane createButtonsGrid() {
        btnDisplayAll = new Button("Display (all)");
        btnDisplayAll.setMinHeight(CONTROL_HEIGHT);
        btnDisplayDepartment = new Button("Display (dept.)");
        btnDisplayDepartment.setMinHeight(CONTROL_HEIGHT);
        btnExit = new Button("Exit");
        btnExit.setMinHeight(CONTROL_HEIGHT);

        btnDisplayAll.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
        btnDisplayDepartment.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
        btnExit.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
        hbErrorMessage = createErrorMessageBox();
        updateErrorMessage();

        GridPane out = new GridPane();
        out.setHgap(6);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        ColumnConstraints col4 = new ColumnConstraints();
        out.getColumnConstraints().addAll(col1, col2, col3, col4);

        out.add(btnDisplayAll, 0, 0);
        out.add(btnDisplayDepartment, 1, 0);
        out.add(hbErrorMessage, 2, 0);
        out.add(btnExit, 3, 0);
        out.setPadding(new javafx.geometry.Insets(6, 6, 6, 6));

        return out;
    }

    /**
     * Creates the HBox that houses the output list of courses.
     * @return HBox housing Course List.
     */
    private HBox createOutputBox() {
        lvOutput = createOutputList();
        Label lblOutput = new Label("Courses");
        VBox vbOutput = new VBox(lblOutput, lvOutput);
        HBox out = new HBox(vbOutput);
        out.setPadding(new javafx.geometry.Insets(6, 6, 6, 6));

        return out;
    }

    /**
     * Creates the ListView containing the course list output.
     * @return ListView of Courses.
     */
    private ListView<Course> createOutputList() {
        ObservableList<Course> ol = FXCollections.observableArrayList();
        ListView<Course> out = new ListView(ol);
        out.setBorder(
                new Border(
                        new BorderStroke(
                                new Color(.7, 0.7, 0.7, 1),
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(5),
                                new BorderWidths(1)
                        )
                )
        );
        out.setBackground(
                new Background(
                        new BackgroundFill(
                                new Color(.9, .9, .9, .5),
                                new CornerRadii(5),
                                new Insets(1, 1, 1, 1)
                        )
                )
        );
        out.setPrefWidth(WINDOW_WIDTH - 12);
        return out;
    }

    /**
     * Creates the Event Handlers for the program.
     * @param primaryStage The Primary Stage passed into start().
     */
    private void createEventHandlers(Stage primaryStage) {
        createExitHandler(primaryStage);
        createAddCourseHandler();
        createDisplayAllHandler();
        createDisplayDepartmentHandler();
    }

    /**
     * Creates the Event Handler for Quitting the program.
     * @param primaryStage The Primary Stage passed into start().
     */
    private void createExitHandler(Stage primaryStage) {
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        btnExit.setCancelButton(true);
        btnExit.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText("Course catalog will be lost");
            alert.setContentText("Do you wish to Exit?");

            ButtonType btYes = new ButtonType("Yes");
            ButtonType btCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(btYes, btCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent() || result.get() == btCancel) {
                // user closed the confirmation window, or clicked on cancel. Cancelling Exit operation.
            } else {
                // user confirmed they want to exit, or something broke. Exiting in either case.
                if (result.get() == btYes) {
                    // user confirmed.
                } else {
                    // something broke.
                    System.out.println("Unexpected input from Exit Confirmation Dialog: " + result.get().getText() + ".");
                }
                WindowEvent.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        });
    }

    /**
     * Creates the Event Handler for Adding Courses to the catalog.
     */
    private void createAddCourseHandler() {
        btnAddCourse.setDefaultButton(true);
        btnAddCourse.setOnAction(e -> {
            String missingMessage = "";
            String courseNum = txtCourseNumber.getText();
            String courseName = txtCourseName.getText();
            String courseCreditsString = txtCourseCredits.getText();
            int deptIndex = cbDepartment.getSelectionModel().getSelectedIndex();

            if (deptIndex < 0) {
                missingMessage = " department,";
            }
            if (courseNum == null || courseNum.length() < 1) {
                missingMessage += " course number,";
            }
            if (courseName == null || courseName.length() < 1) {
                missingMessage += " course name,";
            }
            if (courseCreditsString == null || courseCreditsString.length() < 1 || Integer.parseInt(courseCreditsString) < 0) {
                missingMessage += " credits,";
            }

            if (missingMessage.length() > 0) {
                missingMessage = "Missing Field" + (missingMessage.length() > 16 ? "s" : "") + ":" +
                        missingMessage.substring(0, missingMessage.length() - 1) + ".";
            }
            else {
                addCourse(new Course(courseNum, courseName, Integer.parseInt(courseCreditsString), deptIndex));
                txtCourseNumber.setText("");
                txtCourseName.setText("");
                txtCourseCredits.setText("");
            }

            errorMessage = missingMessage;
            updateErrorMessage();
        });
    }

    /**
     * Creates the Event Handler for Clicking on the Display (all) button.
     */
    private void createDisplayAllHandler() {
        btnDisplayAll.setOnAction(e -> {
            errorMessage = "";
            updateErrorMessage();
            updateCourseList(getCourses());
        });
    }

    /**
     * Creates the Event Handler for Clicking on the Display (dept.) button.
     */
    private void createDisplayDepartmentHandler() {
        btnDisplayDepartment.setOnAction(e -> {
            Department department = cbDepartment.getSelectionModel().getSelectedItem();
            if (department != null) {
                errorMessage = "";
                updateCourseList(getCourses(department));
            } else {
                errorMessage = "Select a department to filter courses by.";
            }
            updateErrorMessage();
        });
    }

    /**
     * Updates the course ListView, and sorts the list to organize the output.
     * @param courseList ArrayList of Courses to be displayed in the output.
     */
    private void updateCourseList(ArrayList<Course> courseList) {
        if (lvOutput.getItems() != null && lvOutput.getItems().size() > 0) lvOutput.getItems().clear();
        Collections.sort(courseList);
        lvOutput.getItems().addAll(courseList);
    }

    /**
     * Updates the message in the error message box.
     */
    private void updateErrorMessage() {
        if (errorMessage != null && errorMessage != "") {
            hbErrorMessage.setBorder(
                    new Border(
                            new BorderStroke(
                                    new Color(1, 0, 0, 1),
                                    BorderStrokeStyle.SOLID,
                                    new CornerRadii(5),
                                    new BorderWidths(1)
                            )
                    )
            );
            hbErrorMessage.setBackground(
                    new Background(
                            new BackgroundFill(
                                    new Color(1, .5, .5, .5),
                                    new CornerRadii(5),
                                    new Insets(1, 1, 1, 1)
                            )
                    )
            );
        } else {
            hbErrorMessage.setBorder(Border.EMPTY);
            hbErrorMessage.setBackground(Background.EMPTY);
        }
        lblErrorMessage.setText(errorMessage);
    }
}
