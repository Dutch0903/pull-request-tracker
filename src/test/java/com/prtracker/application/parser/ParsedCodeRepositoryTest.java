package com.prtracker.application.parser;

import com.prtracker.domain.enums.CodeRepositoryIdentifierType;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParsedCodeRepositoryTest {
    @Test
    void getIdentifier_whenCalled_shouldReturnCodeRepositoryIdentifier() {
        String owner = "owner";
        String name = "repo";
        CodeRepositoryIdentifierType type = CodeRepositoryIdentifierType.HTTPS_URL;

        ParsedCodeRepository parsedCodeRepository = new ParsedCodeRepository(owner, name, type);

        assertEquals(CodeRepositoryIdentifier.from(owner, name), parsedCodeRepository.getIdentifier());
    }
}
