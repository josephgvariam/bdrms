package in.bigdash.rms.application.config;
import in.bigdash.rms.BigDashRMSApplication;
import io.springlets.data.jpa.repository.support.DetachableJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(repositoryBaseClass = DetachableJpaRepositoryImpl.class, basePackageClasses = BigDashRMSApplication.class)
public class SpringDataJpaDetachableRepositoryConfiguration {
}
