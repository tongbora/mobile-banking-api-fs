#!/bin/bash

# Get current date and time
TIMESTAMP=$(date "+%Y-%m-%d %H:%M:%S")

# Use a custom message if provided, otherwise use timestamp
COMMIT_MSG=${1:-"Auto-push on $TIMESTAMP"}

echo "Pushing with message: '$COMMIT_MSG'"

# Git commands
git add .
git commit -m "$COMMIT_MSG"
git push

echo "✅ Auto-push complete."