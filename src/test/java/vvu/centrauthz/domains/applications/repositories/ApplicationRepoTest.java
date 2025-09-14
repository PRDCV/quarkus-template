package vvu.centrauthz.domains.applications.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.speedment.jpastreamer.application.JPAStreamer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import vvu.centrauthz.domains.applications.entities.ApplicationEntity;
import vvu.centrauthz.domains.applications.models.ApplicationFilter;
import vvu.centrauthz.domains.common.models.Sort;
import vvu.centrauthz.domains.common.models.SortDirection;

public class ApplicationRepoTest {
    @Test
    void testQuery_WithNoFilters() {
        JPAStreamer jpaStreamer = mock(JPAStreamer.class);
        ApplicationRepo repo = new ApplicationRepo(jpaStreamer);
        repo.jpaStreamer = jpaStreamer;

        ApplicationEntity mockEntity = new ApplicationEntity();
        Stream<ApplicationEntity> mockStream = Stream.of(mockEntity);

        var filter = ApplicationFilter.builder()
                .pageSize(10)
                .build();
        when(jpaStreamer.stream(ApplicationEntity.class)).thenReturn(mockStream);

        List<ApplicationEntity> result = repo.query(filter, 0);

        assertNotNull(result);
        verify(jpaStreamer).stream(ApplicationEntity.class);
    }

    @Test
    void testQuery_WithFilters() {
        JPAStreamer jpaStreamer = mock(JPAStreamer.class);
        ApplicationRepo repo = new ApplicationRepo(jpaStreamer);
        repo.jpaStreamer = jpaStreamer;

        ApplicationEntity mockEntity = new ApplicationEntity();
        mockEntity.setName("TestApp");
        mockEntity.setOwnerId(UUID.randomUUID());
        mockEntity.setManagementGroupId(UUID.randomUUID());
        Stream<ApplicationEntity> mockStream = Stream.of(mockEntity);

        var filter = ApplicationFilter.builder()
                .name("TestApp")
                .ownerId(mockEntity.getOwnerId())
                .managementGroupId(mockEntity.getManagementGroupId())
                .pageSize(10)
                .build();
        when(jpaStreamer.stream(ApplicationEntity.class)).thenReturn(mockStream);

        List<ApplicationEntity> result = repo.query(filter, 0);

        assertNotNull(result);
        verify(jpaStreamer).stream(ApplicationEntity.class);
    }

    @Test
    void testQuery_WithSortOrder() {
        JPAStreamer jpaStreamer = mock(JPAStreamer.class);
        ApplicationRepo repo = new ApplicationRepo(jpaStreamer);
        repo.jpaStreamer = jpaStreamer;

        ApplicationEntity mockEntity = new ApplicationEntity();
        Stream<ApplicationEntity> mockStream = Stream.of(mockEntity);

        var sortOrder = List.of(new Sort("name", SortDirection.ASC));
        var filter = ApplicationFilter.builder()
                .pageSize(10)
                .sortOrder(sortOrder)
                .build();
        when(jpaStreamer.stream(ApplicationEntity.class)).thenReturn(mockStream);

        List<ApplicationEntity> result = repo.query(filter, 5);

        assertNotNull(result);
        verify(jpaStreamer).stream(ApplicationEntity.class);
    }

    @Test
    void testQuery_WithMultipleSorts() {
        JPAStreamer jpaStreamer = mock(JPAStreamer.class);
        ApplicationRepo repo = new ApplicationRepo(jpaStreamer);
        repo.jpaStreamer = jpaStreamer;

        ApplicationEntity mockEntity = new ApplicationEntity();
        Stream<ApplicationEntity> mockStream = Stream.of(mockEntity);

        var sortOrder = List.of(
                new Sort("name", SortDirection.ASC),
                new Sort("createdDate", SortDirection.DESC)
        );
        var filter = ApplicationFilter.builder()
                .pageSize(10)
                .sortOrder(sortOrder)
                .build();
        when(jpaStreamer.stream(ApplicationEntity.class)).thenReturn(mockStream);

        List<ApplicationEntity> result = repo.query(filter, 0);

        assertNotNull(result);
        verify(jpaStreamer).stream(ApplicationEntity.class);
    }

    @Test
    void testQuery_AllSortableFields() {
        JPAStreamer jpaStreamer = mock(JPAStreamer.class);
        ApplicationRepo repo = new ApplicationRepo(jpaStreamer);
        repo.jpaStreamer = jpaStreamer;

        ApplicationEntity mockEntity = new ApplicationEntity();

        var fields = List.of("applicationKey", "name", "createdDate", "updatedDate");

        for (String field : fields) {
            Stream<ApplicationEntity> mockStream =
                    Stream.of(mockEntity); // Fresh stream for each iteration
            var sortOrder = List.of(new Sort(field, SortDirection.DESC));
            var filter = ApplicationFilter.builder()
                    .pageSize(10)
                    .sortOrder(sortOrder)
                    .build();
            when(jpaStreamer.stream(ApplicationEntity.class)).thenReturn(mockStream);

            List<ApplicationEntity> result = repo.query(filter, 0);
            assertNotNull(result);
        }
    }

