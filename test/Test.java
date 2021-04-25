import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Required class for running JUnit tests
 */
public class Test {
    /**
     * Runs the added classes
     * @param args string arguments
     */
    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(JUnitTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
