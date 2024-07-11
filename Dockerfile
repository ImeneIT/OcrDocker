# Use the official Ubuntu base image
FROM ubuntu:latest

# Update and install dependencies
RUN apt-get update && apt-get install -y \
    tesseract-ocr \
    tesseract-ocr-fra \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /usr/src/app

# Copy the OCR script to the container
COPY ocr-script.sh .

# Make the script executable
RUN chmod +x ocr-script.sh

# Define the command to run the OCR script
CMD ["./ocr-script.sh"]
