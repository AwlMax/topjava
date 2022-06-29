package ru.javawebinar;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimingRules {
    private static final Logger log = LoggerFactory.getLogger("result");

    private static final StringBuilder results = new StringBuilder();

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            log.info(result + " ms\n");
        }
    };

    //format nice console output
    public static final ExternalResource SUMMARY = new ExternalResource() {

        //null before running class tests
        @Override
        protected void before() throws Throwable {
            results.setLength(0);
        }

        //output formatted result
        @Override
        protected void after() {
            log.info("\n" +
                    "\nTest                                                                                       Duration, ms" +
                    "\n" + "\n" + results + "\n");
        }
    };
}
