# Wardcare

A personal healthcare management project built with modern technologies.

## Overview

Wardcare is a full-stack application for managing patient information and medical records. This is a personal project to explore healthcare software development.

## Tech Stack

- **Backend**: Java Quarkus + PostgreSQL
- **Frontend**: Angular + Angular Material
- **Database**: PostgreSQL with Docker

## Quick Start

```bash
# Start everything
make dev

# Or step by step:
make db-up    # Start database
make api      # Start backend
make web      # Start frontend
```

Access the app at `http://localhost:4200` and the API at `http://localhost:8080`.

## Development

- **API docs**: `http://localhost:8080/q/swagger-ui/`
- **Tests**: `make test`

Built as a learning project for healthcare software development.
