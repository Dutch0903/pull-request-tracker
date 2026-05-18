package com.prtracker.app.cli.repository;

import com.prtracker.app.cli.dialog.DialogAction;
import com.prtracker.app.cli.dialog.DialogManager;
import com.prtracker.app.cli.dialog.DialogType;
import com.prtracker.app.cli.dialog.form.FormDialogHandler;
import com.prtracker.coderepository.application.command.CreateCodeRepository;
import com.prtracker.coderepository.application.command.CreateCodeRepositoryDto;
import com.prtracker.token.application.query.GetTokens;
import com.prtracker.token.application.query.TokenProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateRepositoryDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final CreateCodeRepository createCodeRepository;
    private final GetTokens getTokens;

    @Override
    public void open() {
        List<TokenProjection> tokens = getTokens.execute();
        FormDialogHandler handler = values -> {
            Optional<TokenProjection> selectedToken = tokens.stream()
                    .filter(token -> token.name().equals(values.get(RepositoryFormFields.TOKEN))).findFirst();

            UUID tokenId = selectedToken.map(TokenProjection::id).orElse(null);

            createCodeRepository
                    .execute(new CreateCodeRepositoryDto(values.get(RepositoryFormFields.REFERENCE), tokenId));
        };

        dialogManager.openDialog(DialogType.FORM, new CreateRepositoryDialogConfiguration(tokens), handler);
    }
}
