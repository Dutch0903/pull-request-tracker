package com.prtracker.coderepository.application;

import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import com.prtracker.coderepository.domain.port.TokenExistencePort;
import com.prtracker.coderepository.domain.service.CodeRepositoryDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CodeRepositoryDomainConfiguration {

    @Bean
    CodeRepositoryDomainService codeRepositoryDomainService(CodeRepositoryRepository codeRepositoryRepository,
            TokenExistencePort tokenExistencePort) {
        return new CodeRepositoryDomainService(codeRepositoryRepository, tokenExistencePort);
    }
}