    @Test
    void testQuery_EmptySortOrder() {
        JPAStreamer jpaStreamer = mock(JPAStreamer.class);
        ApplicationRepo repo = new ApplicationRepo(jpaStreamer);
        repo.jpaStreamer = jpaStreamer;

        ApplicationEntity mockEntity = new ApplicationEntity();
        Stream<ApplicationEntity> mockStream = Stream.of(mockEntity);

        var filter = ApplicationFilter.builder()
                .pageSize(10)
                .sortOrder(List.of())
                .build();
        when(jpaStreamer.stream(ApplicationEntity.class)).thenReturn(mockStream);

        List<ApplicationEntity> result = repo.query(filter, 0);

        assertNotNull(result);
        verify(jpaStreamer).stream(ApplicationEntity.class);
    }

    @Test
    void testQuery_NullSortOrder() {
        JPAStreamer jpaStreamer = mock(JPAStreamer.class);
        ApplicationRepo repo = new ApplicationRepo(jpaStreamer);
        repo.jpaStreamer = jpaStreamer;

        ApplicationEntity mockEntity = new ApplicationEntity();
        Stream<ApplicationEntity> mockStream = Stream.of(mockEntity);

        var filter = ApplicationFilter.builder()
                .pageSize(10)
                .sortOrder(null)
                .build();
        when(jpaStreamer.stream(ApplicationEntity.class)).thenReturn(mockStream);

        List<ApplicationEntity> result = repo.query(filter, 0);

        assertNotNull(result);
        verify(jpaStreamer).stream(ApplicationEntity.class);
    }

    @Test
    void testFindByKey() {
        ApplicationRepo repo = mock(ApplicationRepo.class);
        ApplicationEntity entity = new ApplicationEntity();

        when(repo.findByKey("test-key")).thenReturn(Optional.of(entity));

        Optional<ApplicationEntity> result = repo.findByKey("test-key");

        assertTrue(result.isPresent());
        verify(repo).findByKey("test-key");
    }

    @Test
    void testFindByKeyWithLock() {
        ApplicationRepo repo = mock(ApplicationRepo.class);
        ApplicationEntity entity = new ApplicationEntity();

        when(repo.findByKeyWithLock("test-key")).thenReturn(Optional.of(entity));

        Optional<ApplicationEntity> result = repo.findByKeyWithLock("test-key");

        assertTrue(result.isPresent());
        verify(repo).findByKeyWithLock("test-key");
    }

    @Test
    void testExistsByKey() {
        ApplicationRepo repo = mock(ApplicationRepo.class);

        when(repo.existsByKey("test-key")).thenReturn(true);

        boolean result = repo.existsByKey("test-key");

        assertTrue(result);
        verify(repo).existsByKey("test-key");
    }

    @Test
    void testExistsByKey_NotFound() {
        ApplicationRepo repo = mock(ApplicationRepo.class);

        when(repo.existsByKey("test-key")).thenReturn(false);

        boolean result = repo.existsByKey("test-key");

        assertFalse(result);
        verify(repo).existsByKey("test-key");
    }

    @Test
    void testDeleteByKey() {
        ApplicationRepo repo = mock(ApplicationRepo.class);

        when(repo.deleteByKey("test-key")).thenReturn(true);

        boolean result = repo.deleteByKey("test-key");

        assertTrue(result);
        verify(repo).deleteByKey("test-key");
    }

    @Test
    void testDeleteByKey_NotFound() {
        ApplicationRepo repo = mock(ApplicationRepo.class);

        when(repo.deleteByKey("test-key")).thenReturn(false);

        boolean result = repo.deleteByKey("test-key");

        assertFalse(result);
        verify(repo).deleteByKey("test-key");
    }

    @Test
    void testExistsByNameIgnoreCase() {
        ApplicationRepo repo = mock(ApplicationRepo.class);

        when(repo.existsByNameIgnoreCase("TestApp")).thenReturn(true);

        boolean result = repo.existsByNameIgnoreCase("TestApp");

        assertTrue(result);
        verify(repo).existsByNameIgnoreCase("TestApp");
    }

    @Test
    void testExistsByNameIgnoreCase_NotFound() {
        ApplicationRepo repo = mock(ApplicationRepo.class);

        when(repo.existsByNameIgnoreCase("TestApp")).thenReturn(false);

        boolean result = repo.existsByNameIgnoreCase("TestApp");

        assertFalse(result);
        verify(repo).existsByNameIgnoreCase("TestApp");
    }
}
