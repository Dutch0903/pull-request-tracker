package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.application.usecase.FetchAllTokens;
import com.pullrequesttracker.application.usecase.CreateCodeRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.presentation.cli.dialog.DialogAction;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogHandler;
import com.pullrequesttracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateRepositoryDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final CreateCodeRepository createCodeRepository;
    private final FetchAllTokens getTokens;

    @Override
    public void open() {
        List<TokenDto> tokens = getTokens.execute();
        FormDialogHandler handler = values -> {
            Optional<TokenDto> selectedToken = tokens.stream()
                    .filter(t -> t.name().equals(values.get(RepositoryFormFields.TOKEN)))
                    .findFirst();

            TokenId tokenId = selectedToken.map(t -> TokenId.from(t.id())).orElse(null);
            createCodeRepository.execute(values.get(RepositoryFormFields.REFERENCE), Platform.GITHUB, tokenId);
        };

        dialogManager.openDialog(DialogType.FORM, new CreateRepositoryDialogConfiguration(tokens), handler);
    }
}
