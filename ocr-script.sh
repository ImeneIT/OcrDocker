#!/bin/bash

# Check if an image path and language are provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <image-path> <language>"
    exit 1
fi

IMAGE_PATH=$1
LANGUAGE=$2

# Perform OCR using Tesseract
tesseract "$IMAGE_PATH" stdout -l "$LANGUAGE"
