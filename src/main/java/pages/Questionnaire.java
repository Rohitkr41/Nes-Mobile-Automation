
package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class Questionnaire {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

	public Questionnaire(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public List<String> getPatientNames() {

    // Actual patient member node load hone ka wait
    wait.until(driver ->
        !driver.findElements(
            AppiumBy.xpath(
                "//*[contains(@content-desc, 'Member No:')]"
            )
        ).isEmpty()
    );

    Set<String> names = new LinkedHashSet<>();

    Pattern pattern = Pattern.compile(
        "#\\d+\\.\\s*([^\\r\\n]+)"
    );

    List<WebElement> elements = driver.findElements(
        AppiumBy.xpath(
            "//*[contains(@content-desc, 'Member No:')]"
        )
    );

    for (WebElement element : elements) {

        String desc = element.getAttribute("content-desc");

        if (desc == null) {
            continue;
        }

        Matcher matcher = pattern.matcher(desc);

        while (matcher.find()) {
            String name = matcher.group(1).trim();

            if (!name.isEmpty()) {
                names.add(name);
            }
        }
    }

    System.out.println("Patient names detected: " + names);

    return new ArrayList<>(names);
}

	private void extractName(Set<String> names, Pattern pattern, String value) {

		if (value == null || value.isEmpty()) {
			return;
		}

		Matcher matcher = pattern.matcher(value);

		while (matcher.find()) {
			String name = matcher.group(1).trim();

			if (!name.isEmpty()) {
				names.add(name);
			}
		}
	}

	public void openMemberQuestionnaire(String memberName) {

		if (memberName == null || memberName.trim().isEmpty()) {
			throw new IllegalArgumentException("Patient name cannot be empty.");
		}

		String name = memberName.trim();

		// Exact patient member node locate karein
		By memberLocator = AppiumBy.xpath("//*[@content-desc and " + "contains(@content-desc, 'Member No:') and "
				+ "contains(@content-desc, " + xpathLiteral(name) + ")]");

		WebElement member = wait.until(ExpectedConditions.visibilityOfElementLocated(memberLocator));

		// Fresh accessibility nodes read karein
		List<WebElement> nodes = driver.findElements(AppiumBy.xpath("//*[@content-desc]"));

		int memberIndex = -1;

		for (int i = 0; i < nodes.size(); i++) {

			String desc = nodes.get(i).getAttribute("content-desc");

			if (desc != null && desc.contains("Member No:") && desc.contains(name)) {

				memberIndex = i;
				break;
			}
		}

		if (memberIndex < 0) {
			throw new org.openqa.selenium.NoSuchElementException("Patient node not found: " + name);
		}

		// Agle patient ke node tak Questionnaire dhoondhein
		for (int i = memberIndex + 1; i < nodes.size(); i++) {

			WebElement node = nodes.get(i);
			String desc = node.getAttribute("content-desc");

			if (desc == null) {
				continue;
			}

			if (desc.contains("Member No:")) {
				break;
			}

			if ("Questionnaire".equalsIgnoreCase(desc.trim())) {

				// Button visible hone ka wait
				wait.until(ExpectedConditions.visibilityOf(node));

				// Button clickable hone ka wait
				wait.until(ExpectedConditions.elementToBeClickable(node));

				node.click();

				System.out.println("Questionnaire clicked for: " + name);

				return;
			}
		}

		throw new org.openqa.selenium.NoSuchElementException("Questionnaire button not found for patient: " + name);
	}

	private String xpathLiteral(String value) {

		if (!value.contains("'")) {
			return "'" + value + "'";
		}

		if (!value.contains("\"")) {
			return "\"" + value + "\"";
		}

		String[] parts = value.split("'", -1);
		StringBuilder result = new StringBuilder("concat(");

		for (int i = 0; i < parts.length; i++) {

			if (i > 0) {
				result.append(", \"'\", ");
			}

			result.append("'").append(parts[i]).append("'");
		}

		result.append(")");

		return result.toString();
	}
}
