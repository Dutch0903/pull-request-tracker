package com.prtracker.presentation.cli.dialog.repository;

import com.prtracker.application.command.CreateCodeRepositoryCommand;
import com.prtracker.application.command.dto.CreateCodeRepositoryDto;
import com.prtracker.application.query.GetTokensQuery;
import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.dialog.DialogAction;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.dialog.DialogType;
import com.prtracker.presentation.cli.dialog.form.FormDialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateRepositoryDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final CreateCodeRepositoryCommand createCodeRepositoryCommand;
    private final GetTokensQuery getTokensQuery;

    @Override
    public void open() {
        List<TokenProjection> tokens = getTokensQuery.execute();
        FormDialogHandler handler = values -> {
            Optional<TokenProjection> selectedToken = tokens.stream()
                    .filter(token -> token.name().equals(values.get(RepositoryFormFields.TOKEN))).findFirst();

            UUID tokenId = selectedToken.map(TokenProjection::id).orElse(null);

            createCodeRepositoryCommand
                    .execute(new CreateCodeRepositoryDto(values.get(RepositoryFormFields.REFERENCE), tokenId));
        };

        dialogManager.openDialog(DialogType.FORM, new CreateRepositoryDialogConfiguration(tokens), handler);
    }
}
