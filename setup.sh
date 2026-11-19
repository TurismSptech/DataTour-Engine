#!/usr/bin/env bash
set -euo pipefail

# Resolve paths from this script, so it can be run from any working directory.
PROJECT_ROOT="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
SOURCE="$PROJECT_ROOT/tourwise-leitor/src/main/java/com/tourwise/database/ConnectionFactoryExample.txt"
DESTINATION="$PROJECT_ROOT/tourwise-leitor/src/main/java/com/tourwise/database/ConnectionFactory.java"

if [[ ! -f "$SOURCE" ]]; then
  echo "Error: ConnectionFactoryExample.txt was not found: $SOURCE" >&2
  exit 1
fi
if [[ ! -f "$PROJECT_ROOT/.env.example" ]]; then
  echo "Error: .env.example was not found at the project root." >&2
  exit 1
fi

cp "$SOURCE" "$DESTINATION"

# Keep existing local credentials; create .env from the template on first setup.
if [[ ! -e "$PROJECT_ROOT/.env" ]]; then
  cp "$PROJECT_ROOT/.env.example" "$PROJECT_ROOT/.env"
  echo "Created .env from .env.example. Fill in the required values."
else
  echo "Keeping existing .env."
fi

echo "Generated ConnectionFactory.java."
