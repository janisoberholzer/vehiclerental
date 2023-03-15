package com.cs.springbootjavafx.controller;


import com.cs.springbootjavafx.entity.Rental;
import com.cs.springbootjavafx.entity.User;
import com.cs.springbootjavafx.entity.Vehicle;
import com.cs.springbootjavafx.repository.*;
import com.cs.springbootjavafx.util.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MainController {


    //Default variables do not change
    private Stage stage;
    private final Helper helper;

    Boolean ispicks = false;
    Boolean isdrops = false;
    Boolean islocs = false;
    Boolean iscats = false;

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    UserRepository userRepository;


    User user;
    @FXML
    private ChoiceBox locd;

    @FXML
    private ChoiceBox catd;
    @FXML
    private DatePicker pickd;
    @FXML
    private DatePicker dropd;
    @FXML
    private TableView<Vehicle> mtab;
    @FXML
    private TableColumn<Vehicle, String> cold;
    @FXML
    private TableColumn<Vehicle, Integer> coly;
    @FXML
    private TableColumn<Vehicle, String> coll;
    @FXML
    private TableColumn<Vehicle, String> colc;

    private ObservableList<Vehicle> tableList;
    @FXML
    private ListView<String> mplist;

    @FXML
    private TableView<Rental> ltab;
    @FXML
    private TableColumn<Rental, Instant> lda;
    @FXML
    private TableColumn<Rental, String> lde;
    @FXML
    private TableColumn<Rental, BigDecimal> lpr;
    private ObservableList<Rental> ltableList;

    @FXML
    private TableView<Rental> ftab;
    @FXML
    private TableColumn<Rental, Instant> fda;
    @FXML
    private TableColumn<Rental, Integer> fde;
    @FXML
    private TableColumn<Rental, BigDecimal> fpr;
    @FXML
    private TableColumn<Rental, String> fre;
    private ObservableList<Rental> ftableList;
    @FXML
    private Label lprice;
    @FXML
    private Label fprice;


    public MainController(Helper helper) {
        this.helper = helper;
    }


    //Button action methods
    @FXML
    private void demoBtn(ActionEvent event) {
        //Load new content to existing window
        this.stage = helper.changeStage(this.getClass().getClassLoader().getResource("views/Test2.fxml"), event, this);
        this.stage.show();
    }

    private void rentedlast30Days(){
        List<Rental> rentalsFromUser = rentalRepository.findAllByUser(user);
        List<Rental> rentalsOfLast30Days = new ArrayList<>();
        for (Rental rental : rentalsFromUser) {
            if (rental.getPickuptime().isAfter(Instant.now().minus(30, ChronoUnit.DAYS)) && !rental.getPickuptime().isAfter(Instant.now()))
                rentalsOfLast30Days.add(rental);
        }
        lda.setCellValueFactory(new PropertyValueFactory<Rental, Instant>("pickuptime"));
        lde.setCellValueFactory(new PropertyValueFactory<Rental, String>("vehicle"));
        lpr.setCellValueFactory(new PropertyValueFactory<Rental, BigDecimal>("price"));
        AtomicInteger total = new AtomicInteger();
        rentalsOfLast30Days.stream().map(rental -> total.addAndGet(rental.getPrice().intValue()));
        ltableList = FXCollections.observableArrayList(rentalsOfLast30Days);
        ltab.setItems(ltableList);
        lprice.setText("CHF " + total);
    }

    //@FXML
    //private void newwindow(ActionEvent event) {
    //    view2Controller.showStage();
    //}
    @FXML
    public void initialize() {
        loadDropDowns();
        List<Vehicle> pvehicles = rentalRepository.findPopular(1);


        ObservableList<String> observableListvehicle = FXCollections.observableArrayList(pvehicles.stream().map(vehicle -> (vehicle.getDescription() + ", " + vehicle.getYear().toString())).toList());
        mplist.setItems(observableListvehicle);
        rentedlast30Days();
    }

    private void loadDropDowns() {
        ObservableList<String> observableListlocation = FXCollections.observableArrayList(locationRepository.findAll().stream().map(location -> (location.getAddress())).toList());
        locd.setItems(observableListlocation);

        ObservableList<String> observableListcategory = FXCollections.observableArrayList(categoryRepository.findAll().stream().map(category -> category.getName()).toList());
        catd.setItems(observableListcategory);
    }

    private void showCars() {
        if (((iscats && islocs) && isdrops) && ispicks) {
            try {
                List<Vehicle> vehicles = vehicleRepository.findAllByCategoryAndLocation(categoryRepository.findCategoryByName(catd.getSelectionModel().getSelectedItem().toString()), locationRepository.findLocationByAddress(locd.getSelectionModel().getSelectedItem().toString()));
                cold.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("description"));
                coly.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("year"));
                colc.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("category"));
                coll.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("location"));
                List<Rental> rentalsOfAvailableCars = new ArrayList<>();
                for (Vehicle vehicle : vehicles) {
                    rentalsOfAvailableCars.addAll(rentalRepository.findAllByVehicle(vehicle));
                    for (Rental rental : rentalsOfAvailableCars) {
                        if ((pickd.getValue().atStartOfDay().toInstant(ZoneOffset.UTC).isBefore(rental.getPickuptime()) && dropd.getValue().atStartOfDay().toInstant(ZoneOffset.UTC).isBefore(rental.getPickuptime())) || (pickd.getValue().atStartOfDay().toInstant(ZoneOffset.UTC).isAfter(rental.getDropofftime()))) {
                            continue;
                        } else {
                            if (vehicles.contains(vehicle)) vehicles.remove(vehicle);
                        }
                    }
                    if (vehicles.isEmpty()) break;
                }

                tableList = FXCollections.observableArrayList(vehicles);
                mtab.setItems(tableList);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }


    @FXML
    private void pickds(ActionEvent event) {
        ispicks = true;
        showCars();
    }

    @FXML
    private void locds(ActionEvent event) {
        islocs = true;
        showCars();
    }

    @FXML
    private void dropds(ActionEvent event) {
        isdrops = true;
        showCars();
    }

    @FXML
    private void catds(ActionEvent event) {
        iscats = true;
        showCars();
    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    @FXML
    private void rent(ActionEvent event) {
        if (mtab.getSelectionModel().getSelectedItem() == null) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please select an available car");
        }else {
            Vehicle vehicle = mtab.getSelectionModel().getSelectedItem();
            System.out.println(user.getId());
            Rental rental = new Rental(LocalDateTime.now().toInstant(ZoneOffset.UTC), pickd.getValue().atStartOfDay().toInstant(ZoneOffset.UTC), dropd.getValue().atStartOfDay().toInstant(ZoneOffset.UTC), (vehicle.getCategoryObject().getChangerate()), user, vehicle);
            System.out.println(rental);
            rentalRepository.save(rental);
            showAlert(Alert.AlertType.INFORMATION, this.stage.getOwner(), "Form Success!", "Successfully rented");
            showCars();
            // check later
            rentedlast30Days();
        }


    }

    //Show new window of this scene
    public void showStage() {
        this.stage = helper.getStage(this.getClass().getClassLoader().getResource("views/Main.fxml"), "WindowName", this);
        this.stage.show();
    }
}
