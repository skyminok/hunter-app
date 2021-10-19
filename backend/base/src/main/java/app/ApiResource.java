package app;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Lazy
@RestController
@PropertySource("classpath:version.properties")
public class ApiResource {

    private transient final ProjectInfo projectInfo;

    public ApiResource(Environment env) {
        projectInfo = ProjectInfo.builder()
                .version(env.getProperty("app.project-info.version"))
                .gitBranch(env.getProperty("app.project-info.gitBranch"))
                .gitHash(env.getProperty("app.project-info.gitHash"))
                .gitHashFull(env.getProperty("app.project-info.gitHashFull"))
                .build();
    }

    @GetMapping(path = AppPath.API_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectInfo apiInfo() {
        return projectInfo;
    }

    @Getter
    @Builder
    public static class ProjectInfo {

        private final String version;
        private final String gitBranch;
        private final String gitHash;
        private final String gitHashFull;
    }
}
