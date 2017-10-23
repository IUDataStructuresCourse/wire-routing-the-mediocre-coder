import java.io.PrintWriter;
import java.io.FileNotFoundException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class IUAutogradingRunner {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Too many arguments!");
			System.out.println(args);
			System.exit(1);
		}

		PrintWriter writer = null;

		try {
			writer = new PrintWriter("testrunner_results.txt");
		} catch (FileNotFoundException e) {
			java.lang.System.exit(-1);
		}

		Class<?> test_class = null;

		try {
			test_class = Class.forName(args[0]);
		} catch (ClassNotFoundException e) {
			writer.println("Unable to find specified test class.");
			System.exit(1);
		}

		Result result = JUnitCore.runClasses(test_class);

		int total_tests    = result.getRunCount(),
		    total_failures = result.getFailureCount();

		for (Failure failure : result.getFailures()) {
			writer.println(failure.toString());
		}

		writer.println(total_tests + " " + (total_tests - total_failures) + " " + total_failures);
		writer.close();
	}
}
