.PHONY: db-up db-down dev api quarkus-dev

dev: db-up api

db-up:
	docker compose up -d db

db-down:
	docker compose down -v

api:
	cd api && ./mvnw quarkus:dev
