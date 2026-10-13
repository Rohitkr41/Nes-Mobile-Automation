//
//package pages;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import org.openqa.selenium.WebElement;
//import io.appium.java_client.AppiumBy;
//
//import io.appium.java_client.AppiumBy;
//import io.appium.java_client.android.AndroidDriver;
//
//public class Questionnaire {
//
//	private final AndroidDriver driver;
//	private final WebDriverWait wait;
//
//	public Questionnaire(AndroidDriver driver) {
//		this.driver = driver;
//		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//	}
//
//	public List<String> getPatientNames() {
//
//    // Actual patient member node load hone ka wait
//    wait.until(driver ->
//        !driver.findElements(
//            AppiumBy.xpath(
//                "//*[contains(@content-desc, 'Member No:')]"
//            )
//        ).isEmpty()
//    );
//
//    Set<String> names = new LinkedHashSet<>();
//
//    Pattern pattern = Pattern.compile(
//        "#\\d+\\.\\s*([^\\r\\n]+)"
//    );
//
//    List<WebElement> elements = driver.findElements(
//        AppiumBy.xpath(
//            "//*[contains(@content-desc, 'Member No:')]"
//        )
//    );
//
//    for (WebElement element : elements) {
//
//        String desc = element.getAttribute("content-desc");
//
//        if (desc == null) {
//            continue;
//        }
//
//        Matcher matcher = pattern.matcher(desc);
//
//        while (matcher.find()) {
//            String name = matcher.group(1).trim();
//
//            if (!name.isEmpty()) {
//                names.add(name);
//            }
//        }
//    }
//
//    System.out.println("Patient names detected: " + names);
//
//    return new ArrayList<>(names);
//}
//
//	private void extractName(Set<String> names, Pattern pattern, String value) {
//
//		if (value == null || value.isEmpty()) {
//			return;
//		}
//
//		Matcher matcher = pattern.matcher(value);
//
//		while (matcher.find()) {
//			String name = matcher.group(1).trim();
//
//			if (!name.isEmpty()) {
//				names.add(name);
//			}
//		}
//	}
//
//	public void openMemberQuestionnaire(String memberName) {
//
//		if (memberName == null || memberName.trim().isEmpty()) {
//			throw new IllegalArgumentException("Patient name cannot be empty.");
//		}
//
//		String name = memberName.trim();
//
//		// Exact patient member node locate karein
//		By memberLocator = AppiumBy.xpath("//*[@content-desc and " + "contains(@content-desc, 'Member No:') and "
//				+ "contains(@content-desc, " + xpathLiteral(name) + ")]");
//
//		WebElement member = wait.until(ExpectedConditions.visibilityOfElementLocated(memberLocator));
//
//		// Fresh accessibility nodes read karein
//		List<WebElement> nodes = driver.findElements(AppiumBy.xpath("//*[@content-desc]"));
//
//		int memberIndex = -1;
//
//		for (int i = 0; i < nodes.size(); i++) {
//
//			String desc = nodes.get(i).getAttribute("content-desc");
//
//			if (desc != null && desc.contains("Member No:") && desc.contains(name)) {
//
//				memberIndex = i;
//				break;
//			}
//		}
//
//		if (memberIndex < 0) {
//			throw new org.openqa.selenium.NoSuchElementException("Patient node not found: " + name);
//		}
//
//		// Agle patient ke node tak Questionnaire dhoondhein
//		for (int i = memberIndex + 1; i < nodes.size(); i++) {
//
//			WebElement node = nodes.get(i);
//			String desc = node.getAttribute("content-desc");
//
//			if (desc == null) {
//				continue;
//			}
//
//			if (desc.contains("Member No:")) {
//				break;
//			}
//
//			if ("Questionnaire".equalsIgnoreCase(desc.trim())) {
//
//				// Button visible hone ka wait
//				wait.until(ExpectedConditions.visibilityOf(node));
//
//				// Button clickable hone ka wait
//				wait.until(ExpectedConditions.elementToBeClickable(node));
//
//				node.click();
//
//				System.out.println("Questionnaire clicked for: " + name);
//
//				return;
//			}
//		}
//
//		throw new org.openqa.selenium.NoSuchElementException("Questionnaire button not found for patient: " + name);
//	}
//
//
//	private String xpathLiteral(String value) {
//
//		if (!value.contains("'")) {
//			return "'" + value + "'";
//		}
//
//		if (!value.contains("\"")) {
//			return "\"" + value + "\"";
//		}
//
//		String[] parts = value.split("'", -1);
//		StringBuilder result = new StringBuilder("concat(");
//
//		for (int i = 0; i < parts.length; i++) {
//
//			if (i > 0) {
//				result.append(", \"'\", ");
//			}
//
//			result.append("'").append(parts[i]).append("'");
//		}
//
//		result.append(")");
//
//		return result.toString();
//	}
//	
//
//}



