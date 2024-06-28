package regclient.pages.hindi;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import regclient.page.BiometricDetailsPage;
import regclient.page.CameraPage;
import regclient.page.DocumentUploadPage;

public class DocumentUploadPageHindi extends DocumentUploadPage{

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().description(\"दस्तावेज़ अपलोड\"))")
	private WebElement doccumentUploadPage;

	@AndroidFindBy(uiAutomator = "UiSelector().className(\"android.widget.Button\")")
	private WebElement identityProofScanButton;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"निवास प्रमाण पत्र\")]/parent::android.view.View/parent::android.view.View")
	private WebElement addressProofSelectValue;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"पहचान प्रमाण\")]/parent::android.view.View/parent::android.view.View")
	private WebElement identityProofSelectValue;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"संबंध प्रमाण\")]/parent::android.view.View/parent::android.view.View")
	private WebElement relationshipProofSelectValue;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"जन्मतिथि प्रमाण\")]/parent::android.view.View/parent::android.view.View")
	private WebElement dobProofSelectValue;

	@AndroidFindBy(accessibility = "स्क्रिम")
	private WebElement PopUpCloseButton;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"निवास प्रमाण पत्र\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.widget.Button")
	private WebElement scanButtonAddressProof;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"पहचान प्रमाण\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.widget.Button")
	private WebElement scanButtonIdentityProof;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"जन्मतिथि प्रमाण\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.widget.Button")
	private WebElement scanButtonDobProof;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"संबंध प्रमाण\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.widget.Button")
	private WebElement scanButtonRelationshipProof;

	@AndroidFindBy(accessibility = "वापस जाएं")
	private WebElement backButton;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"निवास प्रमाण पत्र\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.view.View/descendant::android.widget.ImageView")
	private WebElement previewCaptureImage;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"निवास प्रमाण पत्र\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.view.View/descendant::android.widget.ImageView[2]")
	private WebElement previewSecondCaptureImage;

	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"निवास प्रमाण पत्र\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.view.View/descendant::android.widget.ImageView[3]")
	private WebElement previewThirdCaptureImage;

	@AndroidFindBy(accessibility = "जारी रखें")
	private WebElement continueButton;

	@AndroidFindBy(accessibility = "मिटाना")
	private WebElement deleteButton;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().descriptionContains(\"जन्मतिथि प्रमाण\"))")
	private WebElement dobsHeader;

	@AndroidFindBy(xpath = "//android.widget.ImageView")
	private WebElement captureImage;
	
	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"निवास प्रमाण पत्र\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.widget.EditText")
	private WebElement addressProofReferenceNumberTextbox;
	
	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"पहचान प्रमाण\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.widget.EditText")
	private WebElement identityProofReferenceNumberTextbox;
	
	@AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"जन्मतिथि प्रमाण\")]/parent::android.view.View/parent::android.view.View/following-sibling::android.widget.EditText")
	private WebElement dobProofReferenceNumberTextbox;


	public DocumentUploadPageHindi(AppiumDriver driver) {
		super(driver);
	}

	public void selectAddressProof() {
		while(!isElementDisplayedOnScreen(addressProofSelectValue) || !isElementDisplayedOnScreen(scanButtonAddressProof)) {
			swipeOrScroll();
		}
		clickOnElement(addressProofSelectValue);
		if(!isElementDisplayedOnScreen(PopUpCloseButton)) {
			swipeOrScroll();
			clickOnElement(addressProofSelectValue);	
		}
	}

	public void selectIdentityProof() {
		while(!isElementDisplayedOnScreen(identityProofSelectValue)|| !isElementDisplayedOnScreen(scanButtonIdentityProof) ) {
			swipeOrScroll();
		}
		clickOnElement(identityProofSelectValue);
		if(!isElementDisplayedOnScreen(PopUpCloseButton)) {
			swipeOrScroll();
			clickOnElement(identityProofSelectValue);	
		}
	}

	public void selectDobProof() {
		while(!isElementDisplayedOnScreen(dobProofSelectValue) || !isElementDisplayedOnScreen(scanButtonDobProof)) {
			swipeOrScroll();
		}
		clickOnElement(dobProofSelectValue);
		if(!isElementDisplayedOnScreen(PopUpCloseButton)) {
			swipeOrScroll();
			clickOnElement(dobProofSelectValue);	
		}
	}

	public void selectRelationshipProof() {
		while(!isElementDisplayedOnScreen(relationshipProofSelectValue) || !isElementDisplayedOnScreen(scanButtonRelationshipProof)) {
			swipeOrScroll();
		}
		clickOnElement(relationshipProofSelectValue);
		if(!isElementDisplayedOnScreen(PopUpCloseButton)) {
			swipeOrScroll();
			clickOnElement(relationshipProofSelectValue);	
		}
	}

	public void selectOnCaptureImage() {
		clickOnElement(previewCaptureImage);
	}

	public void closePopUpClose() {
		clickOnElement(PopUpCloseButton);
	}

	public CameraPage clickOnAddressProofScanButton() {
		if(!isElementDisplayedOnScreen(scanButtonAddressProof)) {
			swipeOrScroll();
		}
		clickOnElement(scanButtonAddressProof);
		return new CameraPage(driver);
	}

	public CameraPage clickOnScanButtonDobProof() {
		if(!isElementDisplayedOnScreen(scanButtonDobProof)) {
			swipeOrScroll();
		}
		clickOnElement(scanButtonDobProof);
		return new CameraPage(driver);
	}

	public CameraPage clickOnScanButtonIdentityProof() {
		if(!isElementDisplayedOnScreen(scanButtonIdentityProof)) {
			swipeOrScroll();
		}
		clickOnElement(scanButtonIdentityProof);
		return new CameraPage(driver);
	}

	public CameraPage clickOnScanButtonRelationshipProof() {
		if(!isElementDisplayedOnScreen(scanButtonRelationshipProof)) {
			swipeOrScroll();
		}
		clickOnElement(scanButtonRelationshipProof);
		return new CameraPage(driver);
	}

	public void clickOnBackButton() {
		clickOnElement(backButton);

	}

	public void clickOnDeleteButton() {
		clickOnElement(deleteButton);

	}

	public  BiometricDetailsPage clickOnContinueButton() {
		clickOnElement(continueButton);
		return new BiometricDetailsPageHindi(driver);
	}

	public  boolean isImageDisplyed() {
		waitTime(1);
		return isElementDisplayed(captureImage);
	}

	public  boolean isSecondImageDisplyed() {
		waitTime(1);
		return isElementDisplayed(previewSecondCaptureImage);
	}

	public  boolean isThirdImageDisplyed() {
		waitTime(1);
		return isElementDisplayed(previewThirdCaptureImage);
	}

	public  boolean isDeleteButtonDisplyed() {
		if(!isElementDisplayedOnScreen(deleteButton)) {
			swipeOrScroll();
		}
		return isElementDisplayed(deleteButton);
	}

	public boolean isDoccumentUploadPageDisplayed() {
		return isElementDisplayed(doccumentUploadPage);
	}

	public boolean isDobHeaderDisplayed() {
		return isElementDisplayed(dobsHeader);
	}

	public  boolean isScanButtonAddressProofEnabled() {
		if(!isElementDisplayedOnScreen(scanButtonAddressProof)) {
			swipeOrScroll();
		}
		return isElementEnabled(scanButtonAddressProof);
	}

	public  boolean isScanButtonIdentityProofEnabled() {
		if(!isElementDisplayedOnScreen(scanButtonIdentityProof)) {
			swipeOrScroll();
		}
		return isElementEnabled(scanButtonIdentityProof);
	}

	public  boolean isScanButtonDobProofEnabled() {
		if(!isElementDisplayedOnScreen(scanButtonDobProof)) {
			swipeOrScroll();
		}
		return isElementEnabled(scanButtonDobProof);
	}

	public  boolean isScanButtonRelationshipProoffEnabled() {
		if(!isElementDisplayedOnScreen(scanButtonRelationshipProof)) {
			swipeOrScroll();
		}
		return isElementEnabled(scanButtonRelationshipProof);
	}
	
	public  void enterReferenceNumberInAdressProof() {
		if(!isElementDisplayedOnScreen(addressProofReferenceNumberTextbox)) {
			swipeOrScroll();
			clickAndsendKeysToTextBox(addressProofReferenceNumberTextbox,"1234567890");
		}else
		clickAndsendKeysToTextBox(addressProofReferenceNumberTextbox,"1234567890");
	}
	
	public  void enterReferenceNumberInIdentityProof() {
		while(!isElementDisplayedOnScreen(identityProofReferenceNumberTextbox)) {
			swipeOrScroll();
		}
		clickAndsendKeysToTextBox(identityProofReferenceNumberTextbox,"1234567890");
	}
	
	public  void enterReferenceNumberInDobProof() {
		while(!isElementDisplayedOnScreen(dobProofReferenceNumberTextbox)) {
			swipeOrScroll();
		}
		clickAndsendKeysToTextBox(dobProofReferenceNumberTextbox,"1234567890");
	}

}
