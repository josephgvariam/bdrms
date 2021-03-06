package in.bigdash.rms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class BigDashRMSApplication {

    private final static Logger log = LoggerFactory.getLogger(BigDashRMSApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BigDashRMSApplication.class, args);
    }
}
