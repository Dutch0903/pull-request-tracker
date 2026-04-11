package com.prtracker.infrastructure.persistence;

import com.prtracker.application.query.dto.CodeRepositoryProjection;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.infrastructure.persistence.dto.CodeRepositoryDto;
import com.prtracker.infrastructure.persistence.mapper.CodeRepositoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static com.prtracker.testfixtures.domain.entity.CodeRepositoryTestBuilder.aCodeRepository;
import static com.prtracker.testfixtures.infrastructure.persistence.dto.CodeRepositoryDtoTestBuilder.aCodeRepositoryDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryCodeRepositoryRepositoryTest {

    @Mock
    private FileStorage fileStorage;

    @Mock
    private CodeRepositoryMapper mapper;

    @InjectMocks
    private InMemoryCodeRepositoryRepository inMemoryCodeRepositoryRepository;

    @Test
    void save_whenCalled_shouldAddCodeRepositoryToList() {
        CodeRepository codeRepository = aCodeRepository().build();

        assertEquals(0, inMemoryCodeRepositoryRepository.count());
        inMemoryCodeRepositoryRepository.save(codeRepository);

        assertEquals(List.of(codeRepository), inMemoryCodeRepositoryRepository.findAll());
    }

    @Test
    void save_whenCodeRepositoryAlreadyExists_shouldOverrideExisting() {
        CodeRepository codeRepository = aCodeRepository().build();

        inMemoryCodeRepositoryRepository.save(codeRepository);

        assertEquals(List.of(codeRepository), inMemoryCodeRepositoryRepository.findAll());

        inMemoryCodeRepositoryRepository.save(codeRepository);

        assertEquals(List.of(codeRepository), inMemoryCodeRepositoryRepository.findAll());
    }

    @Test
    void delete_whenCalled_shouldDeleteCodeRepositoryFromList() {
        CodeRepository codeRepository = aCodeRepository().build();
        inMemoryCodeRepositoryRepository.save(codeRepository);

        assertEquals(1, inMemoryCodeRepositoryRepository.count());

        inMemoryCodeRepositoryRepository.delete(codeRepository);

        assertEquals(0, inMemoryCodeRepositoryRepository.count());
    }

    @Test
    void findAll_whenCalled_shouldFindAllCodeRepositoryFromList() {
        CodeRepository codeRepository1 = aCodeRepository().build();
        CodeRepository codeRepository2 = aCodeRepository().build();

        inMemoryCodeRepositoryRepository.save(codeRepository1);
        inMemoryCodeRepositoryRepository.save(codeRepository2);

        List<CodeRepository> codeRepositoryList = inMemoryCodeRepositoryRepository.findAll();
        assertTrue(codeRepositoryList.contains(codeRepository1));
        assertTrue(codeRepositoryList.contains(codeRepository2));
    }

    @Test
    void count_whenCalled_shouldCountCodeRepositoryFromList() {
        CodeRepository codeRepository1 = aCodeRepository().build();
        CodeRepository codeRepository2 = aCodeRepository().build();

        inMemoryCodeRepositoryRepository.save(codeRepository1);

        assertEquals(1, inMemoryCodeRepositoryRepository.count());

        inMemoryCodeRepositoryRepository.save(codeRepository2);

        assertEquals(2, inMemoryCodeRepositoryRepository.count());
    }

    @Test
    void exists_whenFullNameExists_shouldReturnTrue() {
        FullName fullName = new FullName("owner", "name");
        CodeRepository codeRepository = aCodeRepository().withFullName(fullName).build();

        inMemoryCodeRepositoryRepository.save(codeRepository);

        assertTrue(inMemoryCodeRepositoryRepository.exists(fullName));
    }

    @Test
    void exists_whenFullNameDoesNotExists_shouldReturnFalse() {
        FullName fullName = new FullName("owner", "name");

        assertFalse(inMemoryCodeRepositoryRepository.exists(fullName));
    }

    @Test
    void findAllAsViews_whenCalled_shouldReturnCodeRepositoryFromList() {
        CodeRepository codeRepository = aCodeRepository().build();

        inMemoryCodeRepositoryRepository.save(codeRepository);

        List<CodeRepositoryProjection> projections = inMemoryCodeRepositoryRepository.findAllAsViews();

        assertEquals(1, projections.size());

        CodeRepositoryProjection projection = projections.getFirst();

        assertEquals(codeRepository.getId().value(), projection.id());
    }

    @Test
    void initialize_whenCalled_shouldLoadFromFileAndMapToCodeRepository() {
        CodeRepositoryDto codeRepositoryDto = aCodeRepositoryDto().build();
        CodeRepository codeRepository = aCodeRepository().build();
        List<CodeRepositoryDto> list = List.of(codeRepositoryDto);

        when(fileStorage.load(anyString(), eq(CodeRepositoryDto.class))).thenReturn(list);
        when(mapper.toDomain(codeRepositoryDto)).thenReturn(codeRepository);

        inMemoryCodeRepositoryRepository.initialize();

        assertEquals(List.of(codeRepository), inMemoryCodeRepositoryRepository.findAll());

        verify(fileStorage, times(1)).load(anyString(), eq(CodeRepositoryDto.class));
        verify(mapper, times(1)).toDomain(codeRepositoryDto);
    }

    @Test
    void persist__whenCalled_shouldPersistCodeRepositoryToFile() throws IOException {
        CodeRepository codeRepository = aCodeRepository().build();
        CodeRepositoryDto codeRepositoryDto = aCodeRepositoryDto().build();
        inMemoryCodeRepositoryRepository.save(codeRepository);

        when(mapper.toDto(codeRepository)).thenReturn(codeRepositoryDto);

        inMemoryCodeRepositoryRepository.persist();

        verify(fileStorage, times(1)).save(anyString(), eq(List.of(codeRepositoryDto)));
    }
}
