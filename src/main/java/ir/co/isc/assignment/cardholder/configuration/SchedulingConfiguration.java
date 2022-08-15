package ir.co.isc.assignment.cardholder.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
@Slf4j
public class SchedulingConfiguration {
    private long prevTotal = 0;
    private long prevFree = Runtime.getRuntime().freeMemory();

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void jvmHeapMetric() {
        Runtime rt = Runtime.getRuntime();
        long total = rt.totalMemory();
        long free = rt.freeMemory();
        if (total != prevTotal || free != prevFree) {
            log.info(
                    String.format("Total: %s, Free: %s, Diff: %s",
                            total,
                            free,
                            prevFree - free));
            prevTotal = total;
            prevFree = free;
        }
    }
}
