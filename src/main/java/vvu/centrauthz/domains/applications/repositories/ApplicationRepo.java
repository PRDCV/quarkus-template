package vvu.centrauthz.domains.applications.repositories;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import vvu.centrauthz.domains.applications.entities.ApplicationEntity;
import vvu.centrauthz.domains.applications.models.ApplicationFilter;
import vvu.centrauthz.domains.common.models.SortDirection;

/**
 * Repository for managing ApplicationEntity persistence operations.
 */
@Slf4j
@ApplicationScoped
public class ApplicationRepo implements PanacheRepository<ApplicationEntity> {

    private static final String APPLICATION_KEY = "applicationKey";

    /**
     * Queries applications based on the provided filter criteria.
     *
     * @param filter the search and pagination criteria
     * @param offset the starting index for pagination
     * @return list of matching application entities
     */
    public List<ApplicationEntity> query(ApplicationFilter filter, Integer offset) {
        Sort sortCriteria = buildSortCriteria(filter);
        var query = findAll(sortCriteria);
        query = applyFilters(query, filter);
        query = applyPagination(query, filter, offset);
        return query.list();
    }

    /**
     * Builds the sorting criteria from the filter.
     */
    private Sort buildSortCriteria(ApplicationFilter filter) {
        Sort sort = Sort.by();
        for (vvu.centrauthz.domains.common.models.Sort customSort : filter.sortOrder()) {
            Sort.Direction direction = customSort.direction() == SortDirection.DESC
                    ? Sort.Direction.Descending
                    : Sort.Direction.Ascending;
            sort = sort.and(customSort.field(), direction);
        }
        return sort;
    }

    /**
     * Applies filtering conditions to the query.
     */
    private PanacheQuery<ApplicationEntity> applyFilters(PanacheQuery<ApplicationEntity> query,
                                                         ApplicationFilter filter) {
        if (filter.name() != null) {
            query = query.filter("nameFilter", Map.of("name", filter.name()));
        }
        if (filter.managementGroupId() != null) {
            query = query.filter("managementGroupIdFilter",
                    Map.of("managementGroupId", filter.managementGroupId()));
        }
        if (filter.ownerId() != null) {
            query = query.filter("ownerIdFilter", Map.of("ownerId", filter.ownerId()));
        }
        return query;
    }

    /**
     * Applies pagination to the query.
     */
    private PanacheQuery<ApplicationEntity> applyPagination(PanacheQuery<ApplicationEntity> query,
                                                            ApplicationFilter filter,
                                                            Integer offset) {
        if (filter.pageSize() != null) {
            var endIndex = offset + filter.pageSize() - 1;
            query = query.range(offset, endIndex);
        }
        return query;
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
