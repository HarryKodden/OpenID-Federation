#!/bin/bash
set -e

# Start PostgreSQL server
docker-entrypoint.sh postgres &

# Wait for PostgreSQL to be ready
until pg_isready -d "$POSTGRES_DB" -U "$POSTGRES_USER"; do
    echo "Waiting for PostgreSQL to be ready..."
    sleep 2
done

echo "Waiting for other initilasations..."
sleep 120

echo "updating root identifier..."
# Execute your SQL command
psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "UPDATE account SET identifier = '$ROOT_IDENTIFIER' WHERE id=1;"

# Keep the container running
wait

psql -U "openid-federation-db-user" -d "openid-federation-db" -c "UPDATE account SET identifier = 'https://openid-federation-demo.pilot1.sram.surf.nl' WHERE id=1;"