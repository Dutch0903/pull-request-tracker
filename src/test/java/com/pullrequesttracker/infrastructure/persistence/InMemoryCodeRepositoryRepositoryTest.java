package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.infrastructure.persistence.dto.CodeRepositoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static com.pullrequesttracker.testfixtures.infrastructure.persistence.CodeRepositoryDtoTestBuilder.aCodeRepositoryDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InMemoryCodeRepositoryRepositoryTest {
    @Mock
    private FileStorage fileStorage;

    @Mock
    private CodeRepositoryMapper mapper;

    @InjectMocks
    private InMemoryCodeRepositoryRepository repository;

    @Test
    void save_shouldAddToRepository() {
        CodeRepository repo = aCodeRepository().build();

        repository.save(repo);

        assertEquals(List.of(repo), repository.findAll());
    }

    @Test
    void save_whenSavedTwice_shouldNotDuplicate() {
        CodeRepository repo = aCodeRepository().build();

        repository.save(repo);
        repository.save(repo);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findAll_whenMultipleSaved_shouldReturnAll() {
        CodeRepository repo1 = aCodeRepository().build();
        CodeRepository repo2 = aCodeRepository().build();

        repository.save(repo1);
        repository.save(repo2);

        assertTrue(repository.findAll().contains(repo1));
        assertTrue(repository.findAll().contains(repo2));
    }

    @Test
    void exists_whenFullNamePresent_shouldReturnTrue() {
        FullName fullName = new FullName("owner", "name");
        repository.save(aCodeRepository().withFullName(fullName).build());

        assertTrue(repository.exists(fullName));
    }

    @Test
    void exists_whenFullNameAbsent_shouldReturnFalse() {
        assertFalse(repository.exists(new FullName("owner", "name")));
    }

    @Test
    void initialize_shouldLoadFromFile() {
        CodeRepositoryDto dto = aCodeRepositoryDto().build();
        CodeRepository repo = aCodeRepository().build();

        when(fileStorage.load(anyString(), eq(CodeRepositoryDto.class))).thenReturn(List.of(dto));
        when(mapper.toDomain(dto)).thenReturn(repo);

        repository.initialize();

        assertEquals(List.of(repo), repository.findAll());
    }

    @Test
    void persist_shouldSaveToFile() throws IOException {
        CodeRepository repo = aCodeRepository().build();
        CodeRepositoryDto dto = aCodeRepositoryDto().build();
        repository.save(repo);

        when(mapper.toDto(repo)).thenReturn(dto);

        repository.persist();

        verify(fileStorage).save(anyString(), eq(List.of(dto)));
    }
}