package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.NoSuchElementException;
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

	// "#1.  Karan Kumar" -> "Karan Kumar"
	private static final Pattern NAME_PATTERN = Pattern.compile("#\\d+\\.\\s*([^\\r\\n]+)");

	private static final String CARD_XPATH = "//*[contains(@content-desc, 'Member No:')]";

	private static final int MAX_SCROLLS = 30;

	public Questionnaire(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	// =====================================================
	// 1) Screen par jo patients dikh rahe hain unke naam
	// =====================================================
	public List<String> getPatientNames() {

		waitForMembers();

		Set<String> names = readVisiblePatientNames();

		System.out.println("Patient names detected: " + names);
		return new ArrayList<>(names);
	}

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

	// =====================================================
	// 2) Patient ka Questionnaire button: pehle screen par, nahi to neeche scroll
	// =====================================================
	public void openMemberQuestionnaire(String patientName) {

		if (patientName == null || patientName.trim().isEmpty()) {
			throw new IllegalArgumentException("Patient name cannot be empty.");
		}

		String name = patientName.trim();

		waitForMembers();

		for (int i = 0; i < MAX_SCROLLS; i++) {

			// Pehle current screen par check
			if (clickQuestionnaireIfVisible(name, false)) {
				System.out.println("Questionnaire clicked for: " + name);
				return;
			}

			// Nahi mila -> neeche scroll
			boolean more = scrollDownOnce();

			// Tree refresh hone ka wait
			pause(800);

			if (clickQuestionnaireIfVisible(name, !more)) {
				System.out.println("Questionnaire clicked for: " + name);
				return;
			}

			// List ka end aa gaya
			if (!more) {
				break;
			}
		}

		throw new NoSuchElementException("Patient ya uska Questionnaire button nahi mila: " + name);
	}

	
	private boolean clickQuestionnaireIfVisible(String name, boolean allowOverlap) {

		try {
			for (WebElement card : driver.findElements(AppiumBy.xpath(CARD_XPATH))) {

				String cardName = extractName(card.getAttribute("content-desc"));

				if (cardName == null || !cardName.equalsIgnoreCase(name)) {
					continue;
				}

				List<WebElement> buttons = card.findElements(AppiumBy.accessibilityId("Questionnaire"));

				if (buttons.isEmpty()) {
					return false; // card aadha dikh raha hai, button abhi nahi
				}

				WebElement button = buttons.get(0);

				if (!allowOverlap && isHiddenBehindAddMember(button)) {
					return false;
				}

				wait.until(ExpectedConditions.elementToBeClickable(button)).click();
				return true;
			}
		} catch (StaleElementReferenceException e) {
			return false; // scroll ke beech tree badal gaya, agli baar dobara try hoga
		}

		return false;
	}

	private boolean isHiddenBehindAddMember(WebElement button) {
		List<WebElement> add = driver.findElements(AppiumBy.accessibilityId("Add Member"));
		if (add.isEmpty()) {
			return false;
		}
		Rectangle b = button.getRect();
		Rectangle a = add.get(0).getRect();
		return b.getY() + b.getHeight() > a.getY();
	}

	// =====================================================
	// Scroll (sirf neeche)
	// =====================================================

	// true = scroll hua aur aur scroll ho sakta hai, false = list ke end par
	private boolean scrollDownOnce() {
		try {
			Map<String, Object> args = new HashMap<>();

			List<WebElement> views = driver.findElements(AppiumBy.className("android.widget.ScrollView"));

			if (!views.isEmpty()) {
				// ScrollView ke beech ka safe area (neeche Add Member button se door)
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

			args.put("direction", "down");
			args.put("percent", 0.7);

			Object result = driver.executeScript("mobile: scrollGesture", args);
			return result instanceof Boolean && (Boolean) result;

		} catch (Exception e) {
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
}
