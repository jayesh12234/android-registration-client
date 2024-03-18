package regclient.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import regclient.utils.TestDataReader;

import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

	@AndroidFindBy(uiAutomator = "UiSelector().className(\"android.widget.ImageView\").instance(1)")
	private WebElement mosipLogo;

	@AndroidFindBy(xpath = "//android.widget.EditText")
	private WebElement userNameTextBox;

	@AndroidFindBy(accessibility = "NEXT")
	private WebElement nextButton;

	@AndroidFindBy(xpath = "//android.widget.EditText")
	private WebElement passwordTextBox;

	@AndroidFindBy(accessibility = "LOGIN")
	private WebElement loginButton;

	@AndroidFindBy(accessibility = "Please login to access the features.")
	private WebElement loginMessage;

	@AndroidFindBy(accessibility = "Password")
	private WebElement passwordHeader;

	@AndroidFindBy(accessibility = "Username")
	private WebElement userNameHeader;

	@AndroidFindBy(accessibility = "Welcome to")
	private WebElement welcomeMessageEnglish;
	
	@AndroidFindBy(accessibility = "Bienvenue à")
	private WebElement welcomeMessageFrench;
	
	@AndroidFindBy(accessibility = "مرحبا بكم في")
	private WebElement welcomeMessageArabic;
	
	@AndroidFindBy(accessibility = "ಇದಕ್ಕೆ ಸ್ವಾಗತ")
	private WebElement welcomeMessageKannada;
	
	@AndroidFindBy(accessibility = "आपका स्वागत है")
	private WebElement welcomeMessageHindi;
	
	@AndroidFindBy(accessibility = "உங்களை வரவேற்கிறோம்")
	private WebElement welcomeMessageTamil;

	@AndroidFindBy(accessibility = "HELP")
	private WebElement helpButton;

	@AndroidFindBy(accessibility = "BACK")
	private WebElement backButton;

	@AndroidFindBy(accessibility = "Forgot Password?")
	private WebElement forgetPasswordButton;

	@AndroidFindBy(accessibility = "User not found!")
	private WebElement userNotFoundErrorMessage;

	@AndroidFindBy(accessibility = "Password incorrect!")
	private WebElement passwordIncorrectErrorMessage;

	@AndroidFindBy(accessibility = "English")
	private WebElement englishButton;

	@AndroidFindBy(accessibility = "French")
	private WebElement frenchButton;

	@AndroidFindBy(accessibility = "Arabic")
	private WebElement arabicButton;

	@AndroidFindBy(accessibility = "ಕನ್ನಡ")
	private WebElement kannadaButton;

	@AndroidFindBy(accessibility = "हिन्दी")
	private WebElement hindiButton;

	@AndroidFindBy(accessibility = "தமிழ்")
	private WebElement tamilButton;

	@AndroidFindBy(accessibility = "spanish")
	private WebElement spanishButton;

	public LoginPage(AppiumDriver driver) {
		super(driver);
	}


	public  void enterUserName(String username) {
		clickAndsendKeysToTextBox(userNameTextBox,username);
	}

	public  void clickOnNextButton() {
		clickOnElement(nextButton);
	}

	public  void clickOnBackButton() {
		clickOnElement(backButton);
	}

	public  void enterPassword(String password) {
		clickAndsendKeysToTextBox(passwordTextBox,password);
	}

	public  RegistrationTasksPage clickOnloginButton() {
		clickOnElement(loginButton);
		return new RegistrationTasksPage(driver);
	}

	public  boolean isNextButtonEnabled() {
		return isElementEnabled(nextButton);
	}

	public  boolean isLoginButtonEnabled() {
		return isElementEnabled(loginButton);
	}

	public boolean isLoginPageLoaded() {
		return isElementDisplayed(loginMessage);
	}

	public boolean isPasswordHeaderDisplayed() {
		return isElementDisplayed(passwordHeader);
	}

	public boolean isUserNameHeaderDisplayed() {
		return isElementDisplayed(userNameHeader);
	}

	public boolean isWelcomeMessageDisplayed() {
		return isElementDisplayed(welcomeMessageEnglish);
	}
	
	public boolean isWelcomeMessageInSelectedLanguageDisplayed() {
	    String language = TestDataReader.readData("language");
	    switch (language) {
	        case "eng":
	            return isElementDisplayed(welcomeMessageEnglish);
	        case "fra":
	            return isElementDisplayed(welcomeMessageFrench);
	        case "ara":
	            return isElementDisplayed(welcomeMessageArabic);
	        case "kan":
	            return isElementDisplayed(welcomeMessageKannada);
	        case "hin":
	            return isElementDisplayed(welcomeMessageHindi);
	        case "tam":
	            return isElementDisplayed(welcomeMessageTamil);
//	        case "spa":
//	            return isElementDisplayed(welcomeMessageSpanish);
	        default:
	            return false; // Handle unsupported languages
	    }
	}

	public boolean isHelpButtonDisplayed() {
		return isElementDisplayed(helpButton);
	}

	public boolean isBackButtonDisplayed() {
		return isElementDisplayed(backButton);
	}

	public boolean isForgetOptionDisplayed() {
		return isElementDisplayed(forgetPasswordButton);
	}

	public boolean isUserNotFoundErrorMessageDisplayed() {
		return isElementDisplayed(userNotFoundErrorMessage);
	}

	public boolean isPasswordIncorrectErrorMessageDisplayed() {
		return isElementDisplayed(passwordIncorrectErrorMessage);
	}

	public boolean isMosipLogoDisplayed() {
		return isElementDisplayed(mosipLogo);
	}

	public void selectLanguage(String language) {
	    switch (language) {
	        case "eng":
	            clickOnElement(englishButton);
	            break;
	        case "fra":
	            clickOnElement(frenchButton);
	            break;
	        case "ara":
	            clickOnElement(arabicButton);
	            break;
	        case "kan":
	            clickOnElement(kannadaButton);
	            break;
	        case "hin":
	            clickOnElement(hindiButton);
	            break;
	        case "tam":
	            clickOnElement(tamilButton);
	            break;
	        case "spa":
	            clickOnElement(spanishButton);
	            break;
	        default:
	            clickOnElement(englishButton);
	            break;
	    }
	}


}
