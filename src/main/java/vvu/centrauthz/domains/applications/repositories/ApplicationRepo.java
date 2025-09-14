package vvu.centrauthz.domains.applications.repositories;

import com.speedment.jpastreamer.application.JPAStreamer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import vvu.centrauthz.domains.applications.entities.ApplicationEntity;
import vvu.centrauthz.domains.applications.entities.ApplicationEntity$;
import vvu.centrauthz.domains.applications.models.ApplicationFilter;
import vvu.centrauthz.domains.applications.models.SortableApplicationField;
import vvu.centrauthz.domains.common.models.SortDirection;

/**
 * Repository for managing ApplicationEntity persistence operations.
 */
@Slf4j
@ApplicationScoped
public class ApplicationRepo implements PanacheRepository<ApplicationEntity> {

    private static final String APPLICATION_KEY = "applicationKey";

    JPAStreamer jpaStreamer;

    public ApplicationRepo(JPAStreamer jpaStreamer) {
        this.jpaStreamer = jpaStreamer;
    }

    /**
     * Queries applications based on the provided filter criteria.
     *
     * @param filter the search and pagination criteria
     * @param offset the starting index for pagination
     * @return list of matching application entities
     */
    public List<ApplicationEntity> query(ApplicationFilter filter, Integer offset) {
        var stream = jpaStreamer.stream(ApplicationEntity.class);
        stream = applyFilters(stream, filter);

        return stream.sorted(buildSort(filter))
                .skip(offset)
                .limit(filter.pageSize())
                .toList();
    }

    /**
     * Builds a comparator for sorting applications based on filter criteria.
     * Returns default sort (newest first) if no sort order specified.
     */
    private Comparator<ApplicationEntity> buildSort(ApplicationFilter filter) {
        if (filter.sortOrder() == null || filter.sortOrder().isEmpty()) {
            return ApplicationEntity$.createdAt.reversed(); // Default sort
        }

        Comparator<ApplicationEntity> comparator = null;

        for (var customSort : filter.sortOrder()) {
            var sortField = SortableApplicationField.from(customSort.field());
            var fieldComparator = getFieldComparator(sortField, customSort.direction());

            comparator = (comparator == null) ? fieldComparator :
                    comparator.thenComparing(fieldComparator);
        }

        return comparator;
    }

    /**
     * Creates a comparator for a specific field and direction.
     */
    private Comparator<ApplicationEntity> getFieldComparator(SortableApplicationField field,
                                                             SortDirection direction) {
        var baseComparator = switch (field) {
            case APPLICATION_KEY -> ApplicationEntity$.applicationKey;
            case NAME -> ApplicationEntity$.name;
            case CREATED_DATE -> ApplicationEntity$.createdBy;
            case UPDATED_DATE -> ApplicationEntity$.updatedAt;
        };

        return direction == SortDirection.ASC ? baseComparator.comparator() :
                baseComparator.reversed();
    }

    /**
     * Applies filtering conditions to the query.
     */
    private Stream<ApplicationEntity> applyFilters(Stream<ApplicationEntity> stream,
                                                   ApplicationFilter filter) {
        if (filter.name() != null) {
            stream = stream.filter(applicationEntity -> Objects.equals(applicationEntity.getName(),
                    filter.name()));
        }
        if (filter.managementGroupId() != null) {
            stream = stream.filter(
                    applicationEntity -> Objects.equals(applicationEntity.getManagementGroupId(),
                            filter.managementGroupId()));
        }
        if (filter.ownerId() != null) {
            stream = stream.filter(
                    applicationEntity -> Objects.equals(applicationEntity.getOwnerId(),
                            filter.ownerId()));
        }
        return stream;
    }

    /**
     * Find an application by its key.
     *
     * @param applicationKey the application key to search for
     * @return an Optional containing the found application, or empty if not found
     */
    public Optional<ApplicationEntity> findByKey(String applicationKey) {
        return find(APPLICATION_KEY, applicationKey).firstResultOptional();
    }

    /**
     * Find an application by its key, locking the record for write
     * to prevent concurrent modifications.
     *
     * @param applicationKey the application key to search for
     * @return an Optional containing the found application, or empty if not found
     */
    public Optional<ApplicationEntity> findByKeyWithLock(String applicationKey) {
        return find(APPLICATION_KEY, applicationKey)
                .withLock(LockModeType.PESSIMISTIC_WRITE)
                .firstResultOptional();
    }

    /**
     * Check if an application with the given key exists.
     *
     * @param applicationKey the application key to check
     * @return true if an application with the key exists, false otherwise
     */
    public boolean existsByKey(String applicationKey) {
        return count(APPLICATION_KEY, applicationKey) > 0;
    }

    /**
     * Delete an application by its key.
     *
     * @param applicationKey the application key to delete
     * @return true if the application was deleted, false if not found
     */
    public boolean deleteByKey(String applicationKey) {
        return delete(APPLICATION_KEY, applicationKey) > 0;
    }

    /**
     * Check if an application with the given name exists (case-insensitive).
     *
     * @param name the name to check
     * @return true if an application with the name exists, false otherwise
     */
    public boolean existsByNameIgnoreCase(String name) {
        return count("LOWER(name) = LOWER(?1)", name) > 0;
    }

}
