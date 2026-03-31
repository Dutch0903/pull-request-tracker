package com.prtracker.presentation.cli.view.repository;

import com.prtracker.application.command.CreateCodeRepositoryCommand;
import com.prtracker.application.command.dto.CreateCodeRepositoryDto;
import com.prtracker.application.query.GetTokensQuery;
import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.dialog.DialogType;
import com.prtracker.presentation.cli.dialog.form.FormDialogHandler;
import com.prtracker.presentation.cli.dialog.repository.CreateRepositoryDialogConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RepositoryListController {
    private final DialogManager dialogManager;

    private final CreateCodeRepositoryCommand command;
    private final GetTokensQuery getTokensQuery;

    public void openCreateRepositoryDialog() {
        List<TokenProjection> tokens = getTokensQuery.execute();
        FormDialogHandler handler = values -> {
            Optional<TokenProjection> selectedToken = tokens.stream().filter(
                    token -> token.name().equals(values.get(CreateRepositoryDialogConfiguration.TOKEN))
            ).findFirst();

            UUID tokenId = selectedToken.map(TokenProjection::id).orElse(null);

            command.execute(
                    new CreateCodeRepositoryDto(
                            values.get(CreateRepositoryDialogConfiguration.REFERENCE),
                            tokenId
                    )
            );
        };

        dialogManager.openDialog(DialogType.FORM, new CreateRepositoryDialogConfiguration(tokens), handler);
    }
}
