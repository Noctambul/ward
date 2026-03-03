.PHONY: db-up db-down dev api api-debug test test-unit test-integration

dev: db-up api

db-up:
	docker compose up -d db

db-down:
	docker compose down -v

api:
	cd api && ./mvnw quarkus:dev

api-debug:
	cd api && ./mvnw quarkus:dev -Ddebug=5005

web:
	cd web && npm start

test:
	cd api && ./mvnw test