package com.prtracker.application.parser;

import com.prtracker.domain.enums.CodeRepositoryReferenceType;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParsedCodeRepositoryReferenceTest {
    @Test
    void getIdentifier_whenCalled_shouldReturnCodeRepositoryIdentifier() {
        String owner = "owner";
        String name = "repo";
        CodeRepositoryReferenceType type = CodeRepositoryReferenceType.HTTPS_URL;

        ParsedCodeRepositoryReference parsedCodeRepositoryReference = new ParsedCodeRepositoryReference(owner, name,
                type);

        assertEquals(CodeRepositoryIdentifier.from(owner, name), parsedCodeRepositoryReference.getIdentifier());
    }
}
