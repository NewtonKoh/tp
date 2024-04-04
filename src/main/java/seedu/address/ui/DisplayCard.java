package seedu.address.ui;

import java.util.Comparator;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.AnimationUtil;
import seedu.address.model.person.Person;

/**
 * Displays a person's information
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayCard.fxml";
    private static final int IMAGE_SIZE = 30;

    public final Person person;
    private final Image tagIconImage = new Image(
            this.getClass().getResourceAsStream("/images/tag_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image dayIconImage = new Image(
            this.getClass().getResourceAsStream("/images/day_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image phoneIconImage = new Image(
            this.getClass().getResourceAsStream("/images/phone_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image addressIconImage = new Image(
            this.getClass().getResourceAsStream("/images/address_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image emailIconImage = new Image(
            this.getClass().getResourceAsStream("/images/email_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image birthdayIconImage = new Image(
            this.getClass().getResourceAsStream("/images/birthday_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image moneyIconImage = new Image(
            this.getClass().getResourceAsStream("/images/money_icon.png"),
            IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final TranslateTransition moveTransition = AnimationUtil.getMoveTransition(getRoot());
    private final TranslateTransition bounceBackTransition = AnimationUtil.getBounceBackTransition(getRoot());
    private final FadeTransition fadeInTransition = AnimationUtil.getFadeInTransition(getRoot());
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane daysAvailable;
    @FXML
    private Label birthday;
    @FXML
    private Label remark;
    @FXML
    private Label moneyOwed;
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

    /**
     * @param person Person information to be displayed on the card
     */
    public DisplayCard(Person person) {
        super(FXML);

        this.person = person;
        setUpLabels(person);
        setUpIcons();
        playAnimation();
    }

    private void setUpLabels(Person person) {
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
    }

    private void setUpIcons() {
        tagIcon.setImage(tagIconImage);
        dayIcon.setImage(dayIconImage);
        phoneIcon.setImage(phoneIconImage);
        addressIcon.setImage(addressIconImage);
        emailIcon.setImage(emailIconImage);
        birthdayIcon.setImage(birthdayIconImage);
        moneyIcon.setImage(moneyIconImage);
    }

    /**
     * Plays the card's animations
     */
    public void playAnimation() {
        fadeInTransition.playFromStart();
        moveTransition.playFromStart();
        bounceBackTransition.playFromStart();
    }
}
