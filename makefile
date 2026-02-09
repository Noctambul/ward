.PHONY: db-up db-down

db-up:
	docker compose up -d db

db-down:
	docker compose down -v
