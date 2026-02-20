package com.prtracker.presentation.cli;

import com.prtracker.application.dto.AddCodeRepositoryDto;
import com.prtracker.application.usecase.AddCodeRepositoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddCodeRepositoryCommand {
    private final AddCodeRepositoryUseCase addCodeRepositoryUseCase;

    @Command(
            name = "add-code-repository"
    )
    public void execute(
            @Option(
                    shortName = 'o',
                    longName = "owner",
                    required = true
            ) String owner,
            @Option(
                    shortName = 'n',
                    longName = "name",
                    required = true
            ) String name
    ) {
        AddCodeRepositoryDto dto = new AddCodeRepositoryDto(owner, name);

        addCodeRepositoryUseCase.execute(dto);
    }
}
