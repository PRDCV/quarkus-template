# Use the official Flyway image as base
FROM flyway/flyway:11.11.0

# Copy your SQL migration files to the default location
COPY db/migration/ /flyway/sql/

# Optional: Copy Flyway configuration file
COPY db/flyway.conf /flyway/conf/

# Set working directory
WORKDIR /flyway

# Default command - this will run migrations and then the container will stop
CMD ["migrate"]
