package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.UiTestUtil.CHART_BAR_GAP;
import static seedu.address.ui.UiTestUtil.CHART_CATEGORY_GAP;
import static seedu.address.ui.UiTestUtil.CHART_CREDIT_BAR_INDEX;
import static seedu.address.ui.UiTestUtil.CHART_DEBT_BAR_INDEX;
import static seedu.address.ui.UiTestUtil.CHART_LEGEND_VISIBLE;
import static seedu.address.ui.UiTestUtil.DATE_PATTERN;
import static seedu.address.ui.UiTestUtil.HOME_CARD_AVAILABLE_LIST_FX_CODE;
import static seedu.address.ui.UiTestUtil.HOME_CARD_CHART_FX_CODE;
import static seedu.address.ui.UiTestUtil.HOME_CARD_CONTACT_FX_CODE;
import static seedu.address.ui.UiTestUtil.HOME_CARD_DATE_FX_CODE;
import static seedu.address.ui.UiTestUtil.HOME_CARD_SECOND_FX_CODE;
import static seedu.address.ui.UiTestUtil.HOME_CARD_TIME_FX_CODE;
import static seedu.address.ui.UiTestUtil.SECOND_PATTERN;
import static seedu.address.ui.UiTestUtil.SERIES_SIZE;
import static seedu.address.ui.UiTestUtil.TIME_PATTERN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;

public class HomeCardTest extends ApplicationTest {

    private HomeCard homeCard;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Override
    public void start(Stage stage) {
        homeCard = new HomeCard(model.getFilteredPersonList());
        StackPane root = new StackPane(homeCard.getRoot());
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void execute_timeUpdates_success() {
        // This test assumes the setUpTimeline updates a label to the current time
        String expectedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN));
        String alternateTime = LocalDateTime.now().minusSeconds(1).format(DateTimeFormatter.ofPattern(TIME_PATTERN));
        verifyThat(HOME_CARD_TIME_FX_CODE, (Label label) -> {
            return label.getText().equals(expectedTime) || label.getText().equals(alternateTime);
        });
    }

    @Test
    public void execute_dateUpdates_success() {
        // This test assumes the setUpTimeline updates a label to the current time
        String expectedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        String alternateTime = LocalDateTime.now().minusSeconds(1).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        verifyThat(HOME_CARD_DATE_FX_CODE, (Label label) -> {
            return label.getText().equals(expectedTime) || label.getText().equals(alternateTime);
        });
    }

    @Test
    public void execute_secondsUpdate_success() {
        // This test assumes the setUpTimeline updates a label to the current time
        String expectedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(SECOND_PATTERN));
        String alternateTime = LocalDateTime.now().minusSeconds(1).format(DateTimeFormatter.ofPattern(SECOND_PATTERN));
        verifyThat(HOME_CARD_SECOND_FX_CODE, (Label label) -> {
            return label.getText().equals(expectedTime) || label.getText().equals(alternateTime);
        });
    }

    @Test
    public void execute_moneyChartInitialization_success() {
        // This test ensures the chart is populated with correct data values after initialization
        verifyThat(HOME_CARD_CHART_FX_CODE, (BarChart<String, Number> chart) -> {
            return chart.getData().size() == SERIES_SIZE; // Confirm chart has one data series
        });
    }

    @Test
    public void execute_availableTodayListPopulation_success() {
        // Assumes you want to check if the list correctly displays only those available today
        Day today = Day.valueOf(LocalDate.now().getDayOfWeek().name());
        verifyThat(HOME_CARD_AVAILABLE_LIST_FX_CODE, (ListView<Person> listView) -> {
            return listView.getItems().stream().allMatch(person -> person.getDaysAvailable().contains(today));
        });
    }

    @Test
    public void execute_homeCardInitialization_success() {
        // Verify that contact count label is correctly set
        verifyThat(HOME_CARD_CONTACT_FX_CODE, hasText(Integer.toString(model.getFilteredPersonList().size())));
    }

    @Test
    public void execute_totalDebtComputation_success() {
        double expectedTotalDebt = model.getFilteredPersonList().stream()
                .filter(person -> person.getMoneyOwed().userOwesMoney())
                .mapToDouble(person -> person.getMoneyOwed().getAbsoluteAmount())
                .sum();

        assertEquals(expectedTotalDebt, homeCard.getTotalDebt());
    }

    @Test
    public void execute_totalCreditComputation_success() {
        double expectedTotalCredit = model.getFilteredPersonList().stream()
                .filter(person -> !person.getMoneyOwed().userOwesMoney())
                .mapToDouble(person -> person.getMoneyOwed().getAbsoluteAmount())
                .sum();

        assertEquals(expectedTotalCredit, homeCard.getTotalCredit());
    }

    @Test
    public void execute_chartConfiguration_success() {
        verifyThat(HOME_CARD_CHART_FX_CODE, (BarChart<String, Number> chart) -> {
            return chart.getCategoryGap() == CHART_CATEGORY_GAP
                    && chart.getBarGap() == CHART_BAR_GAP
                    && chart.isLegendVisible() == CHART_LEGEND_VISIBLE;
        });
    }

    @Test
    public void execute_moneyChartValues_success() {
        verifyThat("#chart", (BarChart<String, Number> chart) -> {
            XYChart.Series<String, Number> series = chart.getData().get(0);
            return series.getData().get(CHART_DEBT_BAR_INDEX).getYValue().equals(homeCard.getTotalDebt())
                    && series.getData().get(CHART_CREDIT_BAR_INDEX).getYValue().equals(homeCard.getTotalCredit());
        });
    }
}
