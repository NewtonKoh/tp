package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class HomeCard extends UiPart<Region> {

    private static final String FXML = "HomeCard.fxml";
    private static final String CONTACT_TOP = "There are currently";
    private static final String CONTACT_BOTTOM = "contacts on your FriendFolio";
    private static final String MONEY_OWED_LABEL = "Money you owe";
    private static final String OWED_MONEY_LABEL = "Money owed to you";
    private static final String CHART_LABEL = "Financial Status";
    private static final String AVAILABLE_LABEL = "Available Today";
    private static final double CATEGORY_GAP = 120;
    private static final double BAR_GAP = 0;
    private static final double MOVE_DURATION = 200;
    private static final double MOVE_INITIAL = 500;
    private static final double MOVE_POP = 20;
    private static final double FADE_DURATION = 300;
    private static final double FADE_INITIAL = 0;
    private static final double FADE_ULTIMATE = 1;
    private ObservableList<Person> personList;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label time;
    @FXML
    private Label second;
    @FXML
    private Label date;
    @FXML
    private Label contactTop;
    @FXML
    private Label contactAmount;
    @FXML
    private Label contactBottom;
    @FXML
    private Label chartLabel;
    @FXML
    private Label availableToday;
    @FXML
    private BarChart<String, Number> chart;
    @FXML
    private ListView<Person> availableList;
    private TranslateTransition moveTransition;
    private TranslateTransition moveBackTransition;
    private FadeTransition fadeInTransition;

    /**
     * Creates a {@code HomeCard} with the given Person List.
     */
    public HomeCard(ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter secondFormatter = DateTimeFormatter.ofPattern(":ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");

        time.setText(LocalDateTime.now().format(timeFormatter));
        second.setText(LocalDateTime.now().format(secondFormatter));
        date.setText(LocalDateTime.now().format(dateFormatter));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime currentTime = LocalDateTime.now();
            time.setText(currentTime.format(timeFormatter));
            second.setText(currentTime.format(secondFormatter));
            date.setText(currentTime.format(dateFormatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        contactTop.setText(CONTACT_TOP);
        contactBottom.setText(CONTACT_BOTTOM);
        contactAmount.setText(getContactAmount().toString());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(MONEY_OWED_LABEL, getTotalDebt())); // Replace 1500 with actual value
        series.getData().add(new XYChart.Data<>(OWED_MONEY_LABEL, getTotalCredit())); // Replace 2000 with actual value
        chart.getData().add(series);
        chart.setCategoryGap(CATEGORY_GAP);
        chart.setBarGap(BAR_GAP);
        chart.setLegendVisible(false);
        chartLabel.setText(CHART_LABEL);
        availableToday.setText(AVAILABLE_LABEL);
        setUpList();
        setUpAnimation();
    }

    /**
     * Sets up listView elements.
     */
    private void setUpList() {
        availableList.setItems(getAvailableTodayList());
        availableList.setCellFactory(listView -> new AvailableTodayCell());
        availableList.setFocusTraversable(false);
        availableList.setMouseTransparent(true);
    }

    private void setUpAnimation() {
        moveTransition = new TranslateTransition(Duration.millis(MOVE_DURATION), this.getRoot());
        double originalPosition = this.getRoot().getTranslateX();
        moveTransition.setFromX(originalPosition + MOVE_INITIAL);
        moveTransition.setToX(originalPosition - MOVE_POP);
        moveBackTransition = new TranslateTransition(Duration.millis(MOVE_DURATION), this.getRoot());
        moveBackTransition.setFromX(originalPosition - MOVE_POP);
        moveBackTransition.setToX(originalPosition);
        moveBackTransition.setDelay(Duration.millis(MOVE_DURATION));
        fadeInTransition = new FadeTransition(Duration.millis(FADE_DURATION), this.getRoot());
        fadeInTransition.setFromValue(FADE_INITIAL);
        fadeInTransition.setToValue(FADE_ULTIMATE);
        fadeInTransition.playFromStart();
        moveTransition.playFromStart();
        moveBackTransition.playFromStart();
    }


    /**
     * @return List of people who are available today.
     */
    public ObservableList<Person> getAvailableTodayList() {

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        String today = LocalDateTime.now().format(dayFormatter);
        Day filterDay = Day.getDay(today);
        final Day effectiveFilterDay = filterDay;
        return personList.filtered(person -> person.getDaysAvailable().contains(effectiveFilterDay));
    }

    /**
     * @return Get the amount of contacts in FriendFolio
     */
    public Integer getContactAmount() {
        return personList.size();
    }

    /**
     * @return Get the amount of debt the user has
     */
    public double getTotalDebt() {

        double result = 0.0;
        for (Person x : personList) {
            if (x.getMoneyOwed().getAmount() > 0) {
                result += x.getMoneyOwed().getAmount();
            }
        }

        return result;
    }

    /**
     * @return Get the amount of credit the user has
     */
    public double getTotalCredit() {

        double result = 0.0;
        for (Person x : personList) {
            if (x.getMoneyOwed().getAmount() < 0) {
                result += -x.getMoneyOwed().getAmount();
            }
        }

        return result;
    }

    public HBox getCardPane() {
        return cardPane;
    }

    class AvailableTodayCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            MiniCard personCard = new MiniCard(person);
            setGraphic(personCard.getRoot());
        }
    }
}
