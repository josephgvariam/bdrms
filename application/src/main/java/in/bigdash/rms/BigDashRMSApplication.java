package in.bigdash.rms;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * = BigDashRMSApplication
 *
 * TODO Auto-generated class documentation
 *
 */
@SpringBootApplication
@EnableCaching
public class BigDashRMSApplication {

    /**
     * TODO Auto-generated method documentation
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BigDashRMSApplication.class, args);
    }
}
