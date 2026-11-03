package pages;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;


public class Questionnaire {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

	private static final Pattern NAME_PATTERN = Pattern.compile("#\\d+\\.\\s*([^\\r\\n]+)");

	private static final Pattern FIRST_CARD_PATTERN = Pattern.compile("#1\\.\\s");

	private static final String CARD_XPATH = "//*[contains(@content-desc, 'Member No:')]";

	private static final String LAST_CARD_BUTTON = "Summary";

	private static final int MAX_SCROLLS = 30;

	public Questionnaire(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

//	public void openMemberQuestionnaire(String patientName) {
//		openMemberSection(patientName, "Questionnaire");
//	}

	public void openMemberVisionAssessment(String patientName) {
		openMemberSection(patientName, "Vision Assessment");
	}

	public List<String> getPatientNames() {
		waitForMembers();
		Set<String> names = readVisiblePatientNames();
		System.out.println("Patient names detected: " + names);
		return new ArrayList<>(names);
	}

	public void openMemberSection(String patientName, String sectionName) {

		if (patientName == null || patientName.trim().isEmpty()) {
			throw new IllegalArgumentException("Patient name cannot be empty.");
		}
		if (sectionName == null || sectionName.trim().isEmpty()) {
			throw new IllegalArgumentException("Section name cannot be empty.");
		}

		String name = patientName.trim();
		String section = sectionName.trim();

		waitForMembers();

		
		if (clickSectionIfCardReady(name, section, false)) {
			System.out.println(section + " clicked for: " + name);
			return;
		}

		
		for (int i = 0; i < MAX_SCROLLS; i++) {

			boolean moved = scrollAndCheckMoved("down");

			if (clickSectionIfCardReady(name, section, !moved)) {
				System.out.println(section + " clicked for: " + name);
				return;
			}

			if (!moved) {
				break;
			}
		}

		for (int i = 0; i < MAX_SCROLLS; i++) {

			if (isTopReached()) {
				break;
			}

			boolean moved = scrollAndCheckMoved("up");

			if (clickSectionIfCardReady(name, section, false)) {
				System.out.println(section + " clicked for: " + name);
				return;
			}

			if (!moved) {
				break;
			}
		}

		if (clickSectionIfCardReady(name, section, true)) {
			System.out.println(section + " clicked for: " + name);
			return;
		}

		throw new NoSuchElementException("Patient ya uska " + section + " button nahi mila: " + name
				+ ". Visible patients: " + readVisiblePatientNames());
	}

	// Click helper

	private boolean clickSectionIfCardReady(String patientName, String sectionName, boolean force) {

		try {
			for (WebElement card : driver.findElements(AppiumBy.xpath(CARD_XPATH))) {

				String cardName = extractName(card.getAttribute("content-desc"));

				if (cardName == null || !cardName.equalsIgnoreCase(patientName)) {
					continue;
				}

				List<WebElement> buttons = card.findElements(AppiumBy.accessibilityId(sectionName));

				if (buttons.isEmpty()) {
					return false;
				}

				WebElement button = buttons.get(0);

				if (!force && !isCardFullyVisible(card, button)) {
					return false;
				}

				if (!button.isDisplayed() || !button.isEnabled()) {
					return false;
				}

				wait.until(ExpectedConditions.elementToBeClickable(button)).click();
				return true;
			}
		} catch (StaleElementReferenceException e) {
			return false;
		}

		return false;
	}

	private boolean isCardFullyVisible(WebElement card, WebElement targetButton) {

		List<WebElement> last = card.findElements(AppiumBy.accessibilityId(LAST_CARD_BUTTON));

		if (last.isEmpty()) {
			return false;
		}

		Rectangle viewport = getViewport();
		int topLimit = viewport.getY();
		int bottomLimit = viewport.getY() + viewport.getHeight();

		List<WebElement> add = driver.findElements(AppiumBy.accessibilityId("Add Member"));
		if (!add.isEmpty()) {
			bottomLimit = Math.min(bottomLimit, add.get(0).getRect().getY());
		}

		Rectangle t = targetButton.getRect();
		Rectangle s = last.get(0).getRect();

		boolean topOk = t.getY() >= topLimit;
		boolean bottomOk = s.getY() + s.getHeight() <= bottomLimit;

		return topOk && bottomOk;
	}

	private Rectangle getViewport() {
		List<WebElement> views = driver.findElements(AppiumBy.className("android.widget.ScrollView"));
		if (!views.isEmpty()) {
			return views.get(0).getRect();
		}
		org.openqa.selenium.Dimension size = driver.manage().window().getSize();
		return new Rectangle(0, 0, size.height, size.width);
	}

	// Patient names helpers

	private Set<String> readVisiblePatientNames() {
		Set<String> names = new LinkedHashSet<>();

		for (WebElement card : driver.findElements(AppiumBy.xpath(CARD_XPATH))) {
			try {
				String name = extractName(card.getAttribute("content-desc"));
				if (name != null) {
					names.add(name);
				}
			} catch (StaleElementReferenceException ignored) {
			}
		}
		return names;
	}

	private String extractName(String desc) {
		if (desc == null) {
			return null;
		}
		Matcher m = NAME_PATTERN.matcher(desc);
		if (m.find()) {
			String name = m.group(1).trim();
			return name.isEmpty() ? null : name;
		}
		return null;
	}

	private void waitForMembers() {
		wait.until(d -> !d.findElements(AppiumBy.xpath(CARD_XPATH)).isEmpty());
	}

	private boolean isTopReached() {
		try {
			for (WebElement card : driver.findElements(AppiumBy.xpath(CARD_XPATH))) {
				String desc = card.getAttribute("content-desc");
				if (desc != null && FIRST_CARD_PATTERN.matcher(desc).find()) {
					return true;
				}
			}
		} catch (StaleElementReferenceException ignored) {
		}
		return false;
	}

	public void scrollToBottom() {
		waitForMembers();

		for (int i = 0; i < MAX_SCROLLS; i++) {
			if (!scrollAndCheckMoved("down")) {
				System.out.println("Member list ke bottom par pahunch gaye.");
				return;
			}
		}
		System.out.println("Maximum scroll limit tak pahunch gaye.");
	}

	private boolean scrollAndCheckMoved(String direction) {

		String before = screenSignature();

		scroll(direction);
		pause(800);

		if (!before.equals(screenSignature())) {
			return true;
		}

		pause(700);
		return !before.equals(screenSignature());
	}

	private String screenSignature() {
		StringBuilder sb = new StringBuilder();
		try {
			for (WebElement card : driver.findElements(AppiumBy.xpath(CARD_XPATH))) {
				sb.append(extractName(card.getAttribute("content-desc"))).append('@').append(card.getRect().getY())
						.append('|');
			}
		} catch (StaleElementReferenceException ignored) {
		}
		return sb.toString();
	}

	private boolean scroll(String direction) {
		try {
			Map<String, Object> args = new HashMap<>();

			List<WebElement> views = driver.findElements(AppiumBy.className("android.widget.ScrollView"));

			if (!views.isEmpty()) {

				Rectangle r = views.get(0).getRect();
				args.put("left", r.getX() + 50);
				args.put("top", r.getY() + (int) (r.getHeight() * 0.10));
				args.put("width", r.getWidth() - 100);
				args.put("height", (int) (r.getHeight() * 0.60));
			} else {
				int h = driver.manage().window().getSize().height;
				int w = driver.manage().window().getSize().width;
				args.put("left", 50);
				args.put("top", (int) (h * 0.2));
				args.put("width", w - 100);
				args.put("height", (int) (h * 0.55));
			}

			args.put("direction", direction);
			args.put("percent", 0.7);

			Object result = driver.executeScript("mobile: scrollGesture", args);
			System.out.println("Scroll " + direction + " result: " + result);

			return Boolean.TRUE.equals(result);

		} catch (Exception e) {
			System.out.println("Scroll fail: " + e.getMessage());
			return false;
		}
	}

	private void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void openPendingQuestionnaire(String patientName) {

	    if (patientName == null || patientName.trim().isEmpty()) {
	        throw new IllegalArgumentException(
	                "Patient name cannot be empty.");
	    }

	    String name = patientName.trim();
	    waitForMembers();

	    // Pehle se visible cards check karo
	    if (clickFirstPendingQuestionnaire(name)) {
	        return;
	    }

	    // Neeche scroll karke remaining cards check karo
	    for (int i = 0; i < MAX_SCROLLS; i++) {

	        boolean moved = scrollAndCheckMoved("down");

	        if (clickFirstPendingQuestionnaire(name)) {
	            return;
	        }

	        if (!moved) {
	            break;
	        }
	    }

	    // List ke top se dobara scan karo, agar zaroorat ho
	    for (int i = 0; i < MAX_SCROLLS; i++) {

	        if (isTopReached()) {
	            break;
	        }

	        boolean moved = scrollAndCheckMoved("up");

	        if (clickFirstPendingQuestionnaire(name)) {
	            return;
	        }

	        if (!moved) {
	            break;
	        }
	    }

	    System.out.println(
	            "Koi pending Questionnaire nahi mila for: " + name);
	}

	private boolean clickFirstPendingQuestionnaire(String patientName) {

	    try {
	        for (WebElement card :
	                driver.findElements(AppiumBy.xpath(CARD_XPATH))) {

	            String desc = card.getAttribute("content-desc");
	            String cardName = extractName(desc);

	            if (cardName == null ||
	                    !cardName.equalsIgnoreCase(patientName)) {
	                continue;
	            }

	            List<WebElement> buttons =
	                    card.findElements(
	                            AppiumBy.accessibilityId("Questionnaire"));

	            if (buttons.isEmpty()) {
	                continue;
	            }

	            WebElement button = buttons.get(0);

	            if (!button.isDisplayed() || !button.isEnabled()) {
	                continue;
	            }

	            // Green means already completed
	            if (isQuestionnaireGreen(button)) {
	                System.out.println(
	                        "Questionnaire already green, skipping: "
	                                + cardName);
	                continue;
	            }

	            // Sirf pending patient par click hoga
	            wait.until(
	                    ExpectedConditions.elementToBeClickable(button))
	                    .click();

	            System.out.println(
	                    "Pending Questionnaire clicked for: " + cardName);

	            return true;
	        }

	    } catch (StaleElementReferenceException e) {
	        return false;
	    }

	    return false;
	}
	
	private boolean isQuestionnaireGreen(WebElement button) {

	    try {
	        Rectangle rect = button.getRect();

	        byte[] screenshot =
	                driver.getScreenshotAs(OutputType.BYTES);

	        BufferedImage image = ImageIO.read(
	                new ByteArrayInputStream(screenshot));

	        if (image == null) {
	            return false;
	        }

	        int x = rect.getX() + rect.getWidth() / 2;
	        int y = rect.getY() + rect.getHeight() / 2;

	        // Screenshot ke andar coordinates validate karo
	        if (x < 0 || y < 0 ||
	                x >= image.getWidth() ||
	                y >= image.getHeight()) {
	            return false;
	        }

	        Color color = new Color(image.getRGB(x, y));

	        float[] hsv = Color.RGBtoHSB(
	                color.getRed(),
	                color.getGreen(),
	                color.getBlue(),
	                null);

	        float hue = hsv[0] * 360;
	        float saturation = hsv[1];
	        float brightness = hsv[2];

	        // Green/teal completed button
	        boolean greenOrTeal =
	                hue >= 80 && hue <= 190
	                && saturation >= 0.25
	                && brightness >= 0.20;

	        System.out.println(
	                "Questionnaire pixel RGB: "
	                        + color.getRed() + ","
	                        + color.getGreen() + ","
	                        + color.getBlue()
	                        + " | Green: " + greenOrTeal);

	        return greenOrTeal;

	    } catch (IOException | StaleElementReferenceException e) {
	        System.out.println(
	                "Questionnaire color check failed: "
	                        + e.getMessage());
	        return false;
	    }
	}
	
	
}