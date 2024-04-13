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
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.util.AnimationUtil;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class HomeCard extends UiPart<Region> {

    private static final String FXML = "HomeCard.fxml";
    private static final String MONEY_OWED_LABEL = "Money you owe";
    private static final String OWED_MONEY_LABEL = "Money owed to you";
    private static final double CATEGORY_GAP = 120;
    private static final double BAR_GAP = 0;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter secondFormatter = DateTimeFormatter.ofPattern(":ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
    private final ObservableList<Person> personList;
    private final TranslateTransition moveTransition = AnimationUtil.getMoveTransition(getRoot());
    private final TranslateTransition bounceBackTransition = AnimationUtil.getBounceBackTransition(getRoot());
    private final FadeTransition fadeInTransition = AnimationUtil.getFadeInTransition(getRoot());

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label time;
    @FXML
    private Label second;
    @FXML
    private Label date;
    @FXML
    private Label contactCount;
    @FXML
    private BarChart<String, Number> chart;
    @FXML
    private ListView<Person> availableList;

    /**
     * Creates a {@code HomeCard} with the given Person List.
     */
    public HomeCard(ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;
        this.contactCount.setText(String.valueOf(personList.size()));
        setUpTimeline();
        setUpMoneyChart();
        setUpAvailableTodayList();
        playAnimation();
    }

    private void setUpMoneyChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(MONEY_OWED_LABEL, getTotalDebt()));
        series.getData().add(new XYChart.Data<>(OWED_MONEY_LABEL, getTotalCredit()));
        chart.getData().add(series);
        chart.setCategoryGap(CATEGORY_GAP);
        chart.setBarGap(BAR_GAP);
        chart.setLegendVisible(false);
    }

    /**
     * Sets up a 1-second interval to update the time card.
     */
    private void setUpTimeline() {
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
    }

    /**
     * Sets up list of people who are available today.
     */
    private void setUpAvailableTodayList() {
        availableList.setItems(getAvailableTodayList());
        availableList.setCellFactory(listView -> new AvailableTodayCell());
    }

    /**
     * @return List of people who are available today.
     */
    public ObservableList<Person> getAvailableTodayList() {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        String today = LocalDateTime.now().format(dayFormatter);
        Day filterDay = Day.getDay(today);
        return personList.filtered(person -> person.getDaysAvailable().contains(filterDay));
    }

    /**
     * @return Get the amount of debt the user has
     */
    public double getTotalDebt() {
        double result = 0.0;
        for (Person person : personList) {
            if (person.getMoneyOwed().userOwesMoney()) {
                result += person.getMoneyOwed().getAbsoluteAmount();
            }
        }
        return result;
    }

    /**
     * @return Get the amount of credit the user has
     */
    public double getTotalCredit() {
        double result = 0.0;
        for (Person person : personList) {
            if (!person.getMoneyOwed().userOwesMoney()) {
                result += person.getMoneyOwed().getAbsoluteAmount();
            }
        }
        return result;
    }

    /**
     * Play the card's animation
     */
    public void playAnimation() {
        fadeInTransition.playFromStart();
        moveTransition.playFromStart();
        bounceBackTransition.playFromStart();
    }


    static class AvailableTodayCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            MiniPersonCard personCard = new MiniPersonCard(person);
            setGraphic(personCard.getRoot());
        }
    }
}
