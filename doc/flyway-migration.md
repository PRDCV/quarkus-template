# Working with Flyway

## Running Flyway Migrations

To run Flyway migrations in this project, use the following guidance:

### Project Structure

- Flyway configuration file: `flyway/flyway.conf`
- Migration SQL files: `flyway/sql/`

### Environment Variables

Set the following environment variables to configure the database connection:

- `FLYWAY_URL`: JDBC URL for your database (e.g., `jdbc:postgresql://localhost:5432/mydb`)
- `FLYWAY_USER`: Database username
- `FLYWAY_PASSWORD`: Database password

### Example Command

```bash
export FLYWAY_URL="jdbc:postgresql://localhost:5432/mydb"
export FLYWAY_USER="your_db_user"
export FLYWAY_PASSWORD="your_db_password"
flyway -configFiles=flyway/flyway.conf -locations=filesystem:flyway/sql migrate
```

This will run all migration scripts found in `flyway/sql/` against the configured database.


## Running Flyway Migrations in Docker

You can build and run the Flyway migration container using Docker. The provided `Dockerfile.flyway` uses the official Flyway image and copies your migration and configuration files.

### Building the Docker Image with Buildx

If you want to build a multi-platform image (recommended for CI/CD), use Docker Buildx:

```bash
docker buildx build -f src/main/docker/Dockerfile.flyway -t app-init-db:latest --platform linux/amd64,linux/arm64 .
```

For a standard build (single platform):

```bash
docker buildx build -f src/main/docker/Dockerfile.flyway -t app-init-db:latest .
```

### Running the Migration Container

Set the required environment variables and run the container:

```bash
docker run --rm \
	-e FLYWAY_URL="jdbc:postgresql://localhost:5432/mydb" \
	-e FLYWAY_USER="your_db_user" \
	-e FLYWAY_PASSWORD="your_db_password" \
	flyway-migration:latest
```

This will execute the migrations in the container using your configuration and SQL files.

