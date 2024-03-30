package seedu.address.ui;

import java.util.Comparator;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.model.person.Person;

/**
 * Displays a person's information
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayCard.fxml";
    private static final String TAG_LABEL = "Tags: ";
    private static final String DAYS_LABEL = "Days Available: ";
    private static final String PHONE_LABEL = "Phone Number: ";
    private static final String ADDRESS_LABEL = "Address: ";
    private static final String EMAIL_LABEL = "Email: ";
    private static final String REMARK_LABEL = "Remarks: ";
    private static final String BIRTHDAY_LABEL = "Birthday: ";
    private static final String MONEY_LABEL = "Money Owed: ";
    private static final int IMAGE_SIZE = 30;

    private static final double MOVE_DURATION = 200;
    private static final double MOVE_INITIAL = 500;
    private static final double MOVE_POP = 20;
    private static final double FADE_DURATION = 300;
    private static final double FADE_INITIAL = 0;
    private static final double FADE_ULTIMATE = 1;
    public final Person person;
    private Image tagIconImage = new Image(this.getClass().getResourceAsStream("/images/tag_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private Image dayIconImage = new Image(this.getClass().getResourceAsStream("/images/day_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private Image phoneIconImage = new Image(this.getClass().getResourceAsStream("/images/phone_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private Image addressIconImage = new Image(this.getClass().getResourceAsStream("/images/address_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private Image emailIconImage = new Image(this.getClass().getResourceAsStream("/images/email_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private Image birthdayIconImage = new Image(this.getClass().getResourceAsStream("/images/birthday_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private Image moneyIconImage = new Image(this.getClass().getResourceAsStream("/images/money_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);

    @FXML
    private HBox displayPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label address;
    @FXML
    private Label addressLabel;
    @FXML
    private Label email;
    @FXML
    private Label emailLabel;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane daysAvailable;
    @FXML
    private Label tagLabel;
    @FXML
    private Label dayLabel;
    @FXML
    private Label birthday;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label remark;
    @FXML
    private Label remarkLabel;
    @FXML
    private Label moneyOwed;
    @FXML
    private Label moneyLabel;
    @FXML
    private ImageView tagIcon;
    @FXML
    private ImageView dayIcon;
    @FXML
    private ImageView phoneIcon;
    @FXML
    private ImageView addressIcon;
    @FXML
    private ImageView emailIcon;
    @FXML
    private ImageView birthdayIcon;
    @FXML
    private ImageView moneyIcon;
    private TranslateTransition moveTransition;
    private TranslateTransition moveBackTransition;
    private FadeTransition fadeInTransition;

    /**
     * @param person Person information to be displayed on the card
     */
    public DisplayCard(Person person) {
        super(FXML);

        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        remark.setText(person.getRemark().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getDaysAvailable().stream()
                .sorted(Comparator.comparing(Enum::ordinal))
                .forEach(day -> daysAvailable.getChildren().add(new Label(day.getShortForm())));
        birthday.setText(person.getBirthday().toString());
        moneyOwed.setText(person.getMoneyOwed().getMessage());
        tagLabel.setText(TAG_LABEL);
        dayLabel.setText(DAYS_LABEL);
        phoneLabel.setText(PHONE_LABEL);
        addressLabel.setText(ADDRESS_LABEL);
        emailLabel.setText(EMAIL_LABEL);
        remarkLabel.setText(REMARK_LABEL);
        birthdayLabel.setText(BIRTHDAY_LABEL);
        moneyLabel.setText(MONEY_LABEL);
        tagIcon.setImage(tagIconImage);
        dayIcon.setImage(dayIconImage);
        phoneIcon.setImage(phoneIconImage);
        addressIcon.setImage(addressIconImage);
        emailIcon.setImage(emailIconImage);
        birthdayIcon.setImage(birthdayIconImage);
        moneyIcon.setImage(moneyIconImage);
        setUpAnimation();
    }

    public void setUpAnimation() {
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

    public HBox getDisplayPane() {
        return displayPane;
    }
}
