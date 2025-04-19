package com.example.project2;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {
    static DistrictBinaryTree districtListTree = new DistrictBinaryTree();
    static LocationBinaryTree locationListTree = new LocationBinaryTree();
    static Stack stack = new Stack();

    private TableView<Martyr> tableView = new TableView<>();
    private ObservableList<Martyr> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 750, 460);

            MenuBar menuBar = new MenuBar();

            Menu menuDistruct = new Menu("Distruct");
            MenuItem menuItemInsert = new MenuItem("Insert District");
            MenuItem menuItemDelete = new MenuItem("Delete District");
            MenuItem menuItemUpdate = new MenuItem("Update District");
            MenuItem menuItemSearch = new MenuItem("Search District");
            MenuItem menuItemNavigate = new MenuItem("Navigate District");
            menuDistruct.getItems().addAll(menuItemInsert, menuItemDelete, menuItemUpdate, menuItemSearch,
                    menuItemNavigate);

            Menu menuLocation = new Menu("Location");
            MenuItem menuItemInsertNewLocation = new MenuItem("Insert Location");
            MenuItem menuItemDeleteNewLocation = new MenuItem("Delete Location");
            MenuItem menuItemUpdateNewLocation = new MenuItem("Update Location");
            MenuItem menuItemSearchNewLocation = new MenuItem("Search Location");
            MenuItem menuItemNavigateNewLocation = new MenuItem("Navigate Location");

            menuLocation.getItems().addAll(menuItemInsertNewLocation, menuItemDeleteNewLocation,
                    menuItemUpdateNewLocation, menuItemSearchNewLocation, menuItemNavigateNewLocation);

            Menu menuMartyr = new Menu("MartyrData");
            MenuItem menuItemNavigateMartyr = new MenuItem("Navigate Location");
