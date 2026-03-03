# Wardcare Frontend

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 21.2.0.

## Project Structure

Ce document décrit l'organisation du projet Angular Wardcare.

### Structure des dossiers

#### `src/app/core/`
Contient les services et fonctionnalités centrales de l'application :
- Services d'authentification
- Services HTTP globaux
- Intercepteurs
- Guards

#### `src/app/features/`
Contient les fonctionnalités métier de l'application, organisées par module :
- Patient management
- Login/Authentification
- Dashboard
- Chaque fonctionnalité peut contenir ses propres composants, services et modèles

#### `src/app/shared/`
Contient les composants et utilitaires réutilisables :
- Composants UI partagés
- Pipes personnalisés
- Directives
- Utilitaires et helpers

#### `src/app/models/`
Contient les définitions de données et interfaces TypeScript :
- Interfaces des entités métier
- Types personnalisés
- Modèles de données

### Principe d'organisation

Cette structure suit les bonnes pratiques Angular en séparant :
- **Core** : fonctionnalités uniques et globales
- **Features** : fonctionnalités métier modulaires
- **Shared** : composants réutilisables
- **Models** : définitions de données

Cette organisation facilite la maintenance, les tests et l'évolutivité de l'application.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
