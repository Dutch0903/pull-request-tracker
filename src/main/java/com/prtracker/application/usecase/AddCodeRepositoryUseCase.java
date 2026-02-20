package com.prtracker.application.usecase;

import com.prtracker.application.dto.AddCodeRepositoryDto;
import org.springframework.stereotype.Component;

@Component
public class AddCodeRepositoryUseCase extends AbstractUseCase<AddCodeRepositoryDto, Void> {

    @Override
    protected Void executeInternal(AddCodeRepositoryDto input) {
        return null;
    }
}