//			MenuItem menuItemInsertNewLocation = new MenuItem("Insert Location");
            menuMartyr.getItems().add(menuItemNavigateMartyr);

            menuBar.getMenus().addAll(menuDistruct, menuLocation, menuMartyr);

            Button openButton = new Button("Open File");
            openButton.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    try {
                        readDataFromFile(file);
                        setupTable();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            menuItemInsert.setOnAction(e -> {
                insertDistrict(primaryStage);

            });
            menuItemDelete.setOnAction(e -> {
                removeDistrict(primaryStage);

            });
            menuItemUpdate.setOnAction(e -> {
                updateDistrict(primaryStage, tableView);

            });
            menuItemNavigate.setOnAction(e -> {
                navigatenDistrict(primaryStage);

            });

            menuItemInsertNewLocation.setOnAction(e -> {
                insertLocation(primaryStage);

            });
            menuItemDeleteNewLocation.setOnAction(e -> {
                deleteLocation(primaryStage);

            });
            menuItemUpdateNewLocation.setOnAction(e -> {
                updateLocation(primaryStage, tableView);

            });
            menuItemSearchNewLocation.setOnAction(e -> {
                searchLocation(primaryStage, tableView);

            });
            menuItemNavigateNewLocation.setOnAction(e -> {
                navigateLocation(primaryStage, tableView);

            });
            menuItemNavigateMartyr.setOnAction(e -> {
                navigateMartyrLocation(primaryStage, tableView);

            });

            VBox vbox = new VBox(menuBar, openButton, tableView);
            root.setTop(vbox);
            primaryStage.setTitle("Martyr");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateMartyrLocation(Stage navigateMartyrLocationStage, TableView<Martyr> tableView) {
        navigateMartyrLocationStage.setTitle("Navigate Martyr Dates");

        VBox navigateBox = new VBox();
        navigateBox.setSpacing(10);
        navigateBox.setPadding(new Insets(10));

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label averageAgeLabel = new Label("Average Age:");
        TextField averageAgeTextField = new TextField();
        averageAgeTextField.setEditable(false);

        Label youngestMartyrLabel = new Label("Youngest Martyr:");
        TextField youngestMartyrTextField = new TextField();
        youngestMartyrTextField.setEditable(false);

        Label oldestMartyrLabel = new Label("Oldest Martyr:");
        TextField oldestMartyrTextField = new TextField();
        oldestMartyrTextField.setEditable(false);

        Button loadButton = new Button("Load");

        Button prevButton = new Button("<---Previous");
        Button nextButton = new Button("Next---->");
        Button backButton = new Button("<--Back");

        HBox buttonBox = new HBox(20, prevButton, loadButton, nextButton);
        buttonBox.setAlignment(Pos.CENTER);

        TableView<Martyr> martyrsTableView = new TableView<>();
        martyrsTableView.setPrefWidth(600);
        martyrsTableView.setPrefHeight(400);

        TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Martyr, String> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAge())));

        TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGender()));

        TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
        districtColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDistrict()));

        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        martyrsTableView.getColumns().addAll(districtColumn, locationColumn, nameColumn, ageColumn, genderColumn);

        navigateBox.getChildren().addAll(dateLabel, datePicker, averageAgeLabel, averageAgeTextField,
                youngestMartyrLabel, youngestMartyrTextField, oldestMartyrLabel, oldestMartyrTextField,
                martyrsTableView, buttonBox, backButton);

        loadButton.setOnAction(event -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                String selectedDateString = selectedDate.toString();

                // Filter the data for the selected date
                ObservableList<Martyr> filteredData = FXCollections.observableArrayList();
                for (Martyr martyr : data) {
                    if (martyr.getDate().equals(selectedDateString)) {
                        filteredData.add(martyr);
                    }
                }

                // Update the TableView with the filtered data
                martyrsTableView.setItems(filteredData);

                // Update the average age, youngest martyr, and oldest martyr text fields
                updateMartyrsInfo(selectedDateString, averageAgeTextField, youngestMartyrTextField,
                        oldestMartyrTextField);
            } else {
                showErrorAlert("Error", "Empty Date", "Please select a date.");
            }
        });

        prevButton.setOnAction(event -> {
            LocalDate currentDate = datePicker.getValue();
            if (currentDate != null) {
                LocalDate previousDate = currentDate.minusDays(1);
                datePicker.setValue(previousDate); // Update DatePicker to show the previous date
                // Trigger the loadButton action to update the information based on the new date
                loadButton.fire();
            } else {
                showErrorAlert("Error", "Empty Date", "Please select a date.");
            }
        });

        nextButton.setOnAction(event -> {
            LocalDate currentDate = datePicker.getValue();
            if (currentDate != null) {
                LocalDate nextDate = currentDate.plusDays(1);
                datePicker.setValue(nextDate); // Update DatePicker to show the next date
                // Trigger the loadButton action to update the information based on the new date
                loadButton.fire();
            } else {
                showErrorAlert("Error", "Empty Date", "Please select a date.");
            }
        });

        backButton.setOnAction(event -> {
            start(navigateMartyrLocationStage);
        });

        Scene navigateScene = new Scene(navigateBox, 800, 600);
        navigateMartyrLocationStage.setScene(navigateScene);
        navigateMartyrLocationStage.show();
    }

    private void updateMartyrsInfo(String selectedDateString, TextField averageAgeTextField,
                                   TextField youngestMartyrTextField, TextField oldestMartyrTextField) {
        // TODO Auto-generated method stub

    }

    private Date getPreviousDate(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private Date getNextDate(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    private void updateMartyrsInfo(Date selectedDate, TextField averageAgeTextField, TextField youngestMartyrTextField,
                                   TextField oldestMartyrTextField) {
        List<Integer> ages = new ArrayList<>();
        int totalAge = 0;
        int youngestAge = Integer.MAX_VALUE;
        int oldestAge = Integer.MIN_VALUE;
        String youngestMartyrName = "";
        String oldestMartyrName = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // Update the date format if necessary

        for (Martyr martyr : data) {
            Date martyrDate = null;
            try {
                String dateStr = martyr.getDate();
                System.out.println("Date String: " + dateStr); // Print the date string for debugging purposes
                Date date = dateFormat.parse(dateStr);
                if (date != null && date.compareTo(selectedDate) <= 0) {
                    int age = calculateAge(date, selectedDate);
                    ages.add(age);
                    totalAge += age;

                    if (age < youngestAge) {
                        youngestAge = age;
                        youngestMartyrName = martyr.getName();
                    }

                    if (age > oldestAge) {
                        oldestAge = age;
                        oldestMartyrName = martyr.getName();
                    }
                }
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
                // Handle the parsing error, such as setting default values or skipping the
                // record
            }
        }

        // Update the remaining logic for displaying average age, youngest martyr, and
        // oldest martyr
        // Ensure appropriate error handling and default values are set if necessary
    }

    private int calculateAge(Date birthDate, Date currentDate) {
        Calendar calBirth = Calendar.getInstance();
        Calendar calCurrent = Calendar.getInstance();
        calBirth.setTime(birthDate);
        calCurrent.setTime(currentDate);

        int age = calCurrent.get(Calendar.YEAR) - calBirth.get(Calendar.YEAR);
        if (calCurrent.get(Calendar.DAY_OF_YEAR) < calBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    private void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void navigateLocation(Stage primaryStage, TableView<Martyr> tableView) {
        primaryStage.setTitle("Navigate Location");

        VBox navigateBox = new VBox();
        navigateBox.setSpacing(10);
        navigateBox.setPadding(new Insets(10));

        HBox locationBox = new HBox();
        locationBox.setAlignment(Pos.CENTER);
        Label locationLabel = new Label("Location:");
        ComboBox<String> locationComboBox = new ComboBox<>();
        locationComboBox.getItems().addAll(getUniqueLocations());
        locationComboBox.setEditable(false);
        locationBox.getChildren().addAll(locationLabel, locationComboBox);

        GridPane infoGridPane = new GridPane();
        infoGridPane.setPadding(new Insets(10));
        infoGridPane.setHgap(10);
        infoGridPane.setVgap(10);

        Label earliestDateLabel = new Label("Earliest Date:");
        Label latestDateLabel = new Label("Latest Date:");
        Label maxMartyrsDateLabel = new Label("Date with Maximum Martyrs:");

        TextField earliestDateTextField = new TextField();
        earliestDateTextField.setEditable(false);
        TextField latestDateTextField = new TextField();
        latestDateTextField.setEditable(false);
        TextField maxMartyrsDateTextField = new TextField();
        maxMartyrsDateTextField.setEditable(false);

        infoGridPane.add(earliestDateLabel, 0, 0);
        infoGridPane.add(latestDateLabel, 0, 1);
        infoGridPane.add(maxMartyrsDateLabel, 0, 2);
        infoGridPane.add(earliestDateTextField, 1, 0);
        infoGridPane.add(latestDateTextField, 1, 1);
        infoGridPane.add(maxMartyrsDateTextField, 1, 2);

        TableView<Martyr> locationTableView = new TableView<>();
        TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Martyr, String> ageColumn = new TableColumn<>("Age");
        TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        ageColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAge())));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));

        locationTableView.getColumns().addAll(nameColumn, ageColumn, genderColumn);

        Button loadButton = new Button("Load");
        Button nextButton = new Button("Next--->");
        Button prevButton = new Button("<--Previous");
        Button backButton = new Button("<--Back");
        backButton.setOnAction(event -> {
            start(primaryStage);
        });

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(30);
        buttonBox.getChildren().addAll(prevButton, loadButton, nextButton);

        navigateBox.getChildren().addAll(locationBox, infoGridPane, locationTableView, buttonBox, messageArea,
                backButton);

        // Logic for nextButton and prevButton
        nextButton.setOnAction(event -> {
            String selectedLocation = locationComboBox.getValue();
            if (selectedLocation == null) {
                showLocationSelectionAlert();
            } else {
                int currentIndex = locationComboBox.getSelectionModel().getSelectedIndex();
                if (currentIndex < locationComboBox.getItems().size() - 1) {
                    locationComboBox.getSelectionModel().select(currentIndex + 1);
                }
            }
        });

        prevButton.setOnAction(event -> {
            String selectedLocation = locationComboBox.getValue();
            if (selectedLocation == null) {
                showLocationSelectionAlert();
            } else {
                int currentIndex = locationComboBox.getSelectionModel().getSelectedIndex();
                if (currentIndex > 0) {
                    locationComboBox.getSelectionModel().select(currentIndex - 1);
                }
            }
        });

        loadButton.setOnAction(event -> {
            String selectedLocation = locationComboBox.getValue();
            if (selectedLocation == null) {
                showLocationSelectionAlert();
            } else {
                ObservableList<Martyr> filteredData = FXCollections.observableArrayList();
                for (Martyr martyr : data) {
                    if (martyr.getLocation().equals(selectedLocation)) {
                        filteredData.add(martyr);
                    }
                }
                locationTableView.setItems(filteredData);
                messageArea.appendText("Data loaded successfully.\n");
            }
        });
        locationComboBox.setOnAction(event -> {
            String selectedLocation = locationComboBox.getValue();
            if (selectedLocation != null) {
                // Calculate earliest date
                String earliestDate = calculateEarliestDate(selectedLocation);
                earliestDateTextField.setText(earliestDate);

                // Calculate latest date
                String latestDate = calculateLatestDate(selectedLocation);
                latestDateTextField.setText(latestDate);

                // Calculate date with maximum martyrs
                String maxMartyrsDate = calculateMaxMartyrsDate(selectedLocation);
                maxMartyrsDateTextField.setText(maxMartyrsDate);
            }
        });

        Scene navigateScene = new Scene(navigateBox, 800, 600);
        primaryStage.setScene(navigateScene);
        primaryStage.show();
    }

    private void showLocationSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please choose a location.");
        alert.showAndWait();
    }

    private String calculateEarliestDate(String location) {
        // Implement logic to calculate earliest date for the given location
        return "";
    }

    private String calculateLatestDate(String location) {
        // Implement logic to calculate latest date for the given location
        return "";
    }

    private String calculateMaxMartyrsDate(String location) {
        // Implement logic to calculate date with maximum martyrs for the given location
        return "";
    }

    private void displayLocationInformation(LocationNode current, TextArea messageArea, TextField nextTextField,
                                            TextField prevTextField) {
        messageArea.appendText("Location: " + current.getLocation() + "\n");
// Implement other desired operations related to the location

// Example: Display additional information about the location
        messageArea.appendText("Additional information about the location...\n");
    }

    private void inOrderTraversal(LocationNode root, TextField nextTextField) {
        if (root != null) {
            inOrderTraversal(root.getLeft(), nextTextField);
            nextTextField.setText(root.getLocation());
            inOrderTraversal(root.getRight(), nextTextField);
        }
    }

    private LocationNode findNode(LocationNode root, String nextLocation) {
        if (root == null || root.getLocation().equals(nextLocation)) {
            return root;
        }
        LocationNode leftResult = findNode(root.getLeft(), nextLocation);
        if (leftResult != null) {
            return leftResult;
        }
        return findNode(root.getRight(), nextLocation);
    }

    private void pushLeftSubtree(LocationNode DistrictNode, Stack stack) {
        while (DistrictNode != null) {
            stack.push(DistrictNode);
            DistrictNode = DistrictNode.getLeft();
        }
    }

    private void searchLocation(Stage searchStage, TableView<Martyr> tableView) {
        searchStage.setTitle("Search Location");

        VBox searchBox = new VBox();
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(10));

        TableView<Martyr> locationTableView = new TableView<>();
        locationTableView.setItems(data);

        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        locationTableView.getColumns().add(locationColumn);

        ComboBox<String> searchComboBox = new ComboBox<>();
        searchComboBox.getItems().addAll(getUniqueLocations());
        searchComboBox.setPromptText("Select Location");

        TextField selectedLocationTextField = new TextField();
        selectedLocationTextField.setEditable(false);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Type Location to Search");

        Button searchButton = new Button("Search");
        Button backButton = new Button("<--Back");

        searchBox.getChildren().addAll(locationTableView, searchComboBox, selectedLocationTextField, searchTextField,
                searchButton, backButton);

        searchComboBox.setOnAction(event -> {
            String selectedLocation = searchComboBox.getValue();
            if (selectedLocation != null && !selectedLocation.isEmpty()) {
                selectedLocationTextField.setText("This Is Location You Want To Search -->" + selectedLocation);
            }
        });

        searchButton.setOnAction(event -> {
            String selectedLocation = searchComboBox.getValue();
            String typedLocation = searchTextField.getText();
            if (selectedLocation != null && !selectedLocation.isEmpty()) {
                tableView.getSelectionModel().clearSelection();
                data.stream().filter(martyr -> martyr.getLocation().equalsIgnoreCase(selectedLocation)).findFirst()
                        .ifPresent(martyr -> {
                            // Select and scroll to the row containing the searched location
                            tableView.getSelectionModel().select(martyr);
                            tableView.scrollTo(martyr);
//										System.out.println("Location found: " + martyr.getLocation());
                        });
            } else if (!typedLocation.isEmpty()) {
                boolean locationFound = data.stream()
                        .anyMatch(martyr -> martyr.getLocation().equalsIgnoreCase(typedLocation));
                if (locationFound) {
                    selectedLocationTextField.setText("Location Found: " + typedLocation);
                } else {
                    selectedLocationTextField.setText("Location Not Found: " + typedLocation);
                }
            } else {
                selectedLocationTextField
                        .setText("Please Choose a Location From the List Box or Type a Location to Search");
            }
        });

        backButton.setOnAction(event -> {
            start(searchStage);
        });

        Scene searchScene = new Scene(searchBox, 450, 500);
        searchStage.setScene(searchScene);
        searchStage.show();
    }

    private void updateLocation(Stage updateStage, TableView<Martyr> firstStageTableView) {
        updateStage.setTitle("Update Location");

        VBox updateBox = new VBox();
        updateBox.setSpacing(10);
        updateBox.setPadding(new Insets(10));

        TableView<Martyr> locationTableView = new TableView<>();
        locationTableView.setItems(data);

        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        locationTableView.getColumns().add(locationColumn);

        ComboBox<String> locationComboBox = new ComboBox<>();
        locationComboBox.getItems().addAll(getUniqueLocations());
        locationComboBox.setPromptText("Select Location");

        Label oldLocationLabel = new Label("Enter Old Location Name:");
        TextField oldLocationField = new TextField();

        Label newLocationLabel = new Label("Enter New Location Name:");
        TextField newLocationField = new TextField();

        Button updateButton = new Button("Update");
        Button backButton = new Button("<--Back");

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        updateBox.getChildren().addAll(locationTableView, locationComboBox, oldLocationLabel, oldLocationField,
                newLocationLabel, newLocationField, updateButton, messageArea, backButton);

        // Update text field with selected location from combo box
        locationComboBox.setOnAction(event -> {
            String selectedLocation = locationComboBox.getValue();
            if (selectedLocation != null && !selectedLocation.isEmpty()) {
                oldLocationField.setText(selectedLocation);
            }
        });

        updateButton.setOnAction(updateEvent -> {
            String oldLocation = oldLocationField.getText();
            String newLocation = newLocationField.getText();
            if (oldLocation != null && !oldLocation.isEmpty() && newLocation != null && !newLocation.isEmpty()) {
                Martyr martyrToUpdate = data.stream().filter(martyr -> martyr.getLocation().equals(oldLocation))
                        .findFirst().orElse(null);
                if (martyrToUpdate != null) {
                    martyrToUpdate.setLocation(newLocation);
                    messageArea.appendText("Location updated successfully.\n");
                    locationTableView.refresh(); // Refresh the TableView in the update stage
                    firstStageTableView.refresh(); // Refresh the TableView in the first stage
                    locationComboBox.getItems().setAll(getUniqueLocations());
                    oldLocationField.clear(); // Clear the old location text field
                    newLocationField.clear(); // Clear the new location text field
                } else {
                    messageArea.appendText(
                            "Old Location '" + oldLocation + "' not found. Please enter a valid old location name.\n");
                }
            } else {
                messageArea.appendText("Please enter both old and new location names.\n");
            }
        });

        backButton.setOnAction(event -> {
            start(updateStage);
        });

        Scene updateScene = new Scene(updateBox, 450, 500);
        updateStage.setScene(updateScene);
        updateStage.show();
    }

    private void deleteLocation(Stage deleteStage) {
        deleteStage.setTitle("Delete Location");

        VBox deleteBox = new VBox();
        deleteBox.setSpacing(10);
        deleteBox.setPadding(new Insets(10));

        TableView<Martyr> locationTableView = new TableView<>();
        locationTableView.setItems(data);

        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        locationTableView.getColumns().add(locationColumn);

        ComboBox<String> locationComboBox = new ComboBox<>();
        locationComboBox.getItems().addAll(getUniqueLocations());
        locationComboBox.setPromptText("Select Location");

        Label deleteLabel = new Label("Enter Location to Delete:");
        TextField deleteField = new TextField();

        Button deleteButton = new Button("Delete");
        Button backButton = new Button("<--Back");

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        deleteBox.getChildren().addAll(locationTableView, locationComboBox, deleteLabel, deleteField, deleteButton,
                messageArea, backButton);

        // Update text field with selected location from combo box
        locationComboBox.setOnAction(event -> {
            String selectedLocation = locationComboBox.getValue();
            if (selectedLocation != null && !selectedLocation.isEmpty()) {
                deleteField.setText(selectedLocation);
            }
        });

        deleteButton.setOnAction(deleteEvent -> {
            String locationToDelete = deleteField.getText();
            if (locationToDelete != null && !locationToDelete.isEmpty()) {
                Martyr martyrToDelete = data.stream().filter(martyr -> martyr.getLocation().equals(locationToDelete))
                        .findFirst().orElse(null);
                if (martyrToDelete != null) {
                    locationListTree.removeLocation(locationToDelete);
                    data.remove(martyrToDelete);
                    locationTableView.setItems(FXCollections.observableArrayList(data)); // Refresh TableView
                    messageArea.appendText("Deleted Successfully --> " + locationToDelete + "\n");
                } else {
                    messageArea.appendText(
                            "Location '" + locationToDelete + "' not found. Please enter a valid location.\n");
                }
            } else {
                messageArea.appendText("Please Enter a Location to Delete\n");
            }
            deleteField.clear(); // Clear the text field after deleting
        });

        backButton.setOnAction(backEvent -> {
            start(deleteStage);
        });

        Scene deleteScene = new Scene(deleteBox, 450, 500);
        deleteStage.setScene(deleteScene);
        deleteStage.show();
    }

    private Set<String> getUniqueLocations() {
        Set<String> uniqueLocations = new HashSet<>();
        for (Martyr martyr : data) {
            uniqueLocations.add(martyr.getLocation());
        }
        return uniqueLocations;
    }

    private void navigatenDistrict(Stage navigatenStage) {
        navigatenStage.setTitle("Navigate District");

        VBox navigateBox = new VBox();
        navigateBox.setSpacing(10);
        navigateBox.setPadding(new Insets(30));

        HBox districtBox = new HBox();
        districtBox.setAlignment(Pos.CENTER);
        Label districtLabel = new Label("District:");
        ComboBox<String> districtComboBox = new ComboBox<>();
        districtComboBox.getItems().addAll(getUniqueDistricts());
        districtComboBox.setEditable(false);
        districtBox.getChildren().addAll(districtLabel, districtComboBox);

        HBox totalMartyrsBox = new HBox();
        totalMartyrsBox.setAlignment(Pos.CENTER);
        Label totalMartyrsLabel = new Label("Total Martyrs:");
        TextField totalMartyrsField = new TextField();
        totalMartyrsField.setEditable(false);
        totalMartyrsBox.getChildren().addAll(totalMartyrsLabel, totalMartyrsField);

        TableView<Martyr> districtTableView = new TableView<>();
        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Martyr, String> ageColumn = new TableColumn<>("Age");
        TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");

        locationColumn.setCellValueFactory(
                (Callback<CellDataFeatures<Martyr, String>, ObservableValue<String>>) new Callback<TableColumn.CellDataFeatures<Martyr, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Martyr, String> param) {
                        return new SimpleStringProperty(param.getValue().getLocation());
                    }
                });

        nameColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Martyr, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Martyr, String> param) {
                        return new SimpleStringProperty(param.getValue().getName());
                    }
                });

        ageColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Martyr, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Martyr, String> param) {
                        return new SimpleStringProperty(String.valueOf(param.getValue().getAge()));
                    }
                });

        genderColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Martyr, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Martyr, String> param) {
                        return new SimpleStringProperty(param.getValue().getGender());
                    }
                });

        districtTableView.getColumns().addAll(locationColumn, nameColumn, ageColumn, genderColumn);

        Button loadButton = new Button("Load");
        Button nextButton = new Button("Next--->");
        Button prevButton = new Button("<--Previous");

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);
        Button backButton = new Button("<--Back");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(30);
        buttonBox.getChildren().addAll(prevButton, loadButton, nextButton);

        navigateBox.getChildren().addAll(districtBox, totalMartyrsBox, districtTableView, buttonBox, messageArea,
                backButton);
        backButton.setOnAction(backEvent -> {
            start(navigatenStage);
        });
        districtComboBox.setOnAction(event -> {
            String selectedDistrict = districtComboBox.getValue();
            if (selectedDistrict != null) {
                int totalMartyrs = calculateTotalMartyrs(selectedDistrict);
                totalMartyrsField.setText(String.valueOf(totalMartyrs));
            }
        });
        loadButton.setOnAction(event -> {
            String selectedDistrict = districtComboBox.getValue();
            if (selectedDistrict == null || selectedDistrict.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please choose  district ");
                alert.showAndWait();
            } else {
                ObservableList<Martyr> filteredData = FXCollections.observableArrayList();
                for (Martyr martyr : data) {
                    if (martyr.getDistrict().equals(selectedDistrict)) {
                        filteredData.add(martyr);
                    }
                }
                districtTableView.setItems(filteredData);
                messageArea.appendText("--> Data loaded successfully\n");
            }
        });

        nextButton.setOnAction(event -> {
            int currentIndex = districtComboBox.getSelectionModel().getSelectedIndex();
            if (currentIndex < districtComboBox.getItems().size() - 1) {
                districtComboBox.getSelectionModel().select(currentIndex + 1);
            }
        });

        prevButton.setOnAction(event -> {
            int currentIndex = districtComboBox.getSelectionModel().getSelectedIndex();
            if (currentIndex > 0) {
                districtComboBox.getSelectionModel().select(currentIndex - 1);
            }
        });

        Scene navigateScene = new Scene(navigateBox, 800, 600);
        navigatenStage.setScene(navigateScene);
        navigatenStage.show();
    }

    private int calculateTotalMartyrs(String district) {
        int totalMartyrs = 0;
        for (Martyr martyr : data) {
            if (martyr.getDistrict().equals(district)) {
                totalMartyrs++;
            }
        }
        return totalMartyrs;
    }

    private void clearStack(Stack stack) {
        stack.clear(null, null, null);
    }

    private void inOrderTraversal(DistrictNode node, TextField textField) {
        if (node != null) {
            inOrderTraversal(node.getLeft(), textField); // Recursively traverse left subtree
            // Append district to text field
            textField.setText(textField.getText() + (textField.getText().isEmpty() ? "" : ", ") + node.getDistrict());
            inOrderTraversal(node.getRight(), textField); // Recursively traverse right subtree
        }
    }

    private DistrictNode findNode(DistrictNode root, String district) {
        if (root == null) {
            return null;
        }

        int compareResult = district.compareToIgnoreCase(root.getDistrict());
        if (compareResult == 0) {
            return root;
        } else if (compareResult < 0) {
            return findNode(root.getLeft(), district);
        } else {
            return findNode(root.getRight(), district);
        }
    }

    private void displayDistrictInformation(DistrictNode current, TextArea messageArea, TextField nextTextField,
                                            TextField prevTextField) {
        if (current != null) {
            nextTextField.setText(current.getRight() != null ? current.getRight().getDistrict() : "");
            prevTextField.setText(current.getLeft() != null ? current.getLeft().getDistrict() : "");
            messageArea
                    .setText("Total number of martyrs in " + current.getDistrict() + ": " + getTotalMartyrs(current));
        }
    }

    private int getTotalMartyrs(DistrictNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.martyrs + getTotalMartyrs(node.getLeft()) + getTotalMartyrs(node.getRight());
        }
    }

    // Method to push the left subtree onto the stack
    private void pushLeftSubtreeDistruct(DistrictNode node, Stack stack) {
        while (node != null) {
            stack.push(node);
            node = node.getLeft();
        }
    }

    private void updateDistrict(Stage updateStage, TableView<Martyr> firstStageTableView) {
        updateStage.setTitle("Update District");

        VBox updateBox = new VBox();
        updateBox.setSpacing(10);
        updateBox.setPadding(new Insets(10));

        TableView<String> districtTableView = new TableView<>();
        ObservableList<String> districtData = FXCollections.observableArrayList(getUniqueDistricts());
        districtTableView.setItems(districtData);

        TableColumn<String, String> districtColumn = new TableColumn<>("District");
        districtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        districtTableView.getColumns().add(districtColumn);

        Label oldDistrictLabel = new Label("Enter Old District Name:");
        TextField oldDistrictField = new TextField();

        Label newDistrictLabel = new Label("Enter New District Name:");
        TextField newDistrictField = new TextField();

        Button updateButton = new Button("Update");
        Button backButton = new Button("<--Back");

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        updateBox.getChildren().addAll(districtTableView, oldDistrictLabel, oldDistrictField, newDistrictLabel,
                newDistrictField, updateButton, messageArea, backButton);

        updateButton.setOnAction(updateEvent -> {
            String oldDistrict = oldDistrictField.getText();
            String newDistrict = newDistrictField.getText();
            if (oldDistrict != null && !oldDistrict.isEmpty() && newDistrict != null && !newDistrict.isEmpty()) {
                if (getUniqueDistricts().contains(oldDistrict)) {
                    districtListTree.removeDistrict(oldDistrict);
                    data.removeIf(martyr -> martyr.getDistrict().equals(oldDistrict));
                    districtListTree.addDistrict(newDistrict);
                    data.add(new Martyr(newDistrict, newDistrict, newDistrict, newDistrict, newDistrict, newDistrict));
                    messageArea.appendText("District updated successfully.\n");
                    oldDistrictField.clear(); // Clear the old district text field
                    newDistrictField.clear(); // Clear the new district text field
                    districtData.setAll(getUniqueDistricts());
                } else {
                    oldDistrictField.clear(); // Clear the old district text field
                    newDistrictField.clear(); // Clear the new district text field
                    messageArea.appendText(
                            "Old District '" + oldDistrict + "' not found. Please enter a valid old district name.\n");
                }
            } else {
                messageArea.appendText("Please enter both old and new district names.\n");
            }
        });

        backButton.setOnAction(backEvent -> {
            start(updateStage);
        });

        Scene updateScene = new Scene(updateBox, 450, 550);
        updateStage.setScene(updateScene);
        updateStage.show();
    }

    private void removeDistrict(Stage removeStage) {
        removeStage.setTitle("Remove District");

        VBox removeBox = new VBox();
        removeBox.setSpacing(10);
        removeBox.setPadding(new Insets(10));

        TableView<String> districtTableView = new TableView<>();
        ObservableList<String> districtData = FXCollections.observableArrayList(getUniqueDistricts());
        districtTableView.setItems(districtData);

        TableColumn<String, String> districtColumn = new TableColumn<>("District");
        districtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        districtTableView.getColumns().add(districtColumn);

//		Label nameLabel = new Label("Select or Enter District to Remove:");
//		TextField districtField = new TextField();

        Label removeLabel = new Label("Enter District to Remove:");
        TextField removeField = new TextField();

        Button removeButton = new Button("Remove");
        Button backButton = new Button("<--Back");

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        removeBox.getChildren().addAll(districtTableView, removeLabel, removeField, removeButton, messageArea,
                backButton);

        removeButton.setOnAction(removeEvent -> {
            String districtToRemove = removeField.getText();
            if (districtToRemove != null && !districtToRemove.isEmpty()) {
                if (getUniqueDistricts().contains(districtToRemove)) {
                    districtListTree.removeDistrict(districtToRemove);
                    data.removeIf(martyr -> martyr.getDistrict().equals(districtToRemove));
                    messageArea.appendText("Removed Successfully -->" + districtToRemove + "\n");
                    districtData.setAll(getUniqueDistricts());
                } else {
                    messageArea.appendText("District '" + districtToRemove
                            + "' not found, please enter a district that exists in the table\n");
                }
            } else {
                messageArea.appendText("Please Enter a District to Remove\n");
            }
            removeField.clear(); // Clear the text field after removing
        });
        districtTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                String selectedDistrict = districtTableView.getSelectionModel().getSelectedItem();
                if (selectedDistrict != null && !selectedDistrict.isEmpty()) {
//					messageArea.appendText("To remove '" + selectedDistrict
//							+ "', either click Remove or enter the district in the text field and click Remove.\n");
                    removeField.setText(selectedDistrict);
                }
            }
        });

        backButton.setOnAction(backEvent -> {
            start(removeStage);
        });

        Scene removeScene = new Scene(removeBox, 550, 530);
        removeStage.setScene(removeScene);
        removeStage.show();
    }

    private void insertLocation(Stage insertStage) {
        insertStage.setTitle("Insert Location");

        VBox insertBox = new VBox();
        insertBox.setSpacing(10);
        insertBox.setPadding(new Insets(10));

        TableView<Martyr> locationTableView = new TableView<>();
        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        locationTableView.getColumns().add(locationColumn);

        Label nameLabel = new Label("Enter Name Location:");
        TextField nameField = new TextField();

        Button addButton = new Button("Add");
        Button backButton = new Button("<--Back");

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        insertBox.getChildren().addAll(locationTableView, nameLabel, nameField, addButton, messageArea, backButton);

        // Set items of the TableView to the initial list of locations (data)
        locationTableView.setItems(data);

        addButton.setOnAction(addEvent -> {
            String name = nameField.getText();
            // Check if the input contains only letters or spaces
            if (!name.matches("[a-zA-Z ]+")) {
                messageArea.appendText("Please enter a valid location name (letters and spaces only).\n");
                return;
            }

            name = name.trim(); // Remove leading and trailing spaces

            if (!name.isEmpty() && !data.contains(new Martyr(name, name, name, name, name, name))) {
                locationListTree.addLocation(name);
                Martyr newMartyr = new Martyr(name, name, name, name, name, name);
                // Find the index to insert the new item to maintain sorted order
                int index = 0;
                while (index < data.size() && data.get(index).getName().compareToIgnoreCase(name) < 0) {
                    index++;
                }
                data.add(index, newMartyr);
                nameField.clear();
                messageArea.appendText("Added Successfully sorted, You Added -->" + name + "\n");
            } else {
                messageArea.appendText("Not Added Successfully, Please Enter Name Again or Name Already Exists\n");
                nameField.clear(); // Clear the text field after displaying the error message
            }
        });

        backButton.setOnAction(backEvent -> {
            start(insertStage);
        });

        Scene insertScene = new Scene(insertBox, 450, 500);
        insertStage.setScene(insertScene);
        insertStage.show();
    }

    private void insertDistrict(Stage insertStage) {
        insertStage.setTitle("Insert District");

        VBox insertBox = new VBox();
        insertBox.setSpacing(10);
        insertBox.setPadding(new Insets(10));

        TableView<String> districtTableView = new TableView<>();
        ObservableList<String> districtData = FXCollections.observableArrayList(getUniqueDistricts());
        districtTableView.setItems(districtData);

        TableColumn<String, String> districtColumn = new TableColumn<>("District");
        districtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        districtTableView.getColumns().add(districtColumn);

        Label nameLabel = new Label("Enter Name District:");
        TextField nameField = new TextField();

        Button addButton = new Button("Add");
        Button backButton = new Button("<--Back");

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);

        insertBox.getChildren().addAll(districtTableView, nameLabel, nameField, addButton, messageArea, backButton);

        addButton.setOnAction(addEvent -> {
            String name = nameField.getText();
            if (name != null && !name.isEmpty() && !name.matches(".*\\d.*")) {
                districtListTree.addDistrict(name);
                data.add(new Martyr(name, name, name, name, name, name));
                nameField.clear();
                messageArea.appendText("Added Successfully sorted, You Added -->" + name + "\n");
                districtData.setAll(getUniqueDistricts());
            } else {
                if (name == null || name.isEmpty()) {
                    messageArea.appendText("Not Added Successfully, Please Enter Name Again. Name cannot be empty\n");
                } else {
                    messageArea.appendText("Not Added Successfully, Please Enter a valid Name (letters only)\n");
                }
            }
        });

        backButton.setOnAction(backEvent -> {
            start(insertStage);
        });

        Scene insertScene = new Scene(insertBox, 450, 500);
        insertStage.setScene(insertScene);
        insertStage.show();
    }

    private Set<String> getUniqueDistricts() {
        Set<String> districts = new HashSet<>();
        for (Martyr martyr : data) {
            districts.add(martyr.getDistrict());
        }
        return districts;
    }

    private void readDataFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String name = parts[0];
                    String date = parts[1];
                    String age = parts[2];
                    String location = parts[3];
                    String district = parts[4];
                    String gender = parts[5];
                    data.add(new Martyr(name, date, age, location, district, gender));
                }
            }
        }
    }

    private void setupTable() {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Martyr, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

        TableColumn<Martyr, String> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAge()));

        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));

        TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
        districtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDistrict()));

        TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));

        tableView.getColumns().addAll(nameColumn, dateColumn, ageColumn, locationColumn, districtColumn, genderColumn);
        tableView.setItems(data);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Martyr {
        private final SimpleStringProperty name;
        private final SimpleStringProperty date;
        private final SimpleStringProperty age;
        private final SimpleStringProperty location;
        private final SimpleStringProperty district;
        private final SimpleStringProperty gender;

        public Martyr(String name, String date, String age, String location, String district, String gender) {
            this.name = new SimpleStringProperty(name);
            this.date = new SimpleStringProperty(date);
            this.age = new SimpleStringProperty(age);
            this.location = new SimpleStringProperty(location);
            this.district = new SimpleStringProperty(district);
            this.gender = new SimpleStringProperty(gender);
        }

        public void setLocation(String newLocation) {
            this.location.set(newLocation);
        }

        public String getName() {
            return name.get();
        }

        public String getDate() {
            return date.get();
        }

        public String getAge() {
            return age.get();
        }

        public String getLocation() {
            return location.get();
        }

        public String getDistrict() {
            return district.get();
        }

        public String getGender() {
            return gender.get();
        }

        // Define the locationProperty method
        public SimpleStringProperty locationProperty() {
            return location;
        }
    }

}
